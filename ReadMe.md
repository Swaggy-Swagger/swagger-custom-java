# Swaggy Swagger
<br/>
<p align="center">
<img src="https://github.com/user-attachments/assets/98d594cd-1518-49aa-8f61-3aae42c95e6f" width="500" alt="Swaggy-Swagger-Logo" />
</p>
<br/>
<p align="center">
  <a href="https://jitpack.io/#Swaggy-Swagger/swagger-custom-java" target="_blank"><img src="https://jitpack.io/v/Swaggy-Swagger/swagger-custom-java.svg" alt="Jitpack Release"/></a>
  <a href="./LICENSE" target="_blank"><img src="https://img.shields.io/github/license/Swaggy-Swagger/swagger-custom-java?logo=github&color=blue" alt="License"/></a>
  <a href="https://app.fossa.com/projects/git%2Bgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java?ref=badge_shield" target="_blank"><img src="https://app.fossa.com/api/projects/git%2Bgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java.svg?type=shield&issueType=license" alt="Fossa License Scan" /></a>
<a href="https://app.fossa.com/projects/git%2Bgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java?ref=badge_shield&issueType=security" target="_blank"><img src="https://app.fossa.com/api/projects/git%2Bgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java.svg?type=shield&issueType=security" alt="FOSSA Status"/></a>
  <a href="https://github.com/Swaggy-Swagger/swagger-custom-java"><img src="https://img.shields.io/github/v/release/Swaggy-Swagger/swagger-custom-java?logo=github" alt="github release"/></a>
  <a href="https://github.com/Swaggy-Swagger/swagger-custom-java"><img src="https://img.shields.io/github/release-date/Swaggy-Swagger/swagger-custom-java?color=blue&logo=github" alt="github last release date" /></a>
  <a href="/LICENSE"><img src="https://img.shields.io/github/license/Swaggy-Swagger/swagger-custom-java?logo=github&color=blue" alt="github License" /></a>
  <a href="https://github.com/Swaggy-Swagger/swagger-custom-java/graphs/contributors" target="_blank"><img src="https://img.shields.io/github/contributors-anon/Swaggy-Swagger/swagger-custom-java?logo=github&color=blue" alt="github contributors" /></a>
  <a href="https://github.com/Swaggy-Swagger/swagger-custom-java"><img src="https://img.shields.io/github/stars/Swaggy-Swagger/swagger-custom-java?logo=github" alt="github stars" /></a>
  <a href="https://github.com/Swaggy-Swagger/swagger-custom-java"><img src="https://img.shields.io/github/discussions/Swaggy-Swagger/swagger-custom-java?logo=github&color=blue" alt="github discussions" /></a>
</p>
</br>

Demo Video: [Watch on YouTube](https://www.youtube.com/watch?v=oD8ShZGQrqo)
</br>

## Overview
See [Swaggy-Swagger README.md](https://github.com/Swaggy-Swagger)

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
    implementation 'com.github.Swaggy-Swagger:swagger-custom-java:0.0.1'
}
```

## How to Apply Features

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

### UI
<img width="1624" alt="스크린샷 2024-08-28 오후 11 23 15" src="https://github.com/user-attachments/assets/09fe68aa-ebe7-4592-beb5-3d4f3a1eb8a1">
<img width="1624" alt="스크린샷 2024-08-28 오후 11 24 36" src="https://github.com/user-attachments/assets/b09bf788-df68-4975-82cf-dc623d8e4ddc">
<img width="1624" alt="스크린샷 2024-08-28 오후 11 24 29" src="https://github.com/user-attachments/assets/c1181e35-da40-4124-b56c-d1bd9ea35c76">
<img width="1624" alt="스크린샷 2024-08-28 오후 10 54 36" src="https://github.com/user-attachments/assets/c36539b7-0e2d-474a-b074-59e007feef8a">
<img width="1624" alt="스크린샷 2024-08-28 오후 10 54 12" src="https://github.com/user-attachments/assets/9c6e1e6f-4949-4f05-a695-10332ce32720">


## Contributors

Thank you to everyone who contributed to our project.

<a href="https://github.com/Swaggy-Swagger/swagger-custom-java/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=Swaggy-Swagger/swagger-custom-java" />
</a>

_<div align=right>Made with <a href="https://contrib.rocks">contrib.rocks</a></div>_


## License
**Swaggy-Swagger** is licensed under the Apache License, Version 2.0.  
See the [LICENSE](./LICENSE) file for more details.

[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java?ref=badge_large)
