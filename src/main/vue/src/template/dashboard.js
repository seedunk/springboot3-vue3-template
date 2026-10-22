 
import { app } from '@/common/app.js';
 
import dashboard from '@/template/dashboard/dashboard.vue'

import index from '@/template/dashboard/index.vue'
import chart from '@/template/dashboard/chart.vue'
import { createRouter, createWebHistory } from 'vue-router'

 const constantRoutes = [
      
    {
      path: '/',
      name:'default',
      component:  index,
      meta: { title: '首页'  }
    } ,
    {
      path: '/index.html',
      name:'index',
      component:  index,
      meta: { title: '首页'  }
    } ,
    {
      path: '/chart.html',
      name:'chart',
      component:  chart,
      meta: { title: '首页'  }
    } 
  ]
 

const router = createRouter({
    history: createWebHistory("/"),
    routes: constantRoutes,
    scrollBehavior(to, from, savedPosition) {
  
      if (savedPosition) {
        return savedPosition
      } else {
        return { top: 0 }
      }
    } 
})


router.beforeEach((to, from, next) => {
    app.log('🔄 路由跳转:', to.path, '→ 组件:', to.component);
    next();
});

router.afterEach((to) => {
    app.log('✅ 路由加载完成:', to.path);
});



import { createI18n } from 'vue-i18n'

const i18n = createI18n({
  legacy: false,           // 必须，启用 Composition API
  globalInjection: true,   // 模板中可直接用 $t
  locale: 'zh',
  fallbackLocale: 'zh',
  messages: {
    zh: { 
      nav: { dashboard: '概览',  chart: 'ECharts',page:'页面' ,widget:"组件"}  
    },
    en: { 
      nav: { dashboard: 'Dashboard',  chart: 'ECharts',page:'Page' }  
    }
  }
})


app(dashboard).use(router).use(i18n).startup("dark");