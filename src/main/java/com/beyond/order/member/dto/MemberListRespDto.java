package com.beyond.order.member.dto;

import com.beyond.order.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MemberListRespDto {
    private Long id;
    private String name;
    private String email;

    public static MemberListRespDto fromEntity(Member member){
        return MemberListRespDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}
