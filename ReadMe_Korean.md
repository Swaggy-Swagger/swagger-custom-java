# Swaggy Swagger
<br/>
<p align="center">
<img src="https://github.com/user-attachments/assets/b3c3a16d-43aa-4689-8857-dab31f75f601" width="150" alt="Swaggy-Swagger-Logo" />
</p>
<p align="center">
  <img src="https://github.com/user-attachments/assets/13513534-6217-4c10-abdc-cd228fcca076" width="300" alt="Swaggy Swagger" />
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


[English](https://github.com/Swaggy-Swagger/swagger-custom-java?tab=readme-ov-file#swaggy-swagger) | 한국어 
## 개요
**Swaggy-Swagger** 는 인기 있는 API 문서화 도구인 Swagger의 기능과 사용자 경험을 향상시키기 위해 설계된 라이브러리입니다.  


자세한 정보는 [Swaggy-Swagger README.md](https://github.com/Swaggy-Swagger/.github/blob/main/profile/README_KOREAN.md) 를 참고해주세요.

#### 시연 영상 
<div>
<a href="https://www.youtube.com/watch?v=oD8ShZGQrqo"><img src="https://img.shields.io/badge/YOUTUBE-FF0000?style=for-the-badge&logo=YouTube&logoColor=white&link=https://www.youtube.com/watch?v=oD8ShZGQrqo"/></a>
</div>


## 요구사항 
- **자바 17 이상**: 이 프로젝트는 자바17 이상의 버전이 필요합니다.
- **스프링 부트 3.x**: 호환성을 위해 스프링 부트 3 이상의 버전을 사용하는지 확인해주세요. 

## 설치
### 프로젝트에 dependency 추가하기 
```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.Swaggy-Swagger:swagger-custom-java:1.0.1'
}
```
### 버젼 정보
| Swaggy-Swagger 버젼 | 배포일 | 배포 노트                                                                 |
|--------------------|--------------|-----------------------------------------------------------------------|
| 1.0.1            | 2024-11-13   | [tag 1.0.1](https://github.com/Swaggy-Swagger/swagger-custom-java/tree/1.0.1)  |


## 기능 추가하는 방법 

### 코드 작성 순서대로 API 정렬하는 기능 
```java
import io.swaggy.swagger.customlib.config.SwaggyConfig;
import io.swaggy.swagger.customlib.utils.OpenApiChangeTracker;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

// 사용자의 OpenApi 설정 클래스에 'SwaggyConfig' 클래스를 추가해주세요.
@Configuration
@Import(SwaggyConfig.class)
public class YourOpenApiConfig {
}
```
### API 변경 사항 추적하고 저장하는 기능 
```java
import io.swaggy.swagger.customlib.utils.OpenApiChangeTracker;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YourOpenApiConfig {
    
    // 사용자의 OpenApi 설정 클래스에 'OpenApiCustomizer' Bean을 등록해주세요.
    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return new OpenApiChangeTracker();
    }
}
```

## 컨트리뷰트 하는 방법
모든 컨트리뷰트를 환영합니다!

* 프론트엔드(UI)에 컨트리뷰트하고 싶으시다면, 더 자세한 정보는 [swaggy-ui](https://github.com/Swaggy-Swagger/swaggy-ui?tab=readme-ov-file#ways-to-contribute)를 확인해주세요.


* 현재 레포지토리인, 백엔드(서버 사이드)에 컨트리뷰트하고 싶으시다면, [CONTRIBUTING.md](https://github.com/Swaggy-Swagger/swagger-custom-java/blob/main/CONTRIBUTING_KOREAN.md)를 참고해주세요.


## 컨트리뷰터 

**Swaggy-Swagger**에 기여해주신 모든 분들께 감사드립니다.

<a href="https://github.com/Swaggy-Swagger/swagger-custom-java/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=Swaggy-Swagger/swagger-custom-java" />
</a>

_<div align=right>Made with <a href="https://contrib.rocks">contrib.rocks</a></div>_


## 라이센스
**Swaggy-Swagger**는 Apache License 2.0 버전 라이센스 하에 있습니다.
더 자세한 정보는 [LICENSE](https://github.com/Swaggy-Swagger/swagger-custom-java/blob/main/LICENSE)에서 확인해주세요.

[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2FSwaggy-Swagger%2Fswagger-custom-java?ref=badge_large)


## 연락처
<a href="mailto:clcc001@naver.com"><img src="https://img.shields.io/badge/mail-d14836?style=flat-square&logo=Gmail&logoColor=white&link=clcc001@naver.com"/></a>
