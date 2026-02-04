package com.stockmetrics.application.provided.member;

import com.stockmetrics.application.member.RegisterMemberCommand;
import com.stockmetrics.domain.member.Member;

public interface MemberRegistrationUseCase {
    Member register(RegisterMemberCommand command);
}
