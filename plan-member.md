# Member System - TDD Plan

- This plan follows Kent Beck's TDD methodology. Each test should be implemented one at a time.
- Mark with [x] when complete.
- Scope: member registration/update/deletion mirroring stock plan. Identifier: email; fields: email, name; validate email format.
- Deletion: soft delete via status = DELETED.

---

## Feature 1: Member Registration(회원 등록)

### 1.1 Member Entity

- [x] Test: Should create a Member with email and name using request class
- [x] Test: Should reject email not following format (standard email pattern)

### 1.2 register a member in application layer

- [x] Test: reject invalid email format
- [x] Test: reject if member already exists
- [x] Test: register a member

### 1.3 create a controller in adapter layer and write a test code in bootstrap layer

- [x] Test: should return 201 Created on successful member registration. response is a MemberResponse
- [x] Test: should return 400 Bad Request on invalid email format

### 1.4 create a spring restdoc for api documentation in bootstrap layer

- [x] Test: create a test code for spring restdoc for member registration endpoint
- [x] Test: write an asciidoc for member registration endpoint


## Feature 2: Member Modification(회원 수정)

### 2.1 modify name in domain layer

- [ ] Test: modify a name in Member entity (You should use MemberFixture to create a member)

### 2.2 modify name in application layer

- [ ] Test: modify a name using service (You don't use MemberFixture)
- [ ] Test: reject if member does not exist

### 2.3 modify name in controller in adapter layer

- [ ] Test: should return 200 OK on successful member name update
- [ ] Test: should return 404 Not Found if member does not exist

### 2.4 create a spring restdoc for api documentation in bootstrap layer

- [ ] Test: create a test code for spring restdoc for member modification endpoint
- [ ] Test: write an asciidoc for member modification endpoint

## Feature 3: Member Deletion(회원 삭제)

### 3.1 delete a member in domain layer

- [ ] Test: delete a member(change the status of member to DELETED)

### 3.2 delete a member in application layer

- [ ] Test: delete a member(change the status of member to DELETED)
- [ ] Test: reject if member does not exist

### 3.3 delete a member in controller in adapter layer

- [ ] Test: should return 200 OK on successful member deletion
- [ ] Test: should return 404 Not Found if member does not exist

### 3.4 create a spring restdoc for api documentation in bootstrap layer

- [ ] Test: create a test code for spring restdoc for member deletion endpoint
- [ ] Test: write an asciidoc for member deletion endpoint
