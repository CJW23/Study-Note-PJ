<template>
  <h1><slot name="change"></slot></h1>
  <h2><slot name="change2"></slot></h2>
  <!-- 상위 컴포넌트에서 slot이 정의되어 있지 않다면 없는 것 처리 -->
  <slot v-if="$slots.test" name="test">default value</slot>
  <!-- setup의 slot를 사용할 수도 있음-->
  <slot v-if="isSlot" name="test4">default value</slot>
  <slot name="test2"></slot>
  <slot name="test3" :childData="data"></slot>
  <button>
    <slot>C Button</slot>
  </button>

</template>

<script>
import {computed, ref} from "vue";

export default {
  name: "CustomButton",
  setup(props, {slots}) {
    const data = ref('childData이다');
    const isSlot = computed(() => !!slots.test)
    return {
      data,
      isSlot
    }
  }
}
</script>

<style scoped>

</style>