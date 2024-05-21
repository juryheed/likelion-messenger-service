package org.mjulikelion.likelionmessengerservice.error.exception;

import org.mjulikelion.likelionmessengerservice.error.ErrorCode;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}