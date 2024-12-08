package com.dev.qlda.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WrapResponse<T> {
    private boolean isSuccess;
    private HttpStatus status;
    private String message;
    private T data;

    public WrapResponse(boolean isSuccess, HttpStatus status, String message) {
        this.isSuccess = isSuccess;
        this.status = status;
        this.message = message;
    }

    public static <T> WrapResponse<T> ok(String message, T data){
        WrapResponse wrapResponse = new WrapResponse();
        wrapResponse.setStatus(HttpStatus.OK);
        wrapResponse.setMessage(message);
        wrapResponse.setData(data);
        wrapResponse.setSuccess(true);
        return wrapResponse;
    }

    public static <T> WrapResponse<T> ok(T data){
        WrapResponse wrapResponse = new WrapResponse();
        wrapResponse.setStatus(HttpStatus.OK);
        wrapResponse.setData(data);
        wrapResponse.setSuccess(true);
        return wrapResponse;
    }

    public static <T> WrapResponse<T> error(String message){
        WrapResponse wrapResponse = new WrapResponse();
        wrapResponse.setStatus(HttpStatus.BAD_REQUEST);
        wrapResponse.setMessage(message);
        wrapResponse.setSuccess(false);
        return wrapResponse;
    }
}
