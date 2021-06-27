<template>
  <router-view/>
</template>


<script>
export default {
  name: 'App',
  watch:{
    $router(to,from){
      console.log(to)
        let obj = {
          name: to.name,
          title: to.meta.title
        }
      this.$store.commit("addTabs",obj)
      }


  }
}

</script>

<style>
body{
  height: 100%;
  margin: 0;
}
html{
  height: 100%;
}
#app{
  height: 100%;
}

</style>
