package com.woori.back.domain.stamp.dto;

import com.woori.back.domain.stamp.entity.Stamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StampResponse {
    private Long id;
    private int amount;
    private int total;

    public static StampResponse from(Stamp stamp) {
        return new StampResponse(stamp.getId(), stamp.getAmount(), stamp.getAmount());
    }
}
