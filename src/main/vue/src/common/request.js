import axios from 'axios'
import { message } from 'ant-design-vue'
 
 import camelcaseKeys from 'camelcase-keys';
import { getToken, removeToken } from '@/utils/auth'

// 创建axios实例
const request = axios.create({
    baseURL: '',
    timeout: 15000,
    headers: {
      'Content-Type': 'application/json'
    }
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    const token = getToken()
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }

   

    return config
  },
  error => {
    return Promise.reject(error)
  }
)

 




// 响应拦截器
request.interceptors.response.use(
  response => {
    const res = response.data  
    if (res.code === 200) {
  
      return camelcaseKeys( res.data, { deep: true });
    } else {
      message.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || '请求失败'))
    }
  },
  error => {
    const { response } = error
    
    if (response) {
      switch (response.status) {
        case 401:
          // 未登录或token过期
          // const userStore = useUserStore()
          // userStore.resetState()
          // removeToken()
          // message.error('登录已过期，请重新登录')
          // router.push('/login')
          break
        case 403:
          message.error('没有权限访问该资源')
          break
        case 404:
          message.error('请求的资源不存在')
          break
        case 500:
          message.error('服务器内部错误')
          break
        default:
          message.error(response.data?.message || '请求失败')
      }
    } else {
      message.error('网络连接异常，请检查网络')
    }
    
    return Promise.reject(error)
  }
)

export default request
