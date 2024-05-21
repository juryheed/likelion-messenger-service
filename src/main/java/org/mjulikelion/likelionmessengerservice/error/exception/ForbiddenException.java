package org.mjulikelion.likelionmessengerservice.error.exception;

import org.mjulikelion.likelionmessengerservice.error.ErrorCode;

//접근 안됨
public class ForbiddenException extends CustomException {
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}