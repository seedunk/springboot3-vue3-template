import { createApp } from 'vue'
import { createPinia } from 'pinia'  
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import '@/styles/index.scss' 
import { getThemeToken } from '@/common/design.js';
 

const appMountId= typeof getAppMountId == 'undefined'?"app":getAppMountId()
 

export  function app(appView,config={}) {

   try {
            const app = createApp(appView,config)
            const pinia = createPinia()
        
            app.use(pinia)
            app.use(Antd)
         // app.use(router) 
            // 设置全局指令
            //setupDirectives(app)
            // 设置全局组件
            //setupGlobalComponents(app) 
            app.components=function(components){
                 Object.keys(components).forEach((key) => {
                            this.component(key, components[key])
                        })
            }
            app.directives=function(directives){
                 Object.keys(directives).forEach((key) => {
                            this.directive(key, directives[key])
                        })
            }
              
            app.startup=function(themeName="theme"){
                // 加载模板样式
                this.config.globalProperties.$theme = {
                                         token:getThemeToken(themeName )
                                        } 
                document.body.style.backgroundColor = this.config.globalProperties.$theme.token.colorBgLayout; 
                document.body.style.color = this.config.globalProperties.$theme.token.colorText; 

                // 挂载
                this.mount('#'+appMountId)
            };
            return app
    } catch (error) {
            const view= document.querySelector("body")
            if(view==null){
                alert("Error "+error.message)
            }else{
                view.innerHTML = `
                <div style="display:flex;justify-content:center;align-items:center;height:100vh;flex-direction:column;">
                    <h2>Error</h2>
                    <p>${error.message}</p> 
                </div> `
            }
    }
}

app.log=function(log){
  console.log(log)
}


 