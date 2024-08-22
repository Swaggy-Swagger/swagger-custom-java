# Swaggy Swagger - swagger-custom-java
> A Spring Boot Library to make your Swagger Experience better

### Add dependency to your project
```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.Swaggy-Swagger:swagger-custom-java:main-SNAPSHOT'
    implementation 'org.ow2.asm:asm:9.2'
    implementation 'org.ow2.asm:asm-tree:9.2'
}
```

## Features
### UI
- Screenshot

### order controller methods by code line
```java
import io.swaggy.swagger.customlib.config.SwaggerConfig;
import io.swaggy.swagger.customlib.utils.OpenApiChangeTracker;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SwaggerConfig.class)
public class YourOpenApiConfig {
}
```
### track and save changes in api endpoints, parameters, dtos, etc
```java
import io.swaggy.swagger.customlib.utils.OpenApiChangeTracker;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YourOpenApiConfig {
    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return new OpenApiChangeTracker();
    }
}
```

