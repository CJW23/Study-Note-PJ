<template>
  <input v-model="text">
  <input placeholder="readonly" v-model="readOnlyText">
  <input placeholder="update" v-model="updateText">
  <br>
  <ChildPractice />
</template>

<script>
import ChildPractice from "@/components/provide-inject/ChildPractice.vue";
import {provide, readonly, ref} from "vue";

export default {
  name: "ProvideInjectPractice",
  components: {ChildPractice},
  setup() {
    const text = ref('');
    const readOnlyText = ref('');
    const updateText = ref('');
    //변경 메소드도 같이 전달해서 한곳에서 처리하게끔
    const update = (value) => {
      console.log(value.target.value);
      updateText.value = value.target.value;
    }
    provide('provide-text', text);
    provide('readonly-text', readonly(readOnlyText));
    provide('update-text',{updateText, update});

    return {
      text,
      readOnlyText,
      update,
      updateText
    }
  }
}
</script>

<style scoped>

</style>