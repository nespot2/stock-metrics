## Goal
Create a JWT utility (HS256) in the adapter/jwt module that can generate tokens for a subject with expiration and validate/parse them.

## Approach
- Use HS256 with a symmetric secret provided via configuration; default expiration duration configurable.
- Place utility in adapter/jwt; expose simple methods: generate(subject, expiration?) and parse/verify(token) returning subject/claims or error.
- Favor small, focused unit tests (red/green/refactor) and minimal surface area; no broader auth flows yet.

## Workplan
- [x] Confirm target package/naming in adapter/jwt and configuration source for secret/ttl.
  - Module: `adapter/jwt`
  - Package: `com.stockmetrics.adapter.jwt`
  - Config: `jwt.secret` and `jwt.expiration-seconds`
- [x] Define API surface for the util (generate, parse/verify) and error handling contract.
  - `JwtProvider.generateToken(subject: String): String`
  - `JwtProvider.parseToken(token: String): String` (returns subject or throws)
  - Exceptions: `InvalidTokenException` for invalid/expired/blank tokens
- [x] Add unit tests covering: generate token (subject/exp), parse valid token, reject expired/invalid signature/blank token.
- [x] Implement utility with HS256 signing, expiration enforcement, and safe parsing.
- [x] Add configuration properties (secret, default ttl) and wire into util creation.
- [x] Document usage briefly (Javadoc/readme snippet) and ensure module wiring/build passes.

## Notes / Open Questions
- Secret management: confirm storage (application properties vs. env override).
- Time source: prefer injectable Clock for testability.
- Do we need additional standard claims (iss/aud) now? Currently assuming only sub + exp.