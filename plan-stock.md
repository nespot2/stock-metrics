# Stock System - TDD Plan

- This plan follows Kent Beck's TDD methodology. Each test should be implemented one at a time.
- Mark with [x] when complete.


--- 

## Feature 1: Stock Registration(주식 등록)

### 1.1 Stock Entity

- [x] Test: Should create a Stock with ticker, name and exchange using request class
  - exchange (code: NASDAQ | NYSE | NYSE_AMERICAN)
- [x] Test: Should reject ticker is not following format '^[A-Z]{1,5}(\.[A-Z])?$'

### 1.2 register a stock in application layer

- [x] Test: reject invalid ticker format
- [x] Test: reject if stock already exists
- [x] Test: register a stock

### 1.3 create a controller in adapter layer and write a test code in bootstrap layer

- [x] Test: should return 201 Created on successful stock registration. response is a StockResponse
- [x] Test: should return 400 Bad Request on invalid ticker format

### 1.4 create a spring restdoc for api documentation in bootstrap layer

- [ ] Test: create a test code for spring restdoc for stock registration endpoint
- [ ] Test: write a asciidoc for stock registration endpoint