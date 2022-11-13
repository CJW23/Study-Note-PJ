<template>
  <br>
  <h1>Event 전파</h1>
  <div @click="bubbling">
    <!-- 클릭시 버블링 캡쳐링으로 인해 bubbling click이벤트와 href가 같이 수행된다 -->
    <a href="https://naver.com" @click="click">버블링 테스트</a><br>
    <!-- @click 디렉티브에 prevent, stop을 넣음으로써 버블링, 캡처링 막기 가능 -->
    <a href="https://naver.com" @click.prevent.stop="click">prevent stopPropagation 테스트</a><br>
    <!-- 클릭이 메소드가 한번만 수행됨 -->
    <button @click.once.stop="click">클릭이 무조건 한번만 수행됨</button>
    <br>
    <!-- keydown -->
    <input @click.stop type="text" @keydown.enter="add" />
    <li v-for="(v, i) in arr" :key="i">
      {{v}}
    </li>
  </div>
</template>

<script>
import {reactive} from "vue";

export default {
  name: "EventPractice",
  setup() {
    const arr = reactive([]);
    const bubbling = () => {
      alert("버블링");
    }
    const click = () => {
      alert("click");
    }
    const add = (e) => {
      const value = e.target.value;
      arr.push(value);
    }
    return {
      bubbling,
      click,
      arr,
      add
    }
  }
}
</script>