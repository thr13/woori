package com.woori.back.domain.cafe.dto;

import com.woori.back.domain.cafe.entity.CafeStatus;
import lombok.Getter;

@Getter
public class CafeStatusChangeRequest {
    private CafeStatus cafeStatus; // 변경할 카페 상태
}
