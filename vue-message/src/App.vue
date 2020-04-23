<template>
  <div id="app">
    <img @click="del"
         src="./assets/logo.png">
    <router-view />
    <button @click="one">默认</button>
    <button @click="two">成功</button>
    <button @click="there">警告</button>
    <button @click="four">错误</button>
  </div>
</template>

<script>

import message from '@/components/message/index.js'
export default {
  name: 'App',
  methods: {
    del () {
      message({
        message: '默认',
        showClose: true
      })
    },
    one () {
      this.del()
    },
    two () {
      message({
        message: '成功',
        showClose: false,
        type: 'success'
      })
    },
    there () {
      message({
        message: '警告',
        showClose: true,
        type: 'warning'
      })
    },
    four () {
      message({
        message: '错误',
        showClose: true,
        center: true,
        type: 'error'
      })
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
