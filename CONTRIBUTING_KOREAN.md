# Swaggy-Swagger 컨트리뷰트하기 
[English](https://github.com/Swaggy-Swagger/swagger-custom-java/blob/main/CONTRIBUTING.md) | 한국어 

Swaggy-Swagger에 컨트리뷰트하는 것에 대한 여러분의 관심, 감사합니다!

시작하기에 좋은 위치는 :
- 이 문서를 읽기  

Swaggy-Swagger에 컨트리뷰트할 수 있는 다양한 방법이 있습니다!

- 현재 코드의 문제점에 대해 트러블슈팅하기. 
- 버그나 새로운 기능과 관련된 버그/기능 이슈 생성하기. 
- 부족해보이는 문서화나 해석에 문서화 이슈 생성하기. 
- 사전 설정의 좋은 예시를 제공하기 위해 토론에 대화 생성하기 
- 지원이 필요한 프레임워크에 대해 토론할 수 있는 대화 생성하기
- 우리 프로젝트나 오픈 소스에 대해 이야기하고 싶은 모든 사항에 대해 자유롭게 토론을 올려주세요!  
  "Swaggy-Swagger Discussions이 활발한 커뮤니티가 되길 바랍니다."


## 이슈 작업하기 
[이메일][email]이나 [새로운 이슈][new-issues] 생성을 통해 자유롭고 솔직하게 의견을 제시해주세요.
Swaggy-Swagger에 새로운 기능을 추가하거나 현재 코드를 기반으로 큰 변경 사항을 만들고 싶다면 특히 중요합니다!
Swaggy-Swagger 코어 개발자들은 최선을 다해 지원하겠습니다. 


이미 등록된 이슈에 작업을 시작하면, 해당 이슈에 댓글을 남겨 다른 사람들이에게 누군가 작업 중임을 알리세요.
제출하고 싶은 솔루션에 대해 확신이 없으면 자유롭게 코멘트를 요청하세요.  

Swaggy-Swagger는 [여기에서 설명되고 있는][development-models] "fork and pull" 모델을 사용합니다.
컨트리뷰터들은 개인 포크에 변경 사항을 푸시하고, 해당 변경 사항을 원본 리포지토리로 반영하기 위해 풀 리퀘스트를 생성합니다.  


시작하는 방법 :
    
- Swaggy-Swagger를 fork한 후, 작업 중인 이슈에 대한 브랜치를 main에서 생성하세요. 
- 개발을 시작하기 전에 Java/Spring Boot 및 필요한 유틸리티가 포함된 개발 환경이 설정되어 있는지 확인해 주세요. 
- 작업하는 위치에서 보이는 코드 스타일을 준수해 주세요. 
- 작업하면서 계속 커밋하세요. 
- 깃허브에 커밋을 푸시하고, swagger-custom-java의 브랜치에 풀 리퀘스트를 생성하세요.


### 🐞 Bug Reports
알지 못하는 문제를 해결할 수 없으므로, 문제를 신고해 주세요. 여기에는 문서 이해의 어려움, 유용하지 않은 오류 메시지, 예기치 않은 동작이 포함됩니다.  

[이 링크][new-issues]를 따라 새로운 이슈를 열고, 이슈 템플릿 중 하나를 선택하세요. 더 나아가, 수정 사항을 포함한 풀 리퀘스트를 제출할 수도 있습니다.


### ✅ Feature Requests

[feature request 템플릿][new-issues]을 사용해 자유롭게 이슈를 열어주세요.

### 📕 문서화에 컨트리뷰트하기 

우리는 항상 문서를 더 명확하고 정확하게 개선할 방법을 찾고 있습니다.
문서의 오타, 누락된 내용, 불명확하거나 부정확한 내용 등 개선할 수 있는 부분을 알려주세요.
변경 사항을 기꺼이 반영하고, 관심이 있다면 컨트리뷰트하는 데 도움을 드리겠습니다!

[문서화 이슈 템플릿][new-issues]을 사용해 이슈를 생성할 수 있습니다.

### 이슈 분류 

때때로 버그가 수정되었거나 버그가 수정되었음에도 불구하고 이슈가 열려 있을 수 있습니다.
또는 원래의 버그가 시간이 지나거나 또다른 이유로 문제가 해결되거나 변화가 생길 수 있습니다.

오래된 버그 report를 살펴보고 여전히 유효한지 확인하는 것이 도움이 될 수 있습니다.
오래된 이슈를 열어보고 여전히 문제인지 다시 확인한 후, 그 결과를 알려주는 댓글을 남겨주세요.
[가장 최근에 업데이트된 정렬][lru] 기능을 사용하면 이러한 이슈를 찾는 데 유용합니다.

<br>

---

## Pull Request (PR) 제출하기 
>_프론트엔드(UI)에 컨트리뷰트하고 싶다면, 더 자세한 정보는 [swaggy-ui](https://github.com/Swaggy-Swagger/swaggy-ui?tab=readme-ov-file#ways-to-contribute)를 참고해주세요._

Pull Request (PR)을 제출하기 전, 다음 지침을 확인해주세요 :

1. 중복 작업을 방지하기 위해, 시작하기 전에 GitHub의 Pull Request 목록에서 제출하려는 내용과 관련된 진행 중이거나 닫힌 PR이 있는지 확인해 주세요.

2. 이 레포지토리를 여러분 깃허브에 fork 해주세요 -
   [클릭해서 swagger-custom-java fork하기](https://github.com/Swaggy-Swagger/swagger-custom-java/fork)

3. 여러분의 swagger-custom-java 레포지토리 clone하기 :
   ```bash
     git clone https://github.com/{your-github-id}/swagger-custom-java
   ```

4. main이나 다른 브랜치에서 변경 작업하기.

5. Swaggy-Swagger의 [스타일 가이드](https://github.com/Swaggy-Swagger/swagger-custom-java/blob/main/STYLE_GUIDE.md)를 따라주세요. <br>
   _간단한 추천 사항이므로, 자유롭게 컨트리뷰트해주세요!_

6. Swaggy-Swagger의 커밋 메세지 컨벤션을 따르는 자세한 커밋 메세지를 남겨 변경사항을 커밋해주세요.

7. 깃허브에 작업한 브랜치를 푸시해주세요 :

   ```bash
    git push origin your-branch
   ```

8. 깃허브에서, `swagger-custom-java:main` 또는 `swagger-custom-java:feature/\* `로 Pull Request를 보내주세요.

> 끝! 컨트리뷰트에 감사드려요! 😎

<br> 

Pull Request가 생성되면, Swaggy-Swagger 프로젝트의 개발자 중 한 명이 여러분의 코드를 검토할 것입니다. 
검토 과정에서는 제안된 변경 사항이 적절한 지 확인합니다. 
특히 주말을 포함해 할당된 리뷰어에게 충분한 시간을 주시기 바랍니다.
답변이 없으면, [이메일][email]로 코어 개발자에게 문의하실 수 있습니다.

Pull Request 생성 후, 변경 사항이 반영된 상태의 Swaggy-Swagger의 메인 브랜치가, 즉시 테스트됩니다. 
이 단계에서 예상치 못한 문제가 발견될 경우(예: 원래 개발하지 않은 환경에서의 실패 등), 도움을 요청할 수 있습니다. 
이러한 문제를 해결하기 위해서는, 여러분의 브랜치에 추가 커밋을 푸시해주세요.

리뷰어가 필요하다고 판단되는 변경 사항을 지적할 수 있습니다. 이러한 변경 사항을 추가 커밋으로 반영해 주세요; 
이렇게 하면 리뷰어가 이전에 검토한 코드 이후에 어떤 변화가 있었는 지 확인할 수 있습니다. 
크고 복잡한 변경 사항은 여러 번의 리뷰와 수정이 필요할 수 있습니다.

리뷰어가 Pull Request를 승인하면, Swaggy-Swagger의 main 브랜치에 merge됩니다.


[new-issues]: https://github.com/Swaggy-Swagger/swagger-custom-java/issues/new/choose
[email]: mailto:clcc001@naver.com
[development-models]: https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/getting-started/about-collaborative-development-models
[lru]: https://docs.github.com/en/search-github/getting-started-with-searching-on-github/sorting-search-results#sort-by-updated-date
