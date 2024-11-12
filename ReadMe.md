# Swaggy Swagger
<br/>
<br/>
<p align="center">
<img src="https://github.com/user-attachments/assets/b3c3a16d-43aa-4689-8857-dab31f75f601" width="150" alt="Swaggy-Swagger-Logo" />
</p>
<p align="center">
  <img src="https://github.com/user-attachments/assets/13513534-6217-4c10-abdc-cd228fcca076" width="300" alt="Swaggy Swagger" />
</p>
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


English | [한국어](https://github.com/Swaggy-Swagger/swagger-custom-java/blob/main/ReadMe_Korean.md) 
## Overview
**Swaggy-Swagger** is a library designed to enhance the functionality and user experience of the popular API documentation tool, Swagger.
See [Swaggy-Swagger README.md](https://github.com/Swaggy-Swagger) for more details.


#### Demo Website 
[swaggy-swagger.vercel.app](https://swaggy-swagger.vercel.app/#/)


#### Demo Video
<div>
<a href="https://www.youtube.com/watch?v=oD8ShZGQrqo"><img src="https://img.shields.io/badge/YOUTUBE-FF0000?style=for-the-badge&logo=YouTube&logoColor=white&link=https://www.youtube.com/watch?v=oD8ShZGQrqo"/></a>
</div>


## Requirements
- **Java 17 or higher**: This project requires Java version 17 or later.
- **Spring Boot 3.x**: Ensure that you are using Spring Boot version 3.x for compatibility.

## Installation
### Add dependency to your project
```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.Swaggy-Swagger:swagger-custom-java:1.0.1'
}
```

### Version Information
| Swaggy-Swagger Version | Release Date | Notes                                                                 |
|--------------------|--------------|-----------------------------------------------------------------------|
| 1.0.1            | 2024-11-13   | [tag 1.0.1](https://github.com/Swaggy-Swagger/swagger-custom-java/tree/1.0.1)  |

> Please use version **1.0.1** or above. Versions **1.0.0** and **0.0.1** contain known issues.

## How to Apply Features

### Order controller methods by code line number
```java
import io.swaggy.swagger.customlib.config.SwaggyConfig;
import io.swaggy.swagger.customlib.utils.OpenApiChangeTracker;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

// Import 'SwaggyConfig' class to your OpenApi configuration class.
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
    
    // Register 'OpenApiCustomizer' Bean to your OpenApi configuration class.
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
