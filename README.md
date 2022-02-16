# project_fsn
김태균 - FSN 카울리 채용 과제 


## 1. 요구사항 구현 여부
|ReqNo|요구사항명|구현|
|------|---|:---:|
|Req-01|Java8, Spring Boot2, JPA, H2 기술 사용.|O|
|Req-02|통계 데이터 저장 테이블 설계.|O|
|Req-03|통계 조회 기능 Rest API 개발.|O|
|Req-04|통계 데이터 업로드 기능 Rest API 개발.|O|
|Req-05|Rest API 문서 작성.|O|
|Req-06|Rest API 테스트 코드 작성.|O|
|Req-07|예외 처리|O|
|Req-08|H2 초기 저장 데이터 임의 설정|O|
<!-- |Req-09|swagger를 이용하여 api 확인 및 api 실행|△|
|Req-10|민감정보(주민등록번호, 비밀번호) 등은 암호화된 상태로 저장|O|
|Req-11|README 파일을 이용하여 요구사항 구현 여부, 구현 방법 그리고 검증 결과에 대해 작성|O|
 -->
<!-- ※Req-09에서 Swagger JWT 토큰 입력 부분 미구현 => Postman 테스트로 대체. -->

## 2. 구현 방법
### Installation (Req-01)
![스프링 초기 설정](https://user-images.githubusercontent.com/40817016/154221723-01283a95-9ca4-4bb8-abc7-ab3b5fc33f4f.png)

### 개발환경
* Spring boot 2.5.9 RELEASE 
(swagger 충돌 문제로 2.6.3에서 다운그레이드)
* Java 8
* JPA & H2
* lombok
* gradle

### Library
```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'com.h2database:h2'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	
	// ETC
	implementation group: 'org.apache.httpcomponents', name: 'httpclient', version: '4.5.13'
	implementation group: 'org.json', name: 'json', version: '20211205'
	implementation group: 'org.modelmapper', name: 'modelmapper', version: '2.3.8'

	// SWAGGER
	// implementation group: 'io.springfox', name: 'springfox-boot-starter', version: '2.9.2'
	implementation group: 'io.springfox', name: 'springfox-swagger2', version: '2.9.2'
	implementation group: 'io.springfox', name: 'springfox-swagger-ui', version: '2.9.2'
	
}
```

### ERD
![image](https://user-images.githubusercontent.com/40817016/150724838-9c57de2e-8f1a-440d-8abe-eb4b2da92c3d.png)

### Swagger URL
http://localhost:8080/swagger-ui.html#/ <br>
※Swagger JWT 토큰 입력 부분 미구현이어서 테스트는 Postman 테스트로 대체.

### 참고
※스웨거 참고 블로그: https://antstudy.tistory.com/251 <br>
※JWT 참고 강의: https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-jwt/dashboard

## 3. 검증 결과 (단위 테스트)
|TestNo|URL|파라미터|테스트 스텝|입력 데이터|성공 결과|실패 결과|
|:-----:|:---|:----|:-----|:-----|:---|:---|
| Test-01 | /szs/signup | userId (String): 아이디<br>password (String): 비밀번호<br>name (String): 이름<br>regNo (String): 주민등록번호 | 1. URL 요청<br>2.SZS_USER_TB INSERT|{"userId":"hong_id",<br>"password":"hong_pw",<br>"name":"홍길동",<br>"regNo":"860824-1655068"} | {"userId": "xorbsdut1",<br>"name": "홍길동"}|{"status": 409,<br>"message": "이미 가입되어 있는 유저입니다.",<br>"fieldErrors": []} |
| Test-02 | /szs/login | userId (String): 아이디<br>password (String): 비밀번호 | 1. URL 요청| {"userId":"hong_id",<br>"password":"hong_pw"}| {"token": "eyJzdWIiOiJ4b3Jic"}|{"timestamp": "2022-01-24T05:34:41.663+00:00",<br>"status": 401,<br>"error": "Unauthorized",<br>"path": "/szs/login"} |
| Test-03 | /szs/me | Header JWT Set | 1. Header에 Login 시 받은 JWT Token을 입력 <br> 2. URL요청| Header token | {"userId": "hong_id",<br>"name": "홍길동"}| {"timestamp": "2022-01-24T05:34:41.663+00:00",<br>"status": 401,<br>"error": "Unauthorized",<br>"path": "/szs/me"}|
| Test-04 | /szs/scrap | Header JWT Set | 1. Header에 Login 시 받은 JWT Token을 입력<br>2. URL요청 <br> 3.SZS_INCOME_TB INSERT | Header token | {"income_details": "급여",<br>"income_type": "근로소득(연간)",<br>"income_total": 24000000,<br>"work_start_date": "2020.10.03",<br>"work_end_date": "2020.11.02",<br>"payday": "2020.11.02",<br>"company_name": "(주)활빈당",<br>"company_regno": "012-34-56789"} | {"timestamp": "2022-01-24T05:34:41.663+00:00",<br>"status": 401,<br>"error": "Unauthorized",<br>"path": "/szs/scrap"} |
| Test-05 | /szs/refund | Header JWT Set | 1. Header에 Login 시 받은 JWT Token을 입력 <br> 2. URL요청 <br> 3.SZS_TAX_TB INSERT | Header token | {"이름":"홍길동",<br>"한도":"74만원",<br>"공제액":"92만 5천원",<br>"환급액":"74만원"} | {"timestamp": "2022-01-24T05:34:41.663+00:00",<br>"status": 401,<br>"error": "Unauthorized",<br>"path": "/szs/refund"} |

### 유저 정보 스크랩 URL 문제점 및 해결방법 
 - scrap001 소득 내역은 소득 종류에 따 복수개가 있을 수 있다. (리스트 형태로 받아서 처리) <br>
 - 총 소득액은 합산으로 구해야 한다.

### 보완 필요
 - 응답 코드 및 메세지 처리 (현재 에러 코드로만 응답) <br>
 - 에러 메세지 로그 파일로 저장 처리 (현재 print 방식)
 

# [주관식 과제]

### 1. 테스트코드 작성시 setup 해야 할 데이터가 대용량이라면 어떤 방식으로 테스트코드를 작성하실지 서술해 주세요.
최대한 깔끔하게 작성하겠습니다. 테스트 단위별로 함수 생성하여 작성하겠습니다. 

### 2. 외부 의존성이 높은 서비스를 만들 때 고려해야 할 사항이 무엇인지 서술해 주세요.
코드 관리가 어렵습니다. 의존성 있는 코드의 한곳을 변경하는 경우 의존하는 코드도 변경해줘야 하는 일이 생깁니다. 

### 3. 일정이 촉박한 프로젝트를 진행하게 되었습니다. 이 경우 본인의 평소 습관에 맞춰 개발을 진행할지, 회사의 코드 컨벤션에 맞춰 개발할지 선택해 주세요. 그리고 그 이유를 서술해 주세요.

회사 코드 컨센션에 맞춰 개발하겠습니다.
평소 습관대로 개발한 코드는 나중에 버그로 작용할 가능성이 크다고 생각합니다. <br> 일정 지연보다 해당 버그로 유지보수에 쏟는 시간과 비용이 더 많이 들어갈 것으로 생각합니다.


※추가할 내용
1. col_date, col_hour 복합키 설정
