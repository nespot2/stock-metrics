package com.stockmetrics.application.member;

import com.stockmetrics.application.provided.member.MemberDeleteUseCase;
import com.stockmetrics.application.provided.member.MemberRegistrationUseCase;
import com.stockmetrics.application.required.member.MemberRepository;
import com.stockmetrics.domain.member.CreateMemberRequest;
import com.stockmetrics.domain.member.Member;
import com.stockmetrics.domain.member.MemberNotFoundException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements MemberRegistrationUseCase, MemberDeleteUseCase {

    private final MemberRepository memberRepository;

    @Override
    public Member register(RegisterMemberCommand command) {
        CreateMemberRequest request = new CreateMemberRequest(command.email(), command.name(), command.snsType(), command.password());
        Member member = Member.create(request);
        
        if (memberRepository.findByEmail(command.email()).isPresent()) {
            throw new IllegalStateException("Member with email " + command.email() + " already exists");
        }

        return memberRepository.save(member);
    }

    public Member updateName(UpdateMemberNameCommand command) {
        Member member = memberRepository.findByEmail(command.email())
                .orElseThrow(() -> new NoSuchElementException("Member with email " + command.email() + " not found"));
        member.modifyName(command.newName());
        return member;
    }

    @Override
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
        member.delete();
    }
}
