// composables/useWebSocketManager.js
import { ref, reactive, shallowRef, onUnmounted, computed } from 'vue'

// 连接状态常量
export const WS_STATUS = {
  CONNECTING: 'connecting',
  CONNECTED: 'connected',
  DISCONNECTED: 'disconnected',
  RECONNECTING: 'reconnecting',
  ERROR: 'error',
  CLOSED: 'closed'
}

// 默认配置
const DEFAULT_CONFIG = {
  reconnectInterval: 10000,
  maxReconnectAttempts: 5,
  heartbeatInterval: 25000,
  heartbeatTimeout: 60000,
  autoReconnect: true
}

export function useWebSocketManager() {
  // ========== 连接池 ==========
  const connections = reactive(new Map())
  const connectionStatus = reactive({})
  
  // ========== 消息路由器 ==========
  const messageHandlers = new Map()
  const topicHandlers = new Map()
  
  // ========== 全局状态 ==========
  const isAnyConnected = computed(() => {
    for (const status of Object.values(connectionStatus)) {
      if (status === WS_STATUS.CONNECTED) return true
    }
    return false
  })

 
 


  // ========== 创建新连接 ==========
  function createConnection(connectionId, config = {}) {
    if (connections.has(connectionId)) {
      console.warn(`连接 ${connectionId} 已存在`)
      return connections.get(connectionId)
    }

    const options = { ...DEFAULT_CONFIG, ...config }
    
    const connection = {
      id: connectionId,
      ws: null,
      status: WS_STATUS.DISCONNECTED,
      config: options,
      reconnectAttempts: 0,
      reconnectTimer: null,
      heartbeatTimer: null,
      heartbeatCheckTimer: null,
      subscriptions: new Set(),
      messageBuffer: [],
      handlers: new Map()
    }
 
 

    connections.set(connectionId, connection)
    connectionStatus[connectionId] = WS_STATUS.DISCONNECTED

    // 自动连接
    if (options.autoConnect !== false) {
      connect(connectionId)
    }

    return connection
  }

  // ========== 连接 ==========
  function connect(connectionId) {
    const conn = connections.get(connectionId)
    if (!conn) {
      console.error(`连接 ${connectionId} 不存在`)
      return
    }

    if (conn.ws && conn.ws.readyState === WebSocket.OPEN) {
      console.warn(`连接 ${connectionId} 已开启`)
      return
    }

    try {
      conn.status = WS_STATUS.CONNECTING
      updateStatus(connectionId, WS_STATUS.CONNECTING)

      const ws = new WebSocket(conn.config.url)
            ws.binaryType = 'arraybuffer' 
      conn.ws = ws

      ws.onopen = () => {
        onOpen(connectionId)
      }

      ws.onmessage = (event) => { 
         onMessage(connectionId, event)
      }

      ws.onclose = (event) => {
         onClose(connectionId, event)


      }

      ws.onerror = (error) => {
        onError(connectionId, error)
      }

    } catch (error) {
      console.error(`连接 ${connectionId} 创建失败`, error)
      conn.status = WS_STATUS.ERROR
      updateStatus(connectionId, WS_STATUS.ERROR)
      scheduleReconnect(connectionId)
    }
  }

  // ========== 连接打开 ==========
  function onOpen(connectionId) {
    const conn = connections.get(connectionId)
    if (!conn) return

    conn.status = WS_STATUS.CONNECTED
    conn.reconnectAttempts = 0
    updateStatus(connectionId, WS_STATUS.CONNECTED)

    // 启动心跳
    startHeartbeat(connectionId)

    // 发送缓冲消息
    flushBuffer(connectionId)

    // 重新订阅
    resubscribeAll(connectionId)

    //console.log(`✅ 连接 ${connectionId} 已建立`)
    for (const handler of messageHandlers.get("string")) {
                handler({"MessageType":"onOpen"}, connectionId)
     }
  }

  // ========== 接收消息 ==========
  function onMessage(connectionId, event) {
    const conn = connections.get(connectionId)
    if (!conn) return

     
     
    try {

      if(typeof event.data ==="string"){
         const data =  JSON.parse(event.data) ;
         if (data.type === 'pong') {
             conn.lastHeartbeatAck = Date.now()
            return
         }
           
         for (const handler of messageHandlers.get("string")) {
               handler(data, connectionId)
         }
       
      }else{
 
          for (const handler of messageHandlers.get("raw")) { 
               handler(event.data, connectionId)
           }
      } 

    } catch (error) {
      console.error(`解析消息失败 (${connectionId})`, error)
    }
  }

 

  // ========== 关闭连接 ==========
  function onClose(connectionId, event) {
    const conn = connections.get(connectionId)
    if (!conn) return

    conn.status = WS_STATUS.DISCONNECTED
    updateStatus(connectionId, WS_STATUS.DISCONNECTED)
    stopHeartbeat(connectionId)

     // console.log(`❌ 连接 ${connectionId} 已关闭`, event.code, event.reason)

      for (const handler of messageHandlers.get("string")) {
               handler({"MessageType":"onClose"}, connectionId)
      }



    // 自动重连
    if (conn.config.autoReconnect !== false) {
      scheduleReconnect(connectionId)
    }
  }

  // ========== 错误处理 ==========
  function onError(connectionId, error) {
    const conn = connections.get(connectionId)
    if (!conn) return

    conn.status = WS_STATUS.ERROR
    updateStatus(connectionId, WS_STATUS.ERROR)
    for (const handler of messageHandlers.get("string")) {
               handler({"MessageType":"onError"}, connectionId)
      }
  }

  // ========== 重连机制 ==========
  function scheduleReconnect(connectionId) {
    const conn = connections.get(connectionId)
    if (!conn) return

    if (conn.reconnectTimer) {
      clearTimeout(conn.reconnectTimer)
    }

    const maxAttempts = conn.config.maxReconnectAttempts
    if (maxAttempts > 0 && conn.reconnectAttempts >= maxAttempts) {
      console.warn(`连接 ${connectionId} 已达到最大重连次数`)
      conn.status = WS_STATUS.CLOSED
      updateStatus(connectionId, WS_STATUS.CLOSED)
      return
    }

    conn.reconnectAttempts++
    const delay = conn.config.reconnectInterval * Math.min(conn.reconnectAttempts, 10)

    console.log(`⏳ 连接 ${connectionId} ${delay}ms 后重连 (${conn.reconnectAttempts}/${maxAttempts})`)

    conn.reconnectTimer = setTimeout(() => {
      connect(connectionId)
    }, delay)
  }

  // ========== 心跳管理 ==========
  function startHeartbeat(connectionId) {
    const conn = connections.get(connectionId)
    if (!conn) return

    // 发送心跳
    conn.heartbeatTimer = setInterval(() => {
      if (conn.ws && conn.ws.readyState === WebSocket.OPEN) {
        conn.ws.send(JSON.stringify({ type: 'ping', timestamp: Date.now() }))
      }
    }, conn.config.heartbeatInterval)

    // 检查心跳超时
    conn.lastHeartbeatAck = Date.now()
    conn.heartbeatCheckTimer = setInterval(() => {
      const now = Date.now()
      if (now - conn.lastHeartbeatAck > conn.config.heartbeatTimeout) {
        console.warn(`连接 ${connectionId} 心跳超时，重新连接`)
        if (conn.ws) {
          conn.ws.close()
        }
      }
    }, 5000)
  }

  function stopHeartbeat(connectionId) {
    const conn = connections.get(connectionId)
    if (!conn) return

    if (conn.heartbeatTimer) {
      clearInterval(conn.heartbeatTimer)
      conn.heartbeatTimer = null
    }
    if (conn.heartbeatCheckTimer) {
      clearInterval(conn.heartbeatCheckTimer)
      conn.heartbeatCheckTimer = null
    }
  }

  // ========== 消息发送 ==========
  function send(connectionId, data) {
    const conn = connections.get(connectionId)
    if (!conn) {
      console.error(`连接 ${connectionId} 不存在`)
      return false
    }

    const message = typeof data === 'string' ? data : JSON.stringify(data)

    if (conn.ws && conn.ws.readyState === WebSocket.OPEN) {
      conn.ws.send(message)
      return true
    } else {
      // 缓冲消息，待连接恢复后发送
      conn.messageBuffer.push(message)
      return false
    }
  }

  // ========== 发送缓冲消息 ==========
  function flushBuffer(connectionId) {
    const conn = connections.get(connectionId)
    if (!conn) return

    const buffer = conn.messageBuffer
    conn.messageBuffer = []

    for (const message of buffer) {
      send(connectionId, message)
    }
  }

  // ========== 订阅管理 ==========
  
  /**
   * 注册消息处理器（全局）
   */
  function onMessageType(type, handler) {
    if (!messageHandlers.has(type)) {
      messageHandlers.set(type, new Set())
    }
    messageHandlers.get(type).add(handler)
    return () => {
      messageHandlers.get(type)?.delete(handler)
    }
  }

  /**
   * 注册主题处理器（全局）
   */
  function onTopic(topic, handler) {
    if (!topicHandlers.has(topic)) {
      topicHandlers.set(topic, new Set())
    }
    topicHandlers.get(topic).add(handler)
    return () => {
      topicHandlers.get(topic)?.delete(handler)
    }
  }

  /**
   * 注册连接专属处理器
   */
  function onConnectionMessage(connectionId, type, handler) {
    const conn = connections.get(connectionId)
    if (!conn) {
      console.error(`连接 ${connectionId} 不存在`)
      return () => {}
    } 
    if (!messageHandlers.has(type)) {
          messageHandlers.set(type, new Set())
    }
         messageHandlers.get(type).add(handler)
    return () => {
         messageHandlers.get(type)?.delete(handler)
     }
  }

  /**
   * 订阅主题（发送订阅消息）
   */
  function subscribe(connectionId, topic, callback = null) {
    const conn = connections.get(connectionId)
    if (!conn) return

    conn.subscriptions.add(topic)

    // 如果提供了回调，也注册到主题处理器
    if (callback) {
      if (!topicHandlers.has(topic)) {
        topicHandlers.set(topic, new Set())
      }
      topicHandlers.get(topic).add(callback)
    }

    send(connectionId, {
      type: 'subscribe',
      topic
    })
  }

  /**
   * 取消订阅
   */
  function unsubscribe(connectionId, topic, callback = null) {
    const conn = connections.get(connectionId)
    if (!conn) return

    conn.subscriptions.delete(topic)

    if (callback) {
      topicHandlers.get(topic)?.delete(callback)
    }

    send(connectionId, {
      type: 'unsubscribe',
      topic
    })
  }

  /**
   * 重新订阅所有
   */
  function resubscribeAll(connectionId) {
    const conn = connections.get(connectionId)
    if (!conn) return

    for (const topic of conn.subscriptions) {
      send(connectionId, {
        type: 'subscribe',
        topic
      })
    }
  }

  // ========== 状态更新 ==========
  function updateStatus(connectionId, status) {
    connectionStatus[connectionId] = status
  }

  // ========== 获取状态 ==========
  function getStatus(connectionId) {
    return connectionStatus[connectionId] || WS_STATUS.DISCONNECTED
  }

  function isConnected(connectionId) {
    return getStatus(connectionId) === WS_STATUS.CONNECTED
  }

  // ========== 断开连接 ==========
  function disconnect(connectionId, code = 1000, reason = '主动断开') {
    const conn = connections.get(connectionId)
    if (!conn) return

    if (conn.reconnectTimer) {
      clearTimeout(conn.reconnectTimer)
      conn.reconnectTimer = null
    }

    if (conn.ws) {
      conn.ws.close(code, reason)
    }

    conn.status = WS_STATUS.CLOSED
    updateStatus(connectionId, WS_STATUS.CLOSED)
    stopHeartbeat(connectionId)

    console.log(`🔌 连接 ${connectionId} 已主动断开`)
  }

  // ========== 移除连接 ==========
  function removeConnection(connectionId) {
    disconnect(connectionId)
    connections.delete(connectionId)
    delete connectionStatus[connectionId]
  }

  // ========== 清理所有连接 ==========
  function disconnectAll() {
    for (const connectionId of connections.keys()) {
      disconnect(connectionId)
    }
    connections.clear()
    Object.keys(connectionStatus).forEach(key => delete connectionStatus[key])
  }

  // ========== 获取统计信息 ==========
  function getStats() {
    const stats = {
      total: connections.size,
      connected: 0,
      connecting: 0,
      disconnected: 0,
      error: 0,
      closed: 0
    }

    for (const status of Object.values(connectionStatus)) {
      switch (status) {
        case WS_STATUS.CONNECTED: stats.connected++; break
        case WS_STATUS.CONNECTING: stats.connecting++; break
        case WS_STATUS.DISCONNECTED: stats.disconnected++; break
        case WS_STATUS.ERROR: stats.error++; break
        case WS_STATUS.CLOSED: stats.closed++; break
      }
    }

    return stats
  }

  // ========== 生命周期 ==========
  onUnmounted(() => {
    disconnectAll()
  })

  return {
    // 连接管理
    createConnection,
    connect,
    disconnect,
    removeConnection,
    disconnectAll,
    
    // 发送消息
    send,
    
    // 订阅管理
    onMessageType,
    onTopic,
    onConnectionMessage,
    subscribe,
    unsubscribe,
    
    // 状态查询
    getStatus,
    isConnected,
    getStats,
    connectionStatus,
    isAnyConnected,
    
    // 常量
    WS_STATUS
  }
}