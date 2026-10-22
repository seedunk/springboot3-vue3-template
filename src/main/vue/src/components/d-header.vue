<script setup> 
import { useSlots ,defineProps } from 'vue'; 
const slots = useSlots();   
const props = defineProps({
  
  /**[标题](#property)*/
  title: {
        type: String,
        default: ''
       },  
  /**[子标题](#property)*/
  subtitle: {
        type: String,
        default: ''
      }, 
});
</script>
<template>  
        <a-flex justify="space-between" align="center"  :class="$attrs.class" style="margin: 0 16px;"   >
              <a-flex justify="center" align="center"  > 
                  <!--[自定义图标视图](#slot)-->
                 <slot name="logo"></slot> 
                  <!--[自定义标题视图](#slot)-->
                 <slot name="title"></slot>  
                  <!--[自定义子标题视图](#slot)-->
                 <slot name="subtitle"></slot>  
             </a-flex>
           
             <a-flex  align="center"   v-if="$slots.extra" >  
                 <!--[末尾扩展视图](#slot)-->
                 <slot name="extra"></slot> 
            </a-flex>
      </a-flex>
   
</template>
 
<style scoped>
 
  
 
</style>