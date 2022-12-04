<template>
  <input v-model="provideText"/>
  <!-- 읽기 전용이라 변경시 warning -->
  <input placeholder="readonly" v-model="readonlyText"/>
  <input placeholder="update" :value="updateText" @input="update" />
  <br>
</template>

<script>
import {inject} from "vue";

export default {
  name: "ChildOfChildPractice",
  setup() {
    const provideText = inject('provide-text');
    const readonlyText = inject('readonly-text');
    const {updateText, update} = inject('update-text');

    return {
      provideText,
      readonlyText,
      updateText,
      update
    }
  }
}
</script>