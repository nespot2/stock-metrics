package com.stockmetrics.adapter.web.member.dto;

import com.stockmetrics.domain.member.SnsType;

public record MemberRegistrationRequest(String email, String name, SnsType snsType, String password) {
}
