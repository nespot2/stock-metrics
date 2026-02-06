package com.stockmetrics.application.member;

import com.stockmetrics.domain.member.SnsType;

public record RegisterMemberCommand(String email, String name, SnsType snsType, String password) {
}
