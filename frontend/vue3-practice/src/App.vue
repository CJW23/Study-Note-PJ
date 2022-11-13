<template>
  <div>
    <button @click="changeMsg">Click</button>
    <p>isRefMsg: {{ isRefMsg }}</p>
    <p>{{ reactiveMsg }}</p>
    <p>{{ normalMsg }}</p>

    <!-- v-once: 데이터가 변경되어도 반응하지 않게 함 -->
    <div v-once>
      {{ reactiveMsg }}
      {{ reactiveMsg2 }}
    </div>

    <!-- v-html을 사용하여 텍스트를 html로 인식가능 -->
    <div v-html="vHtmlEx"></div>

    <!-- input 속성을 객체로 주입 가능 -->
    <input v-model="inputEx" :="attrs"/>
    <button @click="printInput">Click</button>

    <div>
      {{ state2 }}
      <button @click="clickState2">ClickState2 (바뀌지 않음)</button>
    </div>
    <div>
      refState
      {{ refState }}
      arrState
      <!-- template에서는 .value 안해도 됨 -->
      {{arrState[0]}}
      <button @click="clickRefState">ClickRefState</button>
      <button @click="clickReactiveRefState">ClickReactiveRefState</button>
      <button @click="clickArrState">ClickArrState</button>
    </div>

    <div>
      <ComputedPractice/>
    </div>
    <div>
      <VMemoPractice/>
    </div>
    <div>
      <EventPractice/>
    </div>
    <div>
      <VModelPractice/>
    </div>
  </div>
</template>
<script>
import {isRef, onMounted, reactive, ref} from "vue";
import ComputedPractice from '@/components/ComputedPractice.vue';
import VMemoPractice from "@/components/VMemoPractice.vue";
import EventPractice from "@/components/EventPractice.vue";
import VModelPractice from "@/components/VModelPractice.vue";

export default {
  components: {VModelPractice, EventPractice, VMemoPractice, ComputedPractice},
  /**setUp() hook은 Composition API 사용을 위한 진입점 역할
   * 컴포넌트 인스턴스가 *생성되기전*에 호출
   * 첫번째 매개변수에 props올 수 있음
   * 두번째 매개변수는 context ($attrs, $slots, $emit 등 기존 유용한 속성 사용 가능)
   */
  setup(/*props, context*/) {
    //DOM이 생성된 시점
    onMounted(() => {
      console.log("mounted");
    })

    const normalMsg = 'Hello World';
    //반응형 변수를 설정
    const reactiveMsg = ref('Hello World');
    const reactiveMsg2 = ref("Hello World");
    //반응형 변수인지 체크
    const isRefMsg = isRef(reactiveMsg);
    const vHtmlEx = "<h1>Hi</h1>";
    const inputEx = ref("inputEx");
    const attrs = ref({
      type: 'password',
      value: '123456',
      disabled: true
    });
    //아래처럼 object인 ref 접근 가능
    console.log(attrs.value.type);

    const changeMsg = () => {
      reactiveMsg.value = reactiveMsg.value + "!";
      reactiveMsg2.value = reactiveMsg2.value + "!";
    }

    const printInput = () => {
      alert(inputEx.value);
    }

    /*
    ref vs reactive
    ref는 기본타입을 반응형으로 만들때 사용
    reactive는 객체타입을 반응형으로 만들 때 사용
     */
    const refState = ref(0);
    const arrRef = ref(0);
    const state = reactive({
      count: 0,
      refState,  //ref를 reactive 넣는 것도 가능
    });
    const arrState = reactive([arrRef]);

    let state2 = reactive("str");
    //이 경우 아예 주소값이 바뀌어버리므로 반응형이 사라져버린다.
    const clickState2 = () => state2 = state2 + "!";
    const clickRefState = () => refState.value += 1;
    //이 경우 .value를 사용하지 않고 바로 접근이 가능함
    const clickReactiveRefState = () => state.refState += 1;
    //Array로 들어가있는 경우에는 .value를 사용해야함
    const clickArrState = () => arrState[0].value += 1;

    return {
      reactiveMsg,
      reactiveMsg2,
      normalMsg,
      changeMsg,
      isRefMsg,
      vHtmlEx,
      inputEx,
      printInput,
      attrs,
      state,
      state2,
      refState,
      clickState2,
      clickRefState,
      clickReactiveRefState,
      arrRef,
      arrState,
      clickArrState
    }
  }
}
</script>