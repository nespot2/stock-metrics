# Stock System - TDD Plan

- This plan follows Kent Beck's TDD methodology. Each test should be implemented one at a time.
- Mark with [x] when complete.


--- 

## Feature 1: Stock Registration(주식 등록)

### 1.1 Stock Entity

- [x] Test: Should create a Stock with ticker, name and exchange using request class
  - exchange (code: NASDAQ | NYSE | NYSE_AMERICAN)
- [x] Test: Should reject ticker is not following format like '^[A-Z]{1,5}(\.[A-Z])?$'