package com.woori.back.domain.coupon.exception;

public class NotFoundCouponPolicyException extends RuntimeException {
    public NotFoundCouponPolicyException(String message) {
        super(message);
    }
}
