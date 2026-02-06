package com.stockmetrics.domain.member;

import com.stockmetrics.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractEntity {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SnsType snsType;

    private String password;

    private Member(String email, String name, SnsType snsType, String password) {
        validateEmail(email);
        validatePassword(snsType, password);
        this.email = email;
        this.name = name;
        this.snsType = snsType;
        this.password = password;
    }

    public static Member create(CreateMemberRequest request) {
        return new Member(request.email(), request.name(), request.snsType(), request.password());
    }

    private static void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private static void validatePassword(SnsType snsType, String password) {
        if (snsType == SnsType.EMAIL && (password == null || password.isBlank())) {
            throw new IllegalArgumentException("Password is required for EMAIL type member");
        }
    }

    public void modifyName(String name) {
        this.name = name;
    }

}
