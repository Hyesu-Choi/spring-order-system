package com.beyond.order.dto.member;

import com.beyond.order.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MemberMyinfoResqDto {
    private Long id;
    private String name;
    private String email;

    public static MemberMyinfoResqDto fromEntity(Member member) {
        return MemberMyinfoResqDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }

}
