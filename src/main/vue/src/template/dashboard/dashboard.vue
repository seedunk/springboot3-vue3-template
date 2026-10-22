 <template>
    <a-config-provider  :theme="$theme"   :locale="zhCN"   > 
        <div class="dashboard" >  
              <d-header  class="dashboard-header"     >
                        <template   #logo>
                             <img height="28px" :src="img_seedunk">
                        </template>
                        <template #title>
                        <a-space  :size="[4,0]">   
                              <span >&nbsp;</span>
                              <a-link   class="dashboard-header-title">SEEDUNK</a-link> 
                              <span     class="dashboard-header-subtitle">•</span>
                              <a-link   class="dashboard-header-subtitle" style="margin-right: 12px;">Springboot3 Vue3 Template</a-link > 
                        </a-space >  
                        </template>
                        <template #subtitle> 
                               <a-space > 
                                 <router-link :to="{ name: 'index', params: {  } }"    v-slot="{ isActive, href, navigate  }"> 
                                  <a-button  :type="isActive?'primary': 'ghost'" shape="round" size="small">
                                     {{ $t('nav.dashboard') }}
                                 </a-button>
                                </router-link> 
                                 <router-link :to="{ name: 'chart', params: {  } }"    v-slot="{ isActive, href, navigate  }"> 
                                
                                    <a-button  :type="(isActive ||  route.meta?.activeMenu === 'datacenter')?'primary': 'ghost'"  shape="round" size="small">
                                        {{ $t('nav.chart') }} 
                                    </a-button>
                                </router-link> 
                                 <router-link :to="{ name: 'chart', params: {  } }"    v-slot="{ isActive, href, navigate  }"> 
                                
                                    <a-button  :type="(isActive ||  route.meta?.activeMenu === 'datacenter')?'primary': 'ghost'"  shape="round" size="small">
                                        {{ $t('nav.widget') }} 
                                    </a-button>
                                </router-link> 
                                  <a-dropdown> 

                                  <a-button  :type="(isActive ||  route.meta?.activeMenu === 'datacenter')?'primary': ''"  shape="round" size="small">
                                     {{ $t('nav.page') }}
                                 </a-button>
                               <template #overlay>
                                  <a-menu  >
                                    <a-menu-item key="list">
                                         <a-button  :type="(isActive ||  route.meta?.activeMenu === 'datacenter')?'primary': ''"  shape="round" size="small">
                                          列表
                                        </a-button>
                                    </a-menu-item>
                                    <a-menu-item key="default">
                                        <a-button  :type="(isActive ||  route.meta?.activeMenu === 'datacenter')?'primary': ''"  shape="round" size="small">
                                          详情
                                        </a-button>
                                    </a-menu-item>
                                     <a-menu-item key="form">
                                        <a-button  :type="(isActive ||  route.meta?.activeMenu === 'datacenter')?'primary': ''"  shape="round" size="small">
                                          表单
                                        </a-button>
                                    </a-menu-item>
                                    <a-menu-divider /> 
                                  </a-menu>
                                </template>
                             </a-dropdown>
                                 
                            </a-space >
                        </template>
                        <template #extra> 
                          
                          <a-dropdown>
                            <a-avatar size="small"  ><UserOutlined></UserOutlined></a-avatar> 
                          <template #overlay>
                    <a-menu  >
                      <a-menu-item key="profile">
                        <UserOutlined />
                        个人中心
                      </a-menu-item>
                      <a-menu-item key="settings">
                        <SettingOutlined />
                        账号设置
                      </a-menu-item>
                      <a-menu-divider />
                      <a-menu-item key="logout">
                        <LogoutOutlined />
                        退出登录
                      </a-menu-item>
                    </a-menu>
                                   </template>
                          </a-dropdown>
                        </template>
              </d-header>  
              <div style="grid-template-rows: 2/-1;overflow-y: auto; overflow-x: auto; padding:0 16px; ">
                  <router-view  :style="{ backgroundColor: token?.colorBgBase }" />
              </div>
              <footer>
                <a-flex justify="center" align="center" >
                    <a-space>
                       <a-button target="_blank"  href="https://seedunk.github.io"  type="ghost" shape="round"  size="small">
                                 来自SEEDUNK
                       </a-button> 
                    </a-space>
                </a-flex>
              </footer>
         </div>   
      </a-config-provider>   
</template>

<script setup>
 
import { useRoute } from 'vue-router'

import img_seedunk from '@/asset/seedunk.png';
import { UserOutlined,DownOutlined  } from '@ant-design/icons-vue'; 
const route = useRoute()
 
 

 
// // 4. 组件挂载时加载默认主题
onMounted(() => {  
   
  
     
});

 
 
</script>
<style lang="scss"> 
#app {
  width: 100%;
  height: 100%;
  display: flex;
  overflow: hidden;
}

.dashboard{  display: grid; grid-template-rows:auto 1fr auto;  height:100%;  width:100%; }
.dashboard-header { 
  padding:12px 0;
}
 .dashboard-header-title {
    background-image: linear-gradient(to bottom, #ffffff, #a8c2eb);
    -webkit-background-clip: text;
    background-clip: text;
    color: transparent;
    display: inline-block;
    font-weight: bold;
    font-size:22px;
}
.dashboard-header-subtitle{ 
    color: #a8c2eb;  
    font-size:16px;
}
</style>
