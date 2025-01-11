# IPL Dashboard backend in Spring Boot

## Technologies: Spring Boot, Spring Batch, Java Persistence API, JPQL, HSQL-DB

### Match Data Source: [`match-data.csv`](src/main/resources/match-data.csv)

---

- Setup the `java` and `maven` enviroment using `.sdkmanrc` [sdkman-cli](https://github.com/sdkman/sdkman-cli)

```sh
sdk env install
sdk env
```

- Build and Run the app

```sh
./mvnw clean package
./mvnw clean spring-boot:run
```

See `ipl-app` OpenAPI Swagger docs: [ipl-app-swagger-ui](http://localhost:8081/app/swagger-ui/index.html)
