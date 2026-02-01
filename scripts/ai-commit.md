---
allowed-tools:
  - Bash(git status:*)
  - Bash(git diff:*)
  - Bash(git add:*)
  - Bash(git commit:*)
  - Bash(git branch:*)
  - Bash(git log:*)
description: Automatically generate a Korean Conventional Commit message and commit all changes
---

## Context
- Current branch: !`git branch --show-current`
- Git status: !`git status --porcelain`
- Recent commits: !`git log --oneline -5`
- Full diff (staged + unstaged): !`git diff HEAD`

## Rules (STRICT)
1) If there are ANY changes, you MUST create a commit.
2) Commit messages MUST follow Conventional Commits:
    - Format: <type>(<scope>): <subject>
    - Scope is optional but recommended.
3) The commit <subject> MUST be written in Korean.
4) The subject MUST be imperative and concise.
5) If the changes clearly represent multiple independent concerns:
    - STOP
    - Explain why
    - Ask whether to split into multiple commits
6) DO NOT ask for confirmation unless rule #5 applies.

## Commit Type Guidance
- feat: 새로운 기능
- fix: 버그 수정
- refactor: 리팩터링 (동작 변경 없음)
- perf: 성능 개선
- test: 테스트 추가/수정
- docs: 문서 변경
- build: 빌드 설정
- ci: CI 설정
- chore: 기타 변경

## Task
1) If `git status --porcelain` is empty:
    - Say: "커밋할 변경 사항이 없습니다."
    - Stop immediately.

2) Analyze the diff and infer:
    - The primary intent of the change
    - The most appropriate Conventional Commit type
    - A reasonable scope (module, layer, feature name)

3) Generate ONE commit message that strictly follows:
    - Conventional Commits format
    - Korean subject
    - Clear intent

4) Stage ALL changes:
    - Run: `git add -A`

5) Create the commit:
    - Run: `git commit -m "<generated message>"`

6) Show final result:
    - Run: `git status --porcelain`
    - Report the commit message used.