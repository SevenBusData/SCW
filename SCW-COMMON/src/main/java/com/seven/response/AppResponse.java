package com.seven.response;

import com.seven.enums.ResponseCodeEnume;

/**
 * @author: seven
 * @since: 2024/7/10 17:40
 */
public class AppResponse<T> {

    private Integer code;
    private String msg;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 快速响应成功
     *
     * @param data
     * @return
     */
    public static <T> AppResponse<T> ok(T data) {
        AppResponse<T> resp = new AppResponse<T>();
        resp.setCode(ResponseCodeEnume.SUCCESS.getCode());
        resp.setMsg(ResponseCodeEnume.SUCCESS.getMsg());
        resp.setData(data);
        return resp;
    }

    /**
     * 快速响应失败
     */
    public static <T> AppResponse<T> fail(T data) {
        AppResponse<T> resp = new AppResponse<T>();
        resp.setCode(ResponseCodeEnume.FAIL.getCode());
        resp.setMsg(ResponseCodeEnume.FAIL.getMsg());
        resp.setData(data);
        return resp;
    }
}