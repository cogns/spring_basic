package com.beyond.basic.domain;

import lombok.Data;

import javax.persistence.*;

@Data
//entity어노테이션을 통해 엔티티매니저에게 객체관리를 위임
//해당 클래스명으로 테이블 및 컬럼을 자동생성하고 각종 설정정보 위임
@Entity
public class Member {
    @Id //pk설정
//    identity : auto_increment설정
//    auto : jpa 자동으로 적절한 전략을 선택하도록 맡기는것.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Long은 bigint로 변환
//    String은 Varchar(255)로 기본으로 변환, name변수명이 name컬럼명으로 변환.
    private String name;
//    nullable = false : notnull 제약조건
    @Column(nullable = false, length = 50, unique = true)
    private String email;
//    @Column(name = "pw") 이렇게 할 수는 있으나, 컬럼명과 변수명을 일치시키는것이 혼선을 줄일 수 있음
    private String password;
}
