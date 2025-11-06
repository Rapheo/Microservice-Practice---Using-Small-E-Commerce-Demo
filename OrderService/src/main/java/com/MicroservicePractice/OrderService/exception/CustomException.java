package com.MicroservicePractice.OrderService.exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {

    private String errorCode;
    private int status;

    public CustomException(String massage, String errorCode, int status) {
        super(massage);
        this.errorCode = errorCode;
        this.status = status;
    }


}
