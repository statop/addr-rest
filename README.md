
### How to Run

```shell
$ mvn clean spring-boot:run
```

This will start a lightweight http server on localhost:8080.

### Run Tests

```shell
$ mvn clean test
```

### APIs

```shell

Get Address for Lat/Long:

http://localhost:8080/address/{latitude}/{longitude}

Example: http://localhost:8080/address/44.4647452/7.3553838



Get Last 10 Addresses searched for:

http://localhost:8080/last10Addresses
```
