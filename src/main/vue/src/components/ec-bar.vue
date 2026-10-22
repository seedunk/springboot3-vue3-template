 <script setup> 

import { theme} from 'ant-design-vue'; 
const { token } = theme.useToken();
import { ref, onMounted, onBeforeUnmount } from 'vue'
import * as echarts from 'echarts/core'
import { BarChart } from 'echarts/charts'
import { TitleComponent, TooltipComponent, GridComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import axios from 'axios'

echarts.use([BarChart, TitleComponent, TooltipComponent, GridComponent, CanvasRenderer])

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
     grid: {
        left: 0,
        right: 0,
        top: 0,
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
         axisTick: {
          show: false   // 设为 false 即可
        },
        // 2. 设置 X 轴分割线颜色
        splitLine: {
          show: false,
          lineStyle: {
            color: '#ff0000',     // 分割线颜色 [citation:9]
            type: 'dashed'        // 可选：'solid', 'dashed', 'dotted'
          }
        },
      data: props.x
    },
    yAxis: 
        {  position: 'right',
           show:false,
           splitLine: {
            show: false,  // 不显示 Y 轴分割线
          },min: 0,      // Y 轴最小值
            max: 100    
           },
    series: props.data
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