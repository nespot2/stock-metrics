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

- [x] Test: create a test code for spring restdoc for stock registration endpoint
- [x] Test: write an asciidoc for stock registration endpoint


## Feature 2: Stock Modification(주식 수정)

### 2.1 modify name in domain layer

- [x] Test: modify a name in Stock entity (You should use StockFixture to create a stock)

### 2.2 modify name in application layer

- [x] Test: modify a name using service (You don't use StockFixture)
- [x] Test: reject if stock does not exist

### 2.3 modify name in controller in adapter layer

- [x] Test: should return 200 OK on successful stock name update
- [x] Test: should return 404 Not Found if stock does not exist

### 2.4 create a spring restdoc for api documentation in bootstrap layer

- [x] Test: create a test code for spring restdoc for stock modification endpoint
- [x] Test: write an asciidoc for stock modification endpoint

## Feature 3: Stock Deletion(주식 삭제)

### 3.1 delete a stock in domain layer

- [x] Test: delete a stock(change the status of stock to DELETED)

### 3.2 delete a stock in application layer

- [x] Test: delete a stock(change the status of stock to DELETED)
- [x] Test: reject if stock does not exist

### 3.3 delete a stock in controller in adapter layer

- [x] Test: should return 200 OK on successful stock deletion
- [x] Test: should return 404 Not Found if stock does not exist

### 3.4 create a spring restdoc for api documentation in bootstrap layer

- [x] Test: create a test code for spring restdoc for stock deletion endpoint
- [x] Test: write an asciidoc for stock deletion endpoint