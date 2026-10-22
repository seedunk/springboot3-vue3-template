<script setup> 
import { useSlots ,defineProps ,ref, onMounted, nextTick } from 'vue'; 
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
  
const targetWidth = ref(120)

  

</script>
<template>

  <component
    :is="mode === 'card' ? 'a-card' : 'div'"
    :bodyStyle="{padding:'16px'}"
    :bordered="false"
     >
      <div  v-if="$slots.content" style="height: 100%;">  
          <slot name="content"></slot> 
      </div> 
     <a-skeleton v-else :loading="false"    :paragraph="{ rows: 3 }"> 
       <a-flex  align="center"   > 
             <a-flex  id="targetEl" align="center" gap="12" class="card-content"  > 
                <div class="actions-area" v-if="$slots.icon">
                    <div class="icon-circle"> 
                        <!--[图标视图](#slot)-->
                        <slot name="icon"></slot> 
                    </div> 
                </div>  
              <div class="data-column"> 
                <div class="title-text"  >{{ title }}</div>
                <div  v-if="$slots.value">  
                    <!--[数据视图](#slot)-->
                    <div class="number-text" ><slot name="value"></slot> </div> 
                </div> 
                <div class="number-text" v-else>{{ value }}</div> 
                <div class="trend-row" v-if="$slots.description">  
                  <!--[附加数据视图](#slot)-->
                  <slot name="description"></slot> 
                </div>
              </div> 
            </a-flex>
             <div  v-if="$slots.right" :style="{ left: targetWidth + 'px' }" style="height: 100%;  position: absolute;  top: 0;  right: 0;   bottom: 0;  " >  
                    <slot  name="right"></slot>  
             </div>
       </a-flex>
    </a-skeleton>
  </component>
</template>
 
<style scoped>
 
 
.icon-circle {
  width: 64px;
  height: 64px;
  background: #273649; 
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;  
}

  
.data-column {
  display: flex;
  flex-direction: column;
  gap: 4px;
}
 
.title-text {
  font-size: 14px;
  color: #8ba4c7;
} 

.trend-row {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 13px;
  color: #8ba4c7;
}
 
</style>