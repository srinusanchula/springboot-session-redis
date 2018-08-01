# Springboot Session Redis

This is a sample springboot application about spering session with redis.

## Getting Started

Spring session is a wrapper on top of http session that gives a flexibility to offload the session eother to DB or redis ... etc. The best part is that it is transparent to application, (i.e.) application still uses httpSession as before and the spring wrapped will be effective at filter level.

Here is the dependency that is required to enable this -
```
gradle:compile("org.springframework.session:spring-session-data-redis")
```

Control (application.properties) -
```
spring.session.store-type=redis
```

Other config (application.properties) -
```
spring.redis.host=localhost
spring.redis.password=
spring.redis.port=6379

server.servlet.session.timeout=180
server.servlet.session.cookie.name=cookie
spring.session.redis.flush-mode=on-save
spring.session.redis.namespace=session
```

### Prerequisites

Redis should be installed and ready.

Local redis installation in Mac.
```
brew install redis
redis-server //start redis
```

## Running the tests

Standard SpringbootTests
```
New profile 'test' is created, seperate application properties (application-test.properties) for configuration change and mock tests for web endpoints.
```

### Endpoints

It has following endpoints.

```
/hello
```