package com.stockmetrics.domain.member;

public class MemberFixture {

    public static Member createMember() {
        return Member.create(new CreateMemberRequest("test@example.com", "Test User", SnsType.NAVER, null));
    }

    public static Member createMember(String email, String name) {
        return Member.create(new CreateMemberRequest(email, name, SnsType.NAVER, null));
    }
}
