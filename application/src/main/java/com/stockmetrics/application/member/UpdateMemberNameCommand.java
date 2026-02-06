package com.stockmetrics.application.member;

public record UpdateMemberNameCommand(String email, String newName) {
}
