## Tools
- manven
- docker-compose
- java 21
- swagger

## Steps
- 1 access the project root the app and execute the command below:
```
docker-compose up  --build
```

- 2 Access the link below:
    * [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)


- 3 create first account
```  
curl -X 'POST' \
'http://localhost:8080/cartoes' \
-H 'accept: application/json' \
-H 'Content-Type: application/json' \
-d '{
"accountNumber": "1",
"holderName": "Marco"
}'
```

- 4 create second account

```  
curl -X 'POST' \
'http://localhost:8080/cartoes' \
 -H 'accept: application/json' \
 -H 'Content-Type: application/json' \
 -d '{
 "accountNumber": "1",
 "holderName": "Claudemir"
}'
``` 

- 5 make a deposit into one of the accounts

``` 
  curl -X 'PUT' \
  'http://localhost:8080/transactions/deposit-funds' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "accountNumber": "1",
  "amount": 100,
  "description": "Deposito"
  }'
``` 

- 6 transfer the account with balance to the recipient
  curl -X 'PUT' \

``` 
  'http://localhost:8080/transacoes' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "sourceAccount": "1",
  "destinationAccount": "2",
  "amount": 50,
  "description": "TED"
  }'
```

- 7 check account balance

``` 
  curl -X 'GET' \
  'http://localhost:8080/cartoes/{numeroCartao}' \
  -H 'accept: application/json'
``` 