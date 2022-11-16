<template>
  <input type="text" v-model="a"/>
  <div>
    oldValue: {{ oValue }} <br>
    newValue: {{ nValue }}
  </div>
  <input type="text" v-model="b" placeholder="b"/>
  <input type="text" v-model="c" pattern="c"/>
  <div>
    oldBValue: {{ oB }}<br>
    oldCValue: {{ oC }}<br>
    newBValue; {{ nB }}<br>
    newCValue; {{ nC }}<br>
  </div>
  <div>
    <button @click="h">watchEffectClick</button>
  </div>
</template>

<script>
import {reactive, ref, watch, watchEffect} from "vue";

export default {
  name: "WatchPractice",
  setup() {
    const a = ref('');
    const nValue = ref('');
    const oValue = ref('');

    watch(a, (newValue, oldValue) => {
      nValue.value = newValue;
      oValue.value = oldValue;
    });

    const b = ref('');
    const c = ref('');
    const nB = ref('');
    const nC = ref('');
    const oB = ref('');
    const oC = ref('');

    //배열 값중 하나가 변경되도 watch에 감지됨
    watch([b, () => c.value], ([newB, newC], [oldB, oldC]) => {
      nB.value = newB;
      nC.value = newC;
      oB.value = oldB;
      oC.value = oldC;
    })

    const d = reactive({
      dd: ''
    });
    //하위 속성만 탐지하고 싶으면 getter 방식으로 넘겨야함
    //string만 보내면 인자를 넘긴후에 변경을 알 수 없다
    //하지만 object or 함수를 넘기면 변경을 감지할 수 있다 (함수를 실행하거나 object를 보거나)
    watch((() => d.dd), (newD, oldD) => {
      console.log(oldD, newD);
    })

    const e = ref('바로 실행');
    //immediate 옵션 true인 경우 변경 감지가 안되도 바로 한번 수행
    watch(e, (newV, oldV) => {
      //결과: 바로 실행 undefined
      console.log(newV, oldV);
    }, {immediate: true})

    const f = ref('');
    const g = ref('');
    const h = () => {
      f.value = f.value += '!';
      g.value = g.value += '!';
    }
    //최초 한번 무조건 수행
    //속성을 명시하지 않고 함수내에 들어있는 속성들을 감지
    watchEffect(() => {
      console.log("watchEffect")
      console.log(f.value);
      console.log(g.value);
    })
    return {
      a, nValue, oValue, b, c, nB, nC, oB, oC, d, h
    }
  }
}
</script>