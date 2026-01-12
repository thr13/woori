package com.woori.back.domain.cafe.exception;

public class InvalidCafeStatusException extends RuntimeException {
    public InvalidCafeStatusException(String message) {
        super(message);
    }
}
