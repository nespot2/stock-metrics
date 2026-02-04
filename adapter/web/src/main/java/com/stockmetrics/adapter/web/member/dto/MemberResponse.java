package com.stockmetrics.adapter.web.member.dto;

import com.stockmetrics.domain.member.Member;

public record MemberResponse(String email, String name) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getEmail(), member.getName());
    }
}
