package org.mjulikelion.likelionmessengerservice.error.exception;

import lombok.Getter;
import org.mjulikelion.likelionmessengerservice.error.ErrorCode;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String detail;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.detail = null;
    }
}

