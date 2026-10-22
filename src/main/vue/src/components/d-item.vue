<script setup> 
import { useSlots ,defineProps } from 'vue'; 
const slots = useSlots();   
const props = defineProps({
  
  /**[标题](#property)*/
   "row": {
        type: String,
        default: ''
       },
   "column": {
        type: String,
        default: ''
       }  
   
});
</script>
<template>  
       <div class="d-item" :style="{'gridRows':props.row,'gridcolumn':props.column}">
            <slot />
   
       </div>
   
</template>
 
<style scoped>
 .d-grid{ } 
</style>