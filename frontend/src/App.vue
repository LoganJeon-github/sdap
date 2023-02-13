<template>
  <v-app>
    <v-main>
      <router-link to="/about">About</router-link> | 
      <router-link to="/excel">Excel</router-link> |
      <router-link to="/file-browser">FileBrowser</router-link>

      <router-view/>
    </v-main>
  </v-app>
</template>

<script>

export default {
  name: 'App',

  data: () => ({
    //
  }),
}
</script>
