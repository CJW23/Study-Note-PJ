<template>
  <button @click="text2 = text2 === 'change' ? 'change2' : 'change'">Change</button>
  <CustomButton>
    <template #[text2]>크기가 변해요</template>
  </CustomButton>
  <CustomButton>
    A Button
    <!-- slot name을 동적으로 전달 가능-->
    <template v-slot:test>test</template>
    <template #test2>test2</template>
    <!-- 하위 컴포넌트의 데이터를 상위 컴포넌트로 보낼때 -->
    <template #test3="awd"><h1>{{awd.childData}}</h1></template>
  </CustomButton>
  <CustomButton>
    B Button
  </CustomButton>
  <CustomButton />
</template>

<script>
import CustomButton from "@/components/slot/CustomButton.vue";
import {ref} from "vue";
export default {
  name: "ButtonGroup",
  components: {CustomButton},
  setup() {
    const text2 = ref('change');
    return {
      text2
    }
  }
}
</script>

<style scoped>

</style>