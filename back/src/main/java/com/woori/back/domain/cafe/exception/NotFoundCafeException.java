package com.woori.back.domain.cafe.exception;

public class NotFoundCafeException extends RuntimeException {
    public NotFoundCafeException(String message) {
        super(message);
    }
}