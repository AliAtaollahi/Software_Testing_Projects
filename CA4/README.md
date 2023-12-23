<!-- TOC -->
  * [Sample API Requests](#sample-api-requests)
    * [Authentication](#authentication)
      * [Login](#login)
      * [Signup](#signup)
    * [Buy List](#buy-list)
      * [Get Buy List](#get-buy-list)
      * [Get Purchased List](#get-purchased-list)
      * [Add to Buy List](#add-to-buy-list)
      * [Remove from Buy List](#remove-from-buy-list)
      * [Purchase Buy List](#purchase-buy-list)
    * [Comment](#comment)
      * [Like a Comment](#like-a-comment)
      * [Dislike a Comment](#dislike-a-comment)
    * [Commodity](#commodity)
      * [Get All Commodities](#get-all-commodities)
      * [Get a Specific Commodity](#get-a-specific-commodity)
      * [Rate a Commodity](#rate-a-commodity)
      * [Add a Comment to a Commodity](#add-a-comment-to-a-commodity)
      * [Get Comments for a Commodity](#get-comments-for-a-commodity)
      * [Search Commodities](#search-commodities)
      * [Get Suggested Commodities for a Commodity](#get-suggested-commodities-for-a-commodity)
    * [Provider](#provider)
      * [Get a Specific Provider](#get-a-specific-provider)
      * [Get Commodities Provided by a Provider](#get-commodities-provided-by-a-provider)
    * [User](#user)
      * [Get a Specific User](#get-a-specific-user)
      * [Add Credit to a User](#add-credit-to-a-user)
<!-- TOC -->

## Sample API Requests

Here are some example cURL requests to demonstrate how to interact with the API.

### Authentication
#### Login

```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "amin",
    "password": "456"
  }'
```

#### Signup
```bash
curl -X POST http://localhost:8080/signup \
  -H "Content-Type: application/json" \
  -d '{
    "address": "Tehran",
    "birthDate": "1990-05-15",
    "email": "example@example.com",
    "username": "Reza",
    "password": "secret"
  }'

```

### Buy List

#### Get Buy List

```bash
curl -X POST http://localhost:8080/buy-list \
  -H "Content-Type: application/json" \
  -d '{
    "username": "amin"
  }'
```

#### Get Purchased List

```bash
curl -X POST http://localhost:8080/purchased-list \
  -H "Content-Type: application/json" \
  -d '{
    "username": "amin"
  }'
```

#### Add to Buy List

```bash
curl -X POST http://localhost:8080/buy-list/add \
  -H "Content-Type: application/json" \
  -d '{
    "username": "amin",
    "id": "1"
  }'
```

#### Remove from Buy List

```bash
curl -X POST http://localhost:8080/buy-list/remove \
  -H "Content-Type: application/json" \
  -d '{
    "username": "amin",
    "id": "1"
  }'
```

#### Purchase Buy List

```bash
curl -X POST http://localhost:8080/buy-list/purchase \
  -H "Content-Type: application/json" \
  -d '{
    "username": "amin"
  }'
```

### Comment
#### Like a Comment

```bash
curl -X POST http://localhost:8080/comment/123/like \
  -H "Content-Type: application/json" \
  -d '{
    "username": "amin"
  }'
```


#### Dislike a Comment

```bash
curl -X POST http://localhost:8080/comment/123/dislike \
  -H "Content-Type: application/json" \
  -d '{
    "username": "amin"
  }'
```

Replace `"123"` with the appropriate comment ID.

### Commodity
#### Get All Commodities

```bash
curl -X GET http://localhost:8080/commodities
```

#### Get a Specific Commodity

```bash
curl -X GET http://localhost:8080/commodities/123
```

Replace `"123"` with the appropriate commodity ID.

#### Rate a Commodity

```bash
curl -X POST http://localhost:8080/commodities/123/rate \
  -H "Content-Type: application/json" \
  -d '{
    "rate": 5,
    "username": "amin"
  }'
```

Replace `"123"` with the appropriate commodity ID.

#### Add a Comment to a Commodity

```bash
curl -X POST http://localhost:8080/commodities/123/comment \
  -H "Content-Type: application/json" \
  -d '{
    "username": "example_user",
    "comment": "This is a great product!"
  }'
```

Replace `"123"` with the appropriate commodity ID.

#### Get Comments for a Commodity

```bash
curl -X GET http://localhost:8080/commodities/123/comment
```

Replace `"123"` with the appropriate commodity ID.

#### Search Commodities

```bash
curl -X POST http://localhost:8080/commodities/search \
  -H "Content-Type: application/json" \
  -d '{
    "searchOption": "name",
    "searchValue": "example"
  }'
```

Replace `"name"` with the desired search option ("name", "category", or "provider"), and `"example"` with the search value.

#### Get Suggested Commodities for a Commodity

```bash
curl -X GET http://localhost:8080/commodities/123/suggested
```

Replace `"123"` with the appropriate commodity ID.

### Provider
#### Get a Specific Provider

```bash
curl -X GET http://localhost:8080/providers/123
```

Replace `"123"` with the appropriate provider ID.

#### Get Commodities Provided by a Provider

```bash
curl -X GET http://localhost:8080/providers/123/commodities
```

Replace `"123"` with the appropriate provider ID.

### User

#### Get a Specific User

```bash
curl -X GET http://localhost:8080/users/123
```

Replace `"123"` with the appropriate user ID.

#### Add Credit to a User

```bash
curl -X POST http://localhost:8080/users/123/credit \
  -H "Content-Type: application/json" \
  -d '{
    "credit": 50.0
  }'
```

Replace `"123"` with the appropriate user ID, and `"50.0"` with the desired credit amount.