<!--# 概览示例页面-->
<template> 
<d-grid class="index"   rows="auto 1fr" columns="1fr 1fr 1fr 1fr 1fr">
        <d-overview  title="访问量" value="0">
            <template #icon>
                <FundFilled />
            </template> 
            <template #description>暂无</template> 
        </d-overview> 
        <d-overview  title="模块数" value="3">
            <template #icon>
                <AppstoreFilled />
            </template> 
            <template #description>暂无</template> 
        </d-overview>
        <d-overview  title="页面数" value="3">
            <template #icon>
                <FileFilled/>
            </template> 
            <template #description>暂无</template> 
        </d-overview>
        <d-overview  title="图表数" value="7">
            <template #icon>
                <PieChartFilled/>
            </template> 
            <template #description>暂无</template> 
        </d-overview>
        <d-overview  title="控件数" value="24">
            <template #icon>
                <BuildFilled/>
            </template> 
            <template #description>暂无</template> 
         </d-overview> 
         <d-grid rows="1fr" row="2" column="span 3"> 
            <a-card>数据</a-card>
         </d-grid>
         <d-grid rows="1fr 1fr" row="2" column="span 2">
              <d-grid rows="1rf"><a-card>表格1</a-card></d-grid>
              <d-grid rows="1rf" row="2"><a-card>表格2</a-card></d-grid> 
         </d-grid>
</d-grid>
</template> 
<script setup>
import { HomeFilled, AppstoreFilled, FileFilled,FundFilled, PieChartFilled,BuildFilled  } from '@ant-design/icons-vue';  //<pie-chart-filled />  
</script> 
<style lang="scss">   
.index{   height:100%;  width:100%;  }

.anticon{ font-size: 28px;}
</style>

