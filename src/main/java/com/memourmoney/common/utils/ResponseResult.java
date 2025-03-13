package com.memourmoney.common.utils;

import lombok.Data;

@Data
public class ResponseResult<T> {
    private int code;
    private String msg;
    private T data;

    // 私有构造方法
    private ResponseResult() {}

    // 成功返回
    public static <T> ResponseResult<T> success() {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMessage());
        return result;
    }

    // 成功返回，带数据
    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    // 失败返回
    public static <T> ResponseResult<T> error() {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(ResultCode.ERROR.getCode());
        result.setMsg(ResultCode.ERROR.getMessage());
        return result;
    }

    // 自定义错误
    public static <T> ResponseResult<T> error(String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(ResultCode.ERROR.getCode());
        result.setMsg(message);
        return result;
    }

    // 自定义错误码和消息
    public static <T> ResponseResult<T> error(ResultCode resultCode) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(resultCode.getCode());
        result.setMsg(resultCode.getMessage());
        return result;
    }
}
