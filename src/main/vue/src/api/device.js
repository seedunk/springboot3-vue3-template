import request from '@/utils/request'

import CryptoJS from 'crypto-js';
 
import { useClientStore } from '@/stores/client.js';
 
export const deviceApi  = {
 
   reg(username,passowd){

         return request({
               url: '/login-client.token.json?username='+username+'&password='+CryptoJS.MD5(passowd).toString(),
               method: 'get' })
   },
 
    today(data) { 
    return request({
       url: '/api/device/today.json',
       method: 'get',
       data
    })
  },
 
 
   
}