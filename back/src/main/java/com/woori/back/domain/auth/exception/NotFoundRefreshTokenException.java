package com.woori.back.domain.auth.exception;

public class NotFoundRefreshTokenException extends RuntimeException {
    public NotFoundRefreshTokenException(String message) {
        super(message);
    }
}