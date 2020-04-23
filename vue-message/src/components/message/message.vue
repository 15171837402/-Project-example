<template>
  <transition name="v">
    <div @mouseenter="enter" @mouseleave="leave" class="message" v-if="vShow" :class="setClass"  :style="setStyle">
      <i v-if="this.icon" :class="icon"></i>
      <span :class="{[`message-color--${type}`]:true}">{{message}}</span>
      <span :class="{[`message-color--${type}`]:true}" v-show="showClose" class="close" @click="close">X</span>
    </div>
  </transition>
</template>
<script>
import {pop} from './index'
export default {
  name: 'Message',
  data () {
    return {
      icon: '',
      delay: 5000,
      message: '默认消息',
      type: 'info',
      center: false,
      showClose: false,
      vShow: false,
      timer: null
    }
  },
  methods: {
    fn () {
      console.log(this.message)
    },
    close () {
      this.detach()
    },
    enter () {
      clearTimeout(this.timer)
    },
    leave () {
      this.bind()
    },
    detach () {
      pop(this._uid)
    },
    bind () {
      this.vShow = true
      if (this.delay === 0) {
        this.detach()
        return
      }
      this.timer = setTimeout(() => {
        this.detach()
      }, this.delay)
    }
  },

  computed: {
    setStyle () {
      return {
        'top': (this.index) * 50 + 30 + 'px'
      }
    },
    setClass () {
      return {
        [`message-type--${this.type}`]: true,
        'message-center': this.center
      }
    }
  },
  mounted () {
    this.bind()
  }

}
</script>
<style>
.message {
  display: flex;
  padding: 10px 15px;
  justify-content: space-between;
  font-size: 1em;
  box-sizing: border-box;
  min-width: 150px;
  width: 30%;
  background-color: #edf2fc;
  text-align: center;
  color: #67c23a;
  position: absolute;
  border-radius: 5px;
  transition: all 0.5s;
  left: 50%;
  top:0;
  border: 1px solid #ebeef5;
  transform:translateX(-50%);
}
.message-color--info {
    color: #909399;
}
.message-color--success {
    color: #67c23a;
}
.message-color--warning {
    color: #e6a23c;
}
.message-color--error {
    color: #f56c6c;
}
.message-type--success {
    background-color: #f0f9eb;
    border-color: #e1f3d8;
}
.message-type--warning {
    background-color: #fdf6ec;
    border-color: #faecd8;
}
.message-type--error {
    background-color: #fef0f0;
    border-color: #fde2e2;
}
.close{
  border-radius: 50%;
  text-align: center;
  float: right;
  cursor: pointer;
}
.v-enter,.v-leave{
  transform:translateX(-50%) translateY(-150%);
  opacity: 0;

}
.v-enter-active,.v-leave-active{
  transition: all 0.5s;
}
.v-enter-to{
  transform: translateX(-50%);

}
.v-leave-to{
  transform:translateX(-50%) translateY(-150%);
  opacity: 0;
}
.message-center{
  justify-content: center;
}
</style>
