<script setup> 
import { useSlots ,defineProps } from 'vue'; 
const slots = useSlots();   
const props = defineProps({
  
  /**[标题](#property)*/
   "rows": {
        type: String,
        default: ''
       },
   "columns": {
        type: String,
        default: ''
       },
     "row": {
        type: String,
        default: ''
       },
     "column": {
        type: String,
        default: ''
       }  ,
     "gap": {
        type: String,
        default: '16px'
       }   
   
});
</script>
<template>  
       <div class="d-grid" :style="{'gap':props.gap,'gridTemplateRows':props.rows,'grid-template-columns':props.columns,'gridRow':props.row,'gridColumn':props.column}">
            <slot /> 
       </div>
   
</template>
 
<style scoped>
 .d-grid{display: grid; } 
</style>