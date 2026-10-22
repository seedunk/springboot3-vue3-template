 <script setup> 

import { theme} from 'ant-design-vue'; 
const { token } = theme.useToken();
import { ref, onMounted, onBeforeUnmount } from 'vue'
import * as echarts from 'echarts/core'
import { LineChart } from 'echarts/charts'

import { TitleComponent, TooltipComponent, GridComponent ,LegendComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import axios from 'axios'

echarts.use([LineChart, TitleComponent, TooltipComponent, GridComponent, CanvasRenderer,LegendComponent])

import { useSlots ,defineProps } from 'vue'; 
const slots = useSlots();  
 const props = defineProps({
   x: {
    type: Array,
    default: []
  },
  data: {
    type: Array,
    default: []
  }
});

const chartRef = ref(null);
let chartInstance = null;



const initChart = () => {
  if (!chartRef.value) return;
  // 如果没有实例则创建，如果有则直接更新，避免多次初始化
  if (!chartInstance) {
    chartInstance = echarts.init(chartRef.value);
  }
  
  updateChart();
};

const updateChart = () => {
  if (!chartInstance) return;
   

  const option = {
     title: null,
      legend: {
        show: true,
        data: props.data.map(item => item.name),  // 显式指定图例项
        top: 0, 
        left: 'center',
        textStyle: {
          color: '#fff'   // 图例文字颜色
        },
        inactiveColor: '#aaa'
      },
     grid: {
        left: 0,
        right: 0,
        top: 24,
        bottom: 12,
        containLabel: false,  // 不包含轴标签，让标签溢出
      },
    tooltip: {},
    xAxis: { 
         axisLine: { show: false }, 
         axisLabel: {
          textStyle: {
            color: '#8ba4c7',   
            fontSize: 12
          }
        },
        axisTick: {   show: false    }, 
        splitLine: {
          show: false,
          lineStyle: {
            color: '#ff0000',    
            type: 'dashed'     
          }
        },
        type: 'category',
        boundaryGap: false,
        data: props.x
    },
    yAxis: 
        {  position: 'right',
           show:false,
           splitLine: {
            show: false,  // 不显示 Y 轴分割线
           }
           },
          series: props.data.map(item => ({
                  ...item,
                  type: 'line',
                 //为NULL连线  conectNulls:true,
                  smooth: true
          }))
   }
  
  chartInstance.setOption(option, true); // 第二个参数 true 表示合并模式，但也没坏处
};

const handleResize = () => {
  chartInstance?.resize();
};

onMounted(() => {
  // 确保 DOM 挂载完成
  nextTick(() => {
    initChart();
    window.addEventListener('resize', handleResize);
  });
});

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize);
  chartInstance?.dispose();
  chartInstance = null;
});

watch( 
    [() => props.data,() => props.x],
    () => {
      nextTick(() => {
          updateChart();
      });
    }
);
</script>
<template>
 <div ref="chartRef" class="ec-rate" :style="{height:'100%'}"></div>
   
</template>
 
<style scoped>
  .ec-rate{
    width: 100%;
  }
 
</style>