package org.mjulikelion.likelionmessengerservice.error.exception;

import org.mjulikelion.likelionmessengerservice.error.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}