package com.woori.back.domain.coupon.exception;

public class NotFoundCouponException extends RuntimeException {
    public NotFoundCouponException(String message) {
        super(message);
    }
}
