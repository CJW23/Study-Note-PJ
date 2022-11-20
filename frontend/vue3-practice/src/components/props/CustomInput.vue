<template>
  <input :value='modelValue' @input="e => $emit('update:modelValue', e.target.value)"/>
</template>

<script>
export default {
  //내부적으로 해당 emits, props를 선언해서 부모 컴포넌트의 v-model로 값을 전달 할 수 있음
  emits: ['update:modelValue'], //내부에 이벤트가 있을듯
  props: ['modelValue'],  //v-model에서 전달해줄꺼고
  name: " CustomInput",
  setup() {
    return {

    }
  }
}
</script>