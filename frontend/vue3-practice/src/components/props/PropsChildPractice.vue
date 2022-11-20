<template>
  <button @click="clickEmitTest">EmitTest</button><br>
  <input v-model="emitParam" placeholder="EmitParamTest Param" />
  <button @click="clickEmitParamTest">EmitParamTest</button>
  <p>{{titles}}</p>
</template>

<script>
import {ref} from "vue";

export default {
  //emit 이벤트 명을 등록해야함
  emits: ['emitTest', 'emitParamTest'],
  name: "PropsChildPractice",
  props:{
    title: {
      type: String
    }
  },
  setup(props, context) {
    //setup 안에서는 props를 통해 접근할 수 있다.
    const titles = props.title;
    const emitParam = ref('');
    //setup 두번째 인자를 사옹해 emit 호출
    const clickEmitTest = () => context.emit('emitTest');
    const clickEmitParamTest = () => context.emit('emitParamTest', emitParam.value);
    return {
      titles,
      clickEmitTest,
      clickEmitParamTest,
      emitParam
    }
  }
}
</script>