# fish-market


## - 환경설정 및 설치 가이드

IntelliJ IDEA

open-jdk14 환경 ( Gradle )

spring boot - 2.3.4

spring.dependency-management - 1.0.10

Lombok 사용

jUnit5

위의 조건이 만족한 상태에서 github 링크로 clone을 뜬 뒤, intelliJ IDEA로 해당 프로젝트를 로딩합니다.

build.gradle에서 Gradle로 검사를 하되, openjdk 버전이 14가 아닐 경우, sourceCompatibility = '14' 코드에서 해당하는 openjdk에 맞추고 프로젝트 Build를 합니다.

이후, MarketApplication을 통해 해당 프로젝트를 실행합니다.

프로젝트가 실행중이면, Postman을 통해 아래 API 사용 가이드를 확인하고 테스트합니다.

-----

## - 테이블 생성 SQL

CREATE TABLE market (

    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    
    level INT NOT NULL,
    
    name VARCHAR(20) NOT NULL,
    
    address VARCHAR(50) NOT NULL,
    
    phone VARCHAR(20) NOT NULL,
    
    description VARCHAR(50) NOT NULL,
    
    owner VARCHAR(10) NOT NULL
    
)


CREATE TABLE holiday (

    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    
    holidays_id INT NOT NULL,
    
    date VARCHAR(20) NOT NULL,
    
    FOREIGN KEY(holidays_id) REFERENCES market(id)

)

CREATE TABLE businesstime (

    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    
    businesstime_id INT NOT NULL,
    
    day VARCHAR(10) NOT NULL,
    
    open VARCHAR(10) NOT NULL,
    
    close VARCHAR(10) NOT NULL,
    
    status VARCHAR(10) NULL,
    
    FOREIGN KEY(businesstime_id) REFERENCES market(id)

)

-----

## - API 사용 가이드

로컬호스트 환경에서 포트 8080을 사용하여 테스트하시면 됩니다.


**> "/list"**

ex) localhost:8080/list

[ Body ]  없음.


[ output ]

[

    {
    
        "name": "해적수산",
        
        "description": "노량진 시장 광어, 참돔 등 싱싱한 고퀄 활어 전문 횟집",
        
        "level": 1,
        
        "businessStatus": "HOLIDAY"
        
    },
    
    {
    
        "name": "인어수산",
        
        "description": "인천소래포구 종합어시장 갑각류센터 인어수산",
        
        "level": 2,
        
        "businessStatus": "OPEN"
        
    },
    
    {
    
        "name": "현수수산",
        
        "description": "월계동 맛집 활어 전문 식당",
        
        "level": 3,
        
        "businessStatus": "OPEN"
        
    },
    
    {
    
        "name": "요거수산",
        
        "description": "월계동 커피집",
        
        "level": 4,
        
        "businessStatus": "CLOSE"
        
    }
    
]

-----

**> "/detail"**

ex) localhost:8080/detail

[ Body ]

{

    "id" : 1
    
}

[ output ]

{

    "id": 2,
    
    "level": 4,
    
    "name": "요거수산",
    
    "owner": "프레소",
    
    "address": "월계동 111",
    
    "phone": "010-4478-1121",
    
    "description": "광운대 커피집",
    
    "businessDays": [
    
        {
        
            "day": "Sunday",
            
            "open": "09:00",
            
            "close": "17:00",
            
            "status": "HOLIDAY"
            
        },
        
        {
        
            "day": "Monday",
            
            "open": "09:00",
            
            "close": "24:00",
            
            "status": "CLOSE"
            
        },
        
        {
        
            "day": "Tuesday",
            
            "open": "09:00",
            
            "close": "17:00",
            
            "status": "CLOSE"
            
        }
        
    ]
    
}

-----

**> "/holiday"**

ex) localhost:8080/holiday

[ Body ]

{

    "id": 1,
    
    "holidays": [
    
        "2020-10-11",
        
        "2020-10-12",
        
        "2020-10-15"
        
    ]
    
}

[ output ]

정상 종료시 "{}" 출력

-----


**> "/add"**

ex) localhost:8080/add

[ Body ] 

{

    "name": "해적수산",
    
    "owner": "박해적",
    
    "description": "노량진 시장 광어, 참돔 등 싱싱한 고퀄 활어 전문 횟집",
    
    "level": 1,
    
    "address": "서울 동작구 노량진동 13-8 노량진수산시장 활어 001",
    
    "phone": "010-1234-1234",
    
    "businessTimes": [
    
        {
        
            "day": "Thrusday",
            
            "open": "09:00",
            
            "close": "24:00"
            
        },
        
        {
        
            "day": "Friday",
            
            "open": "09:00",
            
            "close": "24:00"
            
        },
        
        {
        
            "day": "Saturday",
            
            "open": "09:00",
            
            "close": "24:00"
            
        },
        
        {
        
            "day": "Sunday",
            
            "open": "09:00",
            
            "close": "24:00"
            
        }
        
    ]
    
}

[ output ]

정상 종료시 "{}" 출력

-----

**> "/delete"**

ex) localhost:8080/delete

[ Body ]

{

    "id" : 1
    
}

[ output ]

정상 종료시 "{}" 출력
