package com.woori.back.domain.stamp.dto;

import com.woori.back.domain.stamp.entity.Stamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StampCreateResponse {
    private Long stampId;
    private Long memberId;
    private Long cafeId;
    private int amount;
    private int total;

    public static StampCreateResponse from(Stamp stamp) {
        return new StampCreateResponse(
                stamp.getId(),
                stamp.getMember().getId(),
                stamp.getCafe().getId(),
                stamp.getAmount(),
                stamp.getTotal()
        );
    }
}
