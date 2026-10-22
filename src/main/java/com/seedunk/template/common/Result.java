package com.seedunk.template.common;

 
public class Result<T> {
    private Integer code;
    public Integer getCode(){
        return this.code;

    }
    public void setCode(Integer code){
        this.code=code;
    }
    private String message;
    public void setMessage(String message){
          this.message=message;
    }
    public String getMessage(){
        return this.message;
    }
    private T data;
    public T getData(){
        return this.data;
    }

    public void setData(T data){
         this.data=data;
    }

    
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }
    
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
    
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
