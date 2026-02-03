package com.stockmetrics.domain.member;

import com.stockmetrics.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    private Member(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static Member create(CreateMemberRequest request) {
        return new Member(request.email(), request.name());
    }

}
