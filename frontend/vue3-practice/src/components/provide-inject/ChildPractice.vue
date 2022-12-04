<template>
  <input v-model="injectText"/>
  <!-- 읽기 전용이라 변경시 warning -->
  <input placeholder="readonly" v-model="readonlyText" />
  <input placeholder="update" :value="updateText" @input="update" />
  <br>
  <ChildOfChildPractice />
</template>

<script>
import ChildOfChildPractice from "@/components/provide-inject/ChildOfChildPractice.vue";
import {inject} from "vue";
export default {
  name: "ChildPractice",
  components: {ChildOfChildPractice},
  setup() {
    //값이 안넘어오면 default 값 설정 가능 -> 아예 provide 가 없어야 작동
    const injectText = inject('provide-text', 'default value')
    const readonlyText = inject('readonly-text');
    const {updateText, update} = inject('update-text');
    return {
      injectText,
      readonlyText,
      updateText,
      update
    }
  }
}
</script>