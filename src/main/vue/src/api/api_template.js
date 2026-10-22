import request from '@/utils/request'

import CryptoJS from 'crypto-js';
 
import { useClientStore } from '@/stores/client.js';
 
export const clientApi  = {

  

  // 登录
   clients(data) { 
      return request({
         url: '/DeviceWeb/web/client-devs.result.json',
         method: 'get',
         data
      })
  },
  client_stat(data) { 
   const searchParams = new URLSearchParams(data);

      return request({
         url: '/DeviceWeb/web/client_stat.result.json?'+searchParams,
         method: 'get',
         data
      })
  },
  
   today(data) {
    // const clientStore=useClientStore();
    // return request({
    //   url: '/devices.result.json?token='+clientStore.token,
    //   method: 'get',
    //    data
    // })
 
    return request({
       url: '/device/today.json',
       method: 'get',
       data
    })
  },
 today24(data) {
    // const clientStore=useClientStore();
    // return request({
    //   url: '/devices.result.json?token='+clientStore.token,
    //   method: 'get',
    //    data
    // })
 
    return request({
       url: '/device/today24.json',
       method: 'get',
       data
    })
  },
 
   
}