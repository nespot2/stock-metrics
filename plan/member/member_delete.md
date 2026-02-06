# delete member

## Goal
delete a member. you don't delete a member physically, just mark it as deleted using member status

## Preparing before writing code

- If MemberStauts is not exist, create a MemberStatus(OK, DELETED)
- If MemberNotFoundException is not exist, create a MemberNotFoundException

```java
public enum MemberStatus {
    OK, DELETED
}
```

```java
public class MemberNotFoundException extends RuntimeException {
}
```

## Test Cases (domain module)

- [x] delete a member 
  - member.delete() change member status from OK to DELETED  
- [x] throw MemberNotFoundException when MemberStatus is DELETED

## Test Cases (application module)

- [x] delete a member
- [x] throw MemberNotFoundException when not finding a member
- [x] throw MemberNotFoundException when MemberStatus is DELETED

## Test Cases (infra module, bootstarp module)

- [x] when deleting a member, response 200
  - endpoint : @DeleteMapping("/api/members/{id}")
- [x] when throwing MemberNotFoundException, response 400
- [x] write an api document
  - write a test code for restdocs
  - write an asciidoc






