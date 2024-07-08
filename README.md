# 프로젝트 이름
Book

## 설명
이 프로젝트는 MVVM과 클린 아키텍처 패턴을 기반으로 설계되었습니다. 데이터, 도메인, 프레젠테이션 레이어로 나누어져 있으며, 각 레이어는 자체적인 모델을 가지고 있습니다. 뷰는 Jetpack Compose로 구현되었으며, 의존성 주입은 Hilt를 이용하였습니다.

## 목차
- [구조](#구조)
  - [Data 레이어](#data-레이어)
  - [Domain 레이어](#domain-레이어)
  - [Presentation 레이어](#presentation-레이어)
- [기술 스택](#기술-스택)
- [테스트](#테스트)

## 구조

### Data 레이어
- 데이터 소스, 데이터베이스, API 통신 등을 처리합니다.
- 레포지토리 패턴을 사용하여 데이터 접근을 추상화합니다.

### Domain 레이어
- 비즈니스 로직을 처리합니다.
- UseCase를 통해 앱의 주요 기능을 정의하고, Data 레이어와 Presentation 레이어를 연결합니다.

### Presentation 레이어
- UI와 관련된 모든 작업을 처리합니다.
- ViewModel은 Flow State를 통해 UI에 데이터를 제공합니다.
- Jetpack Compose를 이용하여 선언적으로 UI를 구성합니다.

## 기술 스택
- **언어**: Kotlin
- **아키텍처**: MVVM, Clean Architecture
- **UI**: Jetpack Compose
- **DI**: Hilt
- **기타**: Coroutine, Flow, Retrofit

## 테스트
프로젝트의 주요 기능을 검증하기 위해 작성된 테스트 코드에 대한 설명입니다.

### ViewModel 테스트
- ViewModel의 동작을 단위 테스트합니다.
- Flow와 State의 상태 변화를 검증합니다.

### UseCase 테스트
- UseCase의 비즈니스 로직을 단위 테스트합니다.
- 데이터 레이어와의 상호작용을 Mocking하여 검증합니다.
