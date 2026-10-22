<script setup> 

import { theme} from 'ant-design-vue';
import * as echarts from 'echarts/core';
 
import { PieChart } from 'echarts/charts';
import { TitleComponent, TooltipComponent } from 'echarts/components';
import { CanvasRenderer } from 'echarts/renderers';

// 3. 注册这些模块
echarts.use([PieChart, TitleComponent, TooltipComponent, CanvasRenderer]);


import { useSlots ,defineProps } from 'vue'; 
const slots = useSlots();  
const props = defineProps({
  rate: {
    type: String  ,
    default: "0"
  }
});
const chartRef = ref(null);
let chartInstance = null;

// 获取 Antd 成功色
const { token } = theme.useToken();

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
  const rate = Number(props.rate) 
  const successColor = token.value?.colorSuccess || '#52c41a';

  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c}%'
    }, 
    series: [
      {
        type: 'pie',
        radius: ['85%', '100%'],
        avoidLabelOverlap: false,
        label: { show: false },
        emphasis: {
          scale: false
        },
        data: [
          { 
            value: rate, 
            name: '合格',
            itemStyle: { color: successColor }
          },
          { 
            value: Math.max(100 - rate, 0), 
            name: '不合格',
            itemStyle: { color: "#111" }
          }
        ]
      }
    ]
  };
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
  () => props.rate,
  () => {
    nextTick(() => {
      updateChart();
    });
  }
);
</script>
<template>
 <div ref="chartRef" class="ec-rate"></div>
   
</template>
 
<style scoped>
  .ec-rate{
    width: 100%;
  }
 
</style>