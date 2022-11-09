<template>
  <h1>Computed Test</h1>
  <div>{{ computedText }}</div>
  <div>{{ computedText }}</div>
  <div>{{ normalText() }}</div>
  <div>{{ normalText() }}</div>
  <div>{{ computedSet }}</div>
  <button @click="click">Click</button>
  <button @click="click2">Click2</button>
  <button @click="computedSet='xcxc'">click</button>
</template>

<script>
import {computed, reactive, ref} from "vue";

export default {
  name: "ComputedPractice",
  setup() {
    const text = ref('test');
    const click = () => text.value += '!';

    //computed, 함수 결과는 같지만 computed는 값이 캐시됨
    //-> computedText를 여러곳에서 사용해도 재호출 안함
    //-> normalText는 재호출 함
    const computedText = computed(() => {
      console.log("computed");
      return 'computed: ' + text.value;
    })
    const normalText = () => {
      console.log("normal");
      return 'normal: ' + text.value;
    }

    let computedSet = computed({
      get() {
        return "get test: " + text.value;
      },
      set(val) {
        text.value = val;
      }
    });
    const click2 = () => computedSet.value = "computedSet";


    return {
      text,
      computedText,
      normalText,
      click,
      click2,
      computedSet
    }
  }
}
</script>