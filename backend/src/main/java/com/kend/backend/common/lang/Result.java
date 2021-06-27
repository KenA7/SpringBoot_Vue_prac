package com.kend.backend.common.lang;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private int code;
    private String msg;
    private Object data;

    public static Result succ(Object data){
        return success(200,"success",data);
    }

    public static Result success(int code,String msg,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;

    }

    public static Result fail(String msg){
        return fail(404,msg);
    }

    public static Result fail(int code, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
