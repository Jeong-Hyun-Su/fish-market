# fish-market


## - 환경설정 및 설치 가이드

IntelliJ IDEA

open-jdk14 환경 ( Gradle )

spring boot - 2.3.4

spring.dependency-management - 1.0.10

Lombok 사용

jUnit5


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


## - API 사용 가이드

로컬호스트 환경에서 포트 8080을 사용하여 테스트하시면 됩니다.


**"/list"**

ex) localhost:8080/list

[ Body ]  없음.

**"/detail"**

ex) localhost:8080/detail

[ Body ]

{

    "id" : 1
    
}

**"/holiday"**

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

**"/add"**

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


**"/delete"**

ex) localhost:8080/delete

[ Body ]

{

    "id" : 1
    
}
