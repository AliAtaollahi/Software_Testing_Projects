## Adding Swagger UI to Spring using springdoc-openapi

### Step 1: Dependency

Make sure you have the necessary dependency in your `pom.xml`.

```xml

<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.1.0</version>
</dependency>
```

### Step 2: Run Your Application

Now, run your Spring Boot application. Swagger UI should be accessible at:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
