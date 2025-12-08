package com.codeit.library.dto.response;

import com.codeit.library.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponse {

    private Long id;
    private String name;
    private String email;

    public static MemberResponse from(Member member) {
        return new MemberResponse(
            member.getId(),
            member.getName(),
            member.getEmail()
        );
    }
}

