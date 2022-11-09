<template>
  <h1>v-memo practice</h1>
  <!-- str 변경이 감지되야만 렌더링 된다 -->
  <!-- num 변경은 반영이 되지 않다가 str 변경되면 num 변경 반영 -->
  <div v-memo="[str]">
    <div>{{ num }}</div>
    <div>{{ str }}</div>
  </div>
  <!-- 조건으로도 가능한 듯 -->
  <!-- v-for로 1000개를 띄워야할 때 사용하면 성능 개선(1000개 전까지는 렌더링을 안할테니) -->
  <div v-memo="[num % 5 === 0]">
    <div>{{ num }}</div>
  </div>
  <button @click="clickNum">clickNum</button>
  <button @click="clickStr">clickStr</button>
</template>

<script>
import {ref} from "vue";

export default {
  name: "VMemoPractice",
  setup() {
    const num = ref(0);
    const str = ref('str');
    const clickNum = () => num.value++;
    const clickStr = () => str.value += '!';

    return {
      num,str,clickNum,clickStr
    }
  }
}
</script>