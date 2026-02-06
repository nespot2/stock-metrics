package com.stockmetrics.adapter.web.member;

import com.stockmetrics.adapter.web.member.dto.MemberRegistrationRequest;
import com.stockmetrics.adapter.web.member.dto.MemberResponse;
import com.stockmetrics.application.member.RegisterMemberCommand;
import com.stockmetrics.application.provided.member.MemberRegistrationUseCase;
import com.stockmetrics.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRegistrationUseCase memberRegistrationUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse register(@RequestBody MemberRegistrationRequest request) {
        RegisterMemberCommand command = new RegisterMemberCommand(request.email(), request.name(), request.snsType(), request.password());
        Member member = memberRegistrationUseCase.register(command);
        return MemberResponse.from(member);
    }
}
