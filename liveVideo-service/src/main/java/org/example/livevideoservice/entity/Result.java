package org.example.livevideoservice.entity;

public class Result {
    private int status;
    private String message;
    private Object data;

    public Result(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public static Result success(Object data){
        return new Result(200,"success",data);
    }

    public static Result success(){
        return new Result(200,"success",null);
    }

    public static Result error(String message){
        return new Result(500,message,null);
    }
}
