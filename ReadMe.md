# Swaggy Swagger - swagger-custom-java

[![FOSSA Status](https://app.fossa.com/api/projects/custom%2B47288%2Fgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java.svg?type=shield&issueType=license)](https://app.fossa.com/projects/custom%2B47288%2Fgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java?ref=badge_shield&issueType=license)
[![FOSSA Status](https://app.fossa.com/api/projects/custom%2B47288%2Fgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java.svg?type=shield&issueType=security)](https://app.fossa.com/projects/custom%2B47288%2Fgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java?ref=badge_shield&issueType=security)
[![License](https://img.shields.io/github/license/Swaggy-Swagger/swagger-custom-java?logo=github&color=blue")](./LICENSE)
[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java&count_bg=%2328DBE6&title_bg=%232D3540&icon=&icon_color=%23E7E7E7&title=hits)](https://hits.seeyoufarm.com)
> A Spring Boot Library to make your Swagger Experience better

## Requirements
- Java 17

## Installation
### Add dependency to your project
```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.Swaggy-Swagger:swagger-custom-java:main-SNAPSHOT'
}
```

## Features
### UI
- Screenshot

### Order controller methods by code line
```java
import io.swaggy.swagger.customlib.config.SwaggyConfig;
import io.swaggy.swagger.customlib.utils.OpenApiChangeTracker;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SwaggyConfig.class)
public class YourOpenApiConfig {
}
```
### Track and save changes in api endpoints, parameters, dtos, etc
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

