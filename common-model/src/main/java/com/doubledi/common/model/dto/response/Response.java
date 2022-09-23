package com.doubledi.common.model.dto.response;


import com.doubledi.common.model.dto.error.ResponseError;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.Instant;

@Data
public class Response<T> implements Serializable {
    protected T data;
    private Boolean ss;
    private int code;
    private String message;
    private long timestamp;

    private RuntimeException runtimeException;

    public Response() {
        timestamp = Instant.now().toEpochMilli();
        ss = true;
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

//    public static <T> Response<T> fail(RuntimeException runtimeException) {
//        Response<T> response = new Response<>();
//        response.setSs(false);
//        response.setRuntimeException(runtimeException);
//        return response;
//    }

    public Response<T> success() {
        ss = true;
        code = 200;
        return this;
    }

    public Response<T> data(T res) {
        this.data = res;
        return this;
    }

    public Response<T> fail(String message, ResponseError responseError) {
        ss = false;
        code = responseError.getCode();
        if (StringUtils.hasText(message)) {
            this.message = message;
        } else {
            this.message = responseError.getMessage();
        }
        return this;
    }

    public Response<T> fail(Exception exception, ResponseError responseError) {
        ss = false;
        code = responseError.getCode();
        this.message = exception.getMessage();
        return this;
    }

    public T getData() {
        if (runtimeException != null) {
            throw runtimeException;
        }
        return data;
    }

    public boolean getSs() {
        if (runtimeException != null) {
            throw runtimeException;
        }
        return ss;
    }

    @Override
    public String toString() {
        return "Response {" +
                "data=" + data +
                ", success=" + ss +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", exception=" + runtimeException +
                '}';
    }


}
