<template>
  <main>
    <VBtn @click="testButton"> Insert Test</VBtn>

  </main>
</template>

<script>

export default {
  data() {
    return {
      
    }
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