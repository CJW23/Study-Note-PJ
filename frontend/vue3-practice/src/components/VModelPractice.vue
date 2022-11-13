<template>
  <!-- v-model: 양방향 바인딩이라 함 -->
  <input :value="str" @input="inputStr" placeholder="v-model 테스트"/>
  <input v-model="str" />
  <button @click="print">출력</button>
  <div>
    <input type="checkbox" value="a" v-model="arr" /> a
    <input type="checkbox" value="b" v-model="arr" /> b
    {{ printArr }}<br>
    <button @click="click">awd</button><br>
    {{test}}
  </div>
</template>

<script>
import {computed, reactive, ref} from "vue";

export default {
  name: "VModelPractice",
  setup() {
    const api = () => {return {a:12345}};
    const str = ref('');
    const arr = ref([]);
    const test = reactive(api());

    console.log(test.a);
    const inputStr = (event) => str.value = event.target.value;
    const print = () => alert(str.value);
    const printArr = computed(() => arr.value.join(','));
    return {
      str,
      inputStr,
      print,
      arr,
      printArr,
      api
    }
  }
}
</script>