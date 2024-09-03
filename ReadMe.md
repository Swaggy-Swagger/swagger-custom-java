# Swaggy Swagger
<br/>
<p align="center">
<img src="https://github.com/user-attachments/assets/1650bb7d-cdf0-485c-8cc8-9617d8ea7472" width="350" alt="Swaggy-Swagger-Logo" />
</p>
<br/>
<p align="center">
  <a href="https://jitpack.io/#Swaggy-Swagger/swagger-custom-java" target="_blank"><img src="https://jitpack.io/v/Swaggy-Swagger/swagger-custom-java.svg" alt="Jitpack Release"/></a>
  <a href="./LICENSE" target="_blank"><img src="https://img.shields.io/github/license/Swaggy-Swagger/swagger-custom-java?logo=github&color=blue" alt="License"/></a>
  <a href="https://app.fossa.com/projects/git%2Bgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java?ref=badge_shield" target="_blank"><img src="https://app.fossa.com/api/projects/git%2Bgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java.svg?type=shield&issueType=license" alt="Fossa License Scan" /></a>
<a href="https://app.fossa.com/projects/git%2Bgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java?ref=badge_shield&issueType=security" target="_blank"><img src="https://app.fossa.com/api/projects/git%2Bgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java.svg?type=shield&issueType=security" alt="FOSSA Status"/></a>
  <a href="https://github.com/Swaggy-Swagger/swagger-custom-java"><img src="https://img.shields.io/github/v/release/Swaggy-Swagger/swagger-custom-java?logo=github" alt="github release"/></a>
  <a href="https://github.com/Swaggy-Swagger/swagger-custom-java"><img src="https://img.shields.io/github/release-date/Swaggy-Swagger/swagger-custom-java?color=blue&logo=github" alt="github last release date" /></a>
<br>
  <a href="https://github.com/Swaggy-Swagger/swagger-custom-java/graphs/contributors" target="_blank"><img src="https://img.shields.io/github/contributors-anon/Swaggy-Swagger/swagger-custom-java?logo=github&color=blue" alt="github contributors" /></a>
  <a href="https://github.com/Swaggy-Swagger/swagger-custom-java"><img src="https://img.shields.io/github/stars/Swaggy-Swagger/swagger-custom-java?logo=github" alt="github stars" /></a>
  <a href="https://github.com/Swaggy-Swagger/swagger-custom-java"><img src="https://img.shields.io/github/discussions/Swaggy-Swagger/swagger-custom-java?logo=github&color=blue" alt="github discussions" /></a>
</p>
</br>



## Overview
**Swaggy-Swagger** is a library designed to enhance the functionality and user experience of the popular API documentation tool, Swagger.
See [Swaggy-Swagger README.md](https://github.com/Swaggy-Swagger) for more details.

#### Demo Video
<div>
<a href="https://www.youtube.com/watch?v=oD8ShZGQrqo"><img src="https://img.shields.io/badge/YOUTUBE-FF0000?style=for-the-badge&logo=YouTube&logoColor=white&link=https://www.youtube.com/watch?v=oD8ShZGQrqo"/></a>
</div>


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

## Ways to contribute
We always welcome your contributions!
* If you would like to contribute to the frontend (UI), you can see [swaggy-ui](https://github.com/Swaggy-Swagger/swaggy-ui?tab=readme-ov-file#ways-to-contribute) for more details.


* Else, if you want to contribute to the backend (server-side), which is this repository, please refer to [CONTRIBUTING.md](https://github.com/Swaggy-Swagger/swagger-custom-java/blob/main/CONTRIBUTING.md).


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


## Contact
<a href="mailto:clcc001@naver.com"><img src="https://img.shields.io/badge/mail-d14836?style=flat-square&logo=Gmail&logoColor=white&link=clcc001@naver.com"/></a>
