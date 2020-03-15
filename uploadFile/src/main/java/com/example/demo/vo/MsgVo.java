package com.example.demo.vo;

import java.util.List;

public class MsgVo<T> {
    private int code;
    private String msg;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static MsgVo<Object> ok(String str, Object o){
        return getObjectMsgVo(str, o, 1);
    }

    private static MsgVo<Object> getObjectMsgVo(String str, Object o, int i) {
        MsgVo<Object> objectMsgVo = new MsgVo<>();
        objectMsgVo.setCode(i);
        objectMsgVo.setMsg(str);
        objectMsgVo.setData(o);
        return objectMsgVo;
    }

    public static MsgVo<Object> error(String str, Object o){
        return getObjectMsgVo(str, o, 0);
    }
}
