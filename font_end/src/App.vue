<template>
  <Navbar />
  <router-view/>
</template>

<script>
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap'
import Navbar from './components/NavBar.vue'

export default {
  name: "App",
  components: {
    Navbar,
  }
}
</script>

<style>

</style>
