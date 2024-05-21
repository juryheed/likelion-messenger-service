package org.mjulikelion.likelionmessengerservice.error.exception;

import org.mjulikelion.likelionmessengerservice.error.ErrorCode;

public class ConflictException extends CustomException {
    public ConflictException(ErrorCode errorCode){
        super(errorCode);
    }
}
