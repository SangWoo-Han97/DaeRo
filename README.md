# DaeRo : 내맘대로, 발 닿는 대로 여행

<br>
<p align=center>
<img src="https://user-images.githubusercontent.com/48676089/185039598-3abaebd8-0162-4dec-9e04-b67b585e2fec.png" width=10% >
</p>

<br>
<br>

>
><h2 style="text-align:center">
>  최소한의 선택으로 떠나는 여행,
>  <br>
>  정해진 루트에서 벗어나 당신만의 여행루트를
>  <br>
>  "DaeRo"와 함께 만들어 보세요!
>  <br/>
></h2>
> 
<br>
<br>

## 💙 프로젝트 진행 기간
2022.07.11 ~ 2022.08.19
<br>
SSAFY 7기 2학기 공통프로젝트 - DaeRo

<br>
<br>

## ✈ DaeRo - 배경

여행 컨텐츠를 보시면서 한 번쯤은 **나도 여행가고 싶다**는 생각을 해보신 적 있지 않으신가요? 그런데 막상 가려고 하면 어디로 갈지 결정하기 어렵지 않으신가요?

<br>

<h3> 😎 <i>저희가 대신 결정해 드리겠습니다!</i> 😎</h3>

<br>

**DaeRo**는 **사용자 맞춤형 랜덤 국내 여행지 추천 및 기록 서비스**입니다. **DaeRo**와 함께 여러분들만의 여행 일지를 주변사람들과 공유해 보세요!

<br>
<br>

## 💙 주요 화면

![DaeRo](https://user-images.githubusercontent.com/55685140/203496105-52082557-5dd4-4d6c-873e-19eeafcda58d.png)

<br>

## 💙 주요 기능

- 사용자 맞춤형 **랜덤 여행지 추천**
  - 키워드를 통해 찾아 떠나는 여행지
  - 무작위 여행지
  - 인기있는 여행지
  - 첫 여행지 근처 주변 여행지

<br>

- **트립스탬프**를 이용한 여행 기록
  - 트립스탬프(사진과 여행지 이름, 날짜) 만들기
  - 여행 기록 남기기

<br>

- 나만의 **여행 일지**를 공유하는 **SNS**
  - 다양한 사람들의 여행기록
  - 여행지 & 유저 검색
  - 가고 싶은 공간 컬렉션에 저장
  - 다른 유저의 여행 따라가기

<br>
<br>

## 💙 Key benefits

- 잘 알려져 있지 않은 여행지 홍보 및 방문 유도
- 코로나로 인해 침체되어 있던 여행 시장 활성화
- 여행 사전준비에 대한 부담감 완화
- 인증미션을 통한 추가적인 재미요소 부여로 여행 만족도 증가

<br>
<br>


## 💙 Design
### ✔ Android
![안드로이드 figma](https://user-images.githubusercontent.com/48676089/185007551-e8e6c671-7d91-4388-b06a-7ba92fcd6a7b.png)

<br>

### ✔ Front-end
![프론트엔드 figma](https://user-images.githubusercontent.com/48676089/185007614-1b3ee8e6-b895-422e-8fe5-4634d0971b4e.png)

<br>

### ✔ ERD
![공통프로젝트 Backend](https://user-images.githubusercontent.com/48676089/185007772-c0f8a288-740d-4cd0-b0af-03f62c6aaace.png)

<br>

### ✔ 기능 명세서
<a href="https://secret-twine-9b7.notion.site/662787597c18463eaab2298d8ab23e29?v=5a8955b853744ba59707ee17cdb53cee" > 기능 명세서 바로가기</a>
<br>

![기능 명세서](https://user-images.githubusercontent.com/48676089/184919318-603a2671-fc5b-4ebc-b99d-2c59692d8e09.gif)



<br>
<br>

## 💙 기술 스택
### ✔ 안드로이드 <br>
<img src="https://img.shields.io/badge/kotlin-1.7.10-blue?style=plastic&logo=kotlin">
<img src="https://img.shields.io/badge/RxJava-1.7.10-B7178C?style=plastic&logo=ReactiveX">
<img src="https://img.shields.io/badge/Android Studio-Chipmuck 2021.2.1-green?style=plastic&logo=androidstudio">

<br>

| 라이브러리 명 | 설명 |
| :---: | --- |
|RxJava|반응형 프로그래밍 라이브러리|
|Glide|이미지 라이브러리|
|Retrofit|REST API 통신 라이브러리|
|ViewModel|UI 컨트롤러에서 데이터를 다루는 로직을 분리하기 위한 라이브러리|
|LiveData|관찰 가능한 데이터 홀더 클래스|
|Navigation|인앱 UI를 빌드 및 구조화하고 딥 링크를 처리하여 화면 간 이동할 수 있는 라이브러리|
|Room|SQLite 데이터베이스에서 지원하는 영구 데이터를 생성, 저장 및 관리하는 라이브러리|
|Paging|로컬 저장소 또는 네크워크를 통해 대규모 데이터 셋의 데이터 페이지를 로드하고 표시할 수 있는 라이브러리|
|Databinding|레이아웃의 UI 구성요소를 선언적 형식을 사용하여 앱의 데이터 소스에 결합하는 라이브러리|
|ViewPager2|스와이프할 수 있는 형식으로 뷰 또는 프래그먼트를 표시하는 라이브러리|
|NaverMap API| Naver 지도를 사용할 수 있는 라이브러리|
|Lottie|애니메이션 라이브러리|
|TedPermission|안드로이드 권한 관리 라이브러리|
|JWT|클라이언드와 서버, 서비스와 서비스 사이의 통신 시 권한 인가를 위해 사용하는 토큰|
|CanHub/Android-Image-Cropper|이미지 편집 라이브러리|
|Kakao API|카카오톡 공유 및 소셜로그인 라이브러리|


<br>

### ✔ UI/UX <br>
<img src="https://img.shields.io/badge/Figma--red?style=plastic&logo=Figma">

<br>

### ✔ 백엔드<br>
<img src="https://img.shields.io/badge/Java-1.8.0-blue?style=plastic&logo=Java">
<img src="https://img.shields.io/badge/AWS-EC2-FF9900?style=plastic&logo=amazon ec2">
<img src="https://img.shields.io/badge/Ubuntu--E95420?style=plastic&logo=Ubuntu">
<img src="https://img.shields.io/badge/Tomcat-9.0-F8DC75?style=plastic&logo=apachetomcat">
<img src="https://img.shields.io/badge/nginx-1.7.10-009639?style=plastic&logo=nginx">
<img src="https://img.shields.io/badge/mariaDB-10.6.8-003545?style=plastic&logo=mariaDB">
<img src="https://img.shields.io/badge/springboot-2.7.1-009639?style=plastic&logo=springboot">


<br>

| 라이브러리 명 | 설명 |
| :---: | --- |
|Spring boot 2.7.1|기본 Spring 프레임워크에 톰캣 서버를 내장하고 여러 편의 기능들을 추가한 자바 웹 프레임워크|
|JWT |클라이언드와 서버, 서비스와 서비스 사이의 통신 시 권한 인가를 위해 사용하는 토큰|
|FCM |무료로 메시지를 안정적으로 전송할 수 있는 교차 플랫폼 메시징 솔루션|
|Lombok |어노테이션을 제공해 메서드 작성 코드를 줄여주는 라이브러리|
|TourAPI 3.0||


<br>

### ✔ 프론트엔드<br>
<img src="https://img.shields.io/badge/Vue.js-2-blue?style=plastic&logo=vuejs">


<br>

### ✔ 협업환경

<img src="https://img.shields.io/badge/Project Manage-Jira-0052CC?style=plastic&logo=Jira">
<img src="https://img.shields.io/badge/Source Code Manage-Gitlab-FC6D26?style=plastic&logo=Gitlab">
<img src="https://img.shields.io/badge/Document Manage-notion-black?style=plastic&logo=notion">
<img src="https://img.shields.io/badge/Communication Tool-Mattermost-0058CC?style=plastic&logo=mattermost">
<img src="https://img.shields.io/badge/DB Manage-ERD Cloud-0058CC?style=plastic&logo=ERD Cloud">

<br>

---

## 💙 실행 화면
### ✔ 여행 화면
<br>
<img src="https://user-images.githubusercontent.com/48676089/185016879-4adf1fc6-5808-485c-898a-5535dc383cd1.gif" width=40% >

<br>

### ✔ SNS 화면
<br>

<img src="https://user-images.githubusercontent.com/48676089/185018107-ae2f52cf-45b4-491d-b1ea-1c0293ad9de2.gif" width=40%>

<br>

### ✔ 회원가입
<img src="https://user-images.githubusercontent.com/48676089/185037320-c3b6b1d4-8149-4563-bcb6-142bcdcab706.gif" width=40%>


---
<br>

## 👥 팀원 소개
### TEAM DAERO
||||||
|:---:|:---:|:---:|:---:|:---:|
|한상우|김기한|이주은|이호승|이주영|
|AOS 개발자<br>(팀장)|AOS 개발자<br>앱 배포 담당|AOS 개발자<br>문서 담당|백엔드 개발자<br>(부팀장)|백엔드 개발자<br>프론트도 담당|
