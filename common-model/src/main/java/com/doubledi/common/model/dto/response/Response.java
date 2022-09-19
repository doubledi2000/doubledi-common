package com.doubledi.common.model.dto.response;


import com.doubledi.common.model.dto.error.ResponseError;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Response<T> implements Serializable {
    protected T data;
    private boolean success;
    private int code;
    private String message;
    private long timestamp;

    @JsonIgnore
    private RuntimeException exception;

    public Response() {
        timestamp = Instant.now().toEpochMilli();
        success = true;
        code = 200;
    }

    public static <T> Response<T> ok() {
        Response<T> response = new Response<>();
        response.success();
        return response;
    }

    public static <T> Response of(T res) {
        Response response = new Response();
        response.data = res;
        response.success();
        return response;
    }

    public static <T> Response<T> fai(RuntimeException runtimeException) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setException(runtimeException);
        return response;
    }

    public Response<T> success() {
        success = true;
        code = 200;
        return this;
    }

    public Response<T> data(T res) {
        this.data = res;
        return this;
    }

    public Response<T> fail(String message, ResponseError responseError) {
        success = false;
        code = responseError.getCode();
    }

}
