<script setup> 
import { useSlots ,defineProps } from 'vue'; 
const slots = useSlots();  

const props = defineProps({
  
  /**[标题](#property)*/
  title: {
    type: String,
    default: '--'
  },  
  /**[模式](#property)*/
  mode: {
    type: String,
    default: 'card'
  },
   /**[数据]](#property)*/
  value: {
    type: String,
    default:  '--'
  } 
});
</script>
<template>

  <component
    :is="mode === 'card' ? 'a-card' : 'div'"
    :bordered="false" 
     >
    <a-skeleton :loading="true"    :paragraph="{ rows: 3 }">
     
      <a-flex :vertical="true"  > 
             <a-flex  flex="1"  > </a-flex>
             <a-flex>
                   <a-input flex="1"  placeholder="Basic usage" /> 
                   <a-button>发送</a-button>
             </a-flex>  
    </a-flex>
    </a-skeleton>
  </component>
</template>
 
<style scoped>
 
  
 
</style>