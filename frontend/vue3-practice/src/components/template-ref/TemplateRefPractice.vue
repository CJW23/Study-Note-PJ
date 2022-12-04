<template>
  <li v-for="(i, index) in arr" :key="index" ref="refArr">{{ i }}</li>
  <h1>$Parent Test</h1>
  <TemplateRefChildPractice/>
</template>

<script setup>
import {onMounted, ref} from "vue";
import TemplateRefChildPractice from "@/components/template-ref/TemplateRefChildPractice.vue";

const arr = ['a', 'b', 'c'];
const refArr = ref([]);
onMounted(() => {
  refArr.value.forEach(r => console.log(r));
})
//script setup 사용시 $parent에서 속성을 못가져옴
//아래와 같이 defineExpose를 사용해 정의를 해줘야함
defineExpose({
  arr,
  refArr
});
</script>
<!--
<script>
import {onMounted, ref} from "vue";
import TemplateRefChildPractice from "@/components/template-ref/TemplateRefChildPractice.vue";

export default {
  name: "TemplateRefPractice",
  components: {TemplateRefChildPractice},
  setup() {
    const arr = ['a','b','c'];
    //ref를 사용함으로써 li 요소가 저장된다
    const refArr = ref([]);
    onMounted(() => {
      refArr.value.forEach(r => console.log(r));
    })
    return {
      arr,
      refArr
    }
  }
}
</script>-->
