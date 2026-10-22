import request from '@/utils/request'
 
  
 
export const orgApi  = {
 
   info(data) { 
      return request({
            url: 'data/org.json',
            method: 'get',
            data
         })
    },
    devices(data) { 
         return request({
               url: 'data/devices.json',
               method: 'get',
               data
            })
       }, 
    trend(data) { 
         return request({
               url: 'data/trend.json?mode='+data["mode"],
               method: 'get',
               data
            })
       },
     members(params) { 
         return request({
               url: 'data/members.json',
               method: 'get',
               params
            })
       },
     membersStat(params) { 
         return request({
               url: 'data/members_stat.json',
               method: 'get',
               params
            })
       },
      membersToday(params) { 
          return request({
               url: 'data/members_today.json',
               method: 'get',
               params
            })
       },
       ngDevices(params) { 
          return request({
            url: 'data/ng_devices.json',
            method: 'get',
            params
           })
       },
       ngDeviceSteps(params){
        return request({
            url: 'data/ng_device_steps.json',
            method: 'get',
            params
           }) 
       },
       ngDeviceTrend(params){
        return request({
            url: 'data/ng_device_trend.json',
            method: 'get',
            params
           }) 
       },
       message(params){
        return request({
            url: '/api/ai/chat',
            method: 'post',
            params
           }) 
       }
       
      
}