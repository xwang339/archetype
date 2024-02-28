package com.lixin.dal.model;

import java.io.Serializable;


public class Result<T> implements Serializable {
    private static final long serialVersionUID = 3136093880967040057L;
    protected T model;
    protected int code;
    protected String message;

    public Result() {
        this.model = null;
        this.code = 0;
        this.message = "";
    }

    public Result(T model) {
        this.code = 0;
        this.message = "";
        this.model = model;
    }

    public Result(int code, String message) {
        this.model = null;
        this.code = 0;
        this.message = "";
        if (code > 0) {
            code *= -1;
        }

        this.code = code;
        this.message = message;
    }

    public Result(int code) {
        this(code, "failed");
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result(code, message);
    }

    public static <T> Result<T> error(String message) {
        return error(-1, message);
    }

    public static <T> Result<T> normal(T object) {
        return new Result(object);
    }

    public static <T> Result<T> normal() {
        return new Result((Object)null);
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<>(object);
        result.code = 1;
        result.message = "successed";
        return result;
    }

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 1;
        result.message = "successed";
        return result;
    }

    public boolean isSuccessed() {
        return this.code >= 0;
    }

    public boolean isFailed() {
        return !this.isSuccessed();
    }

    public T getModel() {
        return this.model;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String toJson() {
        StringBuilder builder = new StringBuilder();
        builder.append("{").append("code:").append(this.code).append(",").append("message:").append("\"").append(this.message).append("\",").append("failed:").append(this.isFailed()).append(",").append("successed:").append(this.isSuccessed());
        if (null != this.model) {
            builder.append(",").append("model:").append(this.model.toString());
        }

        builder.append("}");
        return builder.toString();
    }
}
