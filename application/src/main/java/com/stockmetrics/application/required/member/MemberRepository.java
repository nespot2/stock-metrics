package com.stockmetrics.application.required.member;

import com.stockmetrics.domain.member.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByEmail(String email);
    Optional<Member> findById(Long id);
}
