import Vue from 'vue'
import CoMessage from './message'

const MessageProfile = Vue.extend(CoMessage)
let instands = []// 消息队列

export default (option) => { // 消息工厂
  let parame = {}
  if (typeof option === 'string') {
    parame.message = option
  } else if (typeof option === 'object') {
    if ('delay' in option) {
      if (typeof option.delay === 'number') {
        parame.delay = option.delay
      }
    }
    if ('message' in option) {
      parame.message = option.message
    }
    if ('type' in option) {
      if (['success', 'warning', 'info', 'error'].indexOf(option.type) !== -1) {
        parame.type = option.type
      }
    }
    if ('icon' in option) {
      parame.icon = option.icon
    }
    parame.showClose = 'showClose' in option && !(option.center) ? option.showClose : false
    parame.center = option.center ? option.center : false
  }
  let com = new MessageProfile({
    data () {
      return {
        index: instands.length,
        ...parame
      }
    }
  }).$mount()
  document.body.appendChild(com.$el)
  instands.push(com)
}

export let pop = (uid) => { // 删除消息队列
  let sis = instands.findIndex(function (v, i) {
    return v._uid === uid
  })
  for (let i = sis; i < instands.length; i++) {
    if (!instands[i]) {
      break
    }
    if (instands[i]._uid === uid) {
      new Promise((resolve, reject) => {
        instands[i].$data.vShow = false
        resolve()
      }).then(() => {
        clearTimeout(instands[i].$data.timer)
        instands[i].$destroy()
        instands.splice(i, 1)
        i--
      })
      continue
    }
    instands[i].$data.index -= 1
  }
}
