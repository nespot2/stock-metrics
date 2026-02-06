# Encode password

## Goal
- Passwords can be encoded using BCrypt.
- Encoded passwords can be verified against raw passwords.

## Test plan
> Implement exactly one unchecked test at a time.

- [x] shouldEncodeRawPassword()
- [x] shouldReturnTrueWhenRawPasswordMatchesEncodedPassword()
- [x] shouldReturnFalseWhenRawPasswordDoesNotMatchEncodedPassword()

## Notes (non-executable)
- Password encoding belongs in the adapter/security module. If the module does not exist, create it.
- Spring Security Crypto does not exist, add it to build.gradle.kts
- A shared PasswordEncoder bean should be added in adapter/security module