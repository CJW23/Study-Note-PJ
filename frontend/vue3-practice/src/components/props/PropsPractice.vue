<template>
  <PropsChildPractice :title="title" @emitTest="emitTest" @emitParamTest="emitParamTest" />
  <CustomInput v-model="vModelTest"/>
  <button @click="clickVModelTest">VModelTest</button>
</template>

<script>
import PropsChildPractice from "@/components/props/PropsChildPractice.vue";
import CustomInput from "@/components/props/CustomInput.vue";
import {ref} from "vue";

export default {
  name: "PropsPractice",
  components: {PropsChildPractice, CustomInput},
  setup() {
    const title = ref('propsPractice');
    const emitTest = () => alert("emitTest");
    const emitParamTest = (param) => alert(param);
    const vModelTest = ref();
    const clickVModelTest = () => alert(vModelTest.value);
    return {
      title,
      emitTest,
      emitParamTest,
      vModelTest,
      clickVModelTest
    }
  }
};
</script>