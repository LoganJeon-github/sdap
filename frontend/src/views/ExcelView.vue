<template>
  <main>
    <h1>{{ counter }}</h1>
    <h1>{{ test.items }}</h1>
    <VBtn @click="inc"> Insert Test</VBtn>

  </main>
</template>

<script>
import { computed } from 'vue';
import { useStore } from 'vuex';
export default {
  data() {
    return {
    }
  },
  setup() {
    const store = useStore();
    const counter = computed(()=> store.state.counter);
    const test = computed(()=> store.getters);
    const inc = () => store.commit("setCounter", counter.value + 1);
    console.log("counter:? ", counter);
    return { counter , inc, test};
  },
  created() {
    
  },
  methods: {
    testButton: function(){
      this.$axios.post('/test').then((response)=> {
        console.log(response);
      });
    }
  },
}
</script>

<style>

</style>