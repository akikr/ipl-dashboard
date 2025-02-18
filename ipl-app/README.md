# IPL Dashboard backend in Spring Boot

## Technologies: Spring Boot, Spring Batch, Java Persistence API, JPQL, HSQL-DB

### Match Data Source: [`match-data.csv`](src/main/resources/match-data.csv)

---

- Set up the `java` and `maven` environment using `.sdkmanrc` [sdkman-cli](https://github.com/sdkman/sdkman-cli)

```sh
sdk env install
sdk env
```

- Build and Run the app

```sh
./mvnw clean package
./mvnw clean spring-boot:run
```

- Build docker-image using paketobuildpacks via spring-boot plugin

```sh
# For current OS-arch
./mvnw clean spring-boot:build-image
# For linux/amd64
./mvnw clean spring-boot:build-image \
  -Dspring-boot.build-image.env.BP_NATIVE_IMAGE_BUILD_ARGUMENTS="-H:Architecture=amd64"
# For linux/arm64
./mvnw clean spring-boot:build-image \
  -Dspring-boot.build-image.env.BP_NATIVE_IMAGE_BUILD_ARGUMENTS="-H:Architecture=arm64"
```

See `ipl-app` OpenAPI Swagger docs: [ipl-app-swagger-ui](http://localhost:8081/app/swagger-ui/index.html)
