<script setup>  
import * as echarts from 'echarts/core';
 
import { PieChart } from 'echarts/charts';
import { TitleComponent, TooltipComponent } from 'echarts/components';
import { CanvasRenderer } from 'echarts/renderers';

// 3. 注册这些模块
echarts.use([PieChart, TitleComponent, TooltipComponent, CanvasRenderer]);


import { useSlots ,defineProps } from 'vue'; 
 
const props = defineProps({
  data: {
    type: Array  ,
    default: []
  },
  tag: {
    type: String  ,
    default: ""
  }
});

const emit =defineEmits(['ecPieSelectChanged'])

const chartRef = ref(null);
let chartInstance = null;

// 获取 Antd 成功色
 

const initChart = () => {
  if (!chartRef.value) return;
  // 如果没有实例则创建，如果有则直接更新，避免多次初始化
  if (!chartInstance) {
       chartInstance = echarts.init(chartRef.value);
       chartInstance.on('selectchanged',  (params) => {
        // 获取当前选中的扇区名称
            const current = params.selected?.[0]?.dataIndex ?? -1

            if (current === lastSelected) {
              return // 状态没变，忽略
            }
            lastSelected = current
 
                const selectedList = params.selected || []
                if (selectedList.length === 0) return
                const { dataIndex } = selectedList[0]
                const indexes = Array.isArray(dataIndex) ? dataIndex : [dataIndex]

                if (indexes.length === 0) {
                    console.log('取消选中')
                    return
                }

                const index = indexes[0]
                const item = props.data[index]
                  
                emit("ecPieSelectChanged",  item) 
        }); 
  } 
  updateChart();
};

const updateChart = () => {
  if (!chartInstance) return;
  
  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c}%'
    }, 
    series: [
      {
         type: 'pie',  
        radius: ['0%', '60%'],
        avoidLabelOverlap: false,
        label: { show: true, 
            
            color: '#ffffff',            // 文字颜色
            fontSize: 14,             // 字号
            fontWeight: 'normal'      // 字重 
          },
       emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        // 关键：开启单选选中模式
        selectedMode: 'single',
        selectedOffset: 10, // 选中扇区偏移距离
        // 选中状态的样式
        select: {
          itemStyle: {
            shadowBlur: 20,
            shadowColor: 'rgba(0, 0, 0, 0.5)',
            borderColor: '#fff',
            borderWidth: 2
          },
          label: {
            show: true,
            fontWeight: 'bold'
          }
        },
        data: props.data
      }
    ]
  }; 

   

  chartInstance.setOption(option, true); // 第二个参数 true 表示合并模式，但也没坏处
 
  const defaultItem = props.data.find(item => item.selected)
  if (defaultItem) {
     emit("ecPieSelectChanged",  defaultItem)  
  }
  
                           
};
let lastSelected = ''
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
  () => props.data,
  () => {
    nextTick(() => {
      updateChart();
    });
  }
);
</script>
<template>
 <div ref="chartRef" class="ec-pie"></div>
   
</template>
 
<style scoped>
  .ec-pie{
    width: 100%;
    height:100%; 
  }
 
</style>