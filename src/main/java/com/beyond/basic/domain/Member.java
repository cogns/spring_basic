package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
//entity어노테이션을 통해 엔티티매니저에게 객체관리를 위임
//해당 클래스명으로 테이블 및 컬럼을 자동생성하고 각종 설정정보 위임
@Entity
//@AllArgsConstructor
@NoArgsConstructor //기본생성자는 JPA에서 필수
public class Member {
    @Id //pk설정
//    identity : auto_increment설정
//    auto : jpa 자동으로 적절한 전략을 선택하도록 맡기는것.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Long은 bigint로 변환
//    String은 Varchar(255)로 기본으로 변환, name변수명이 name컬럼명으로 변환
    private String name;
//    nullable = false : notnull 제약조건
    @Column(nullable = false, length = 50, unique = true)
    private String email;
//    @Column(name = "pw") 이렇게 할 수는 있으나, 컬럼명과 변수명을 일치시키는것이 혼선을 줄일 수 있음
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Post> posts;



//    캐멀케이스 사용시 DB에는 _(언더바)로 생성
    @CreationTimestamp //DB에는 current_timestamp가 생성되지 않음
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;


    public Member(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }


//    password 상단에 @Setter를 통해 특정 변수만 setter사용이 가능하나, 일반적으로 의도를 명확하게 한 메서드를 별도로 만들어 사용하는 것을 권장.
    public void updatePw(String password){
        this.password = password;
    }

    public MemberDetailResDto detailFromEntinty(){
        LocalDateTime createdTime = this.getCreatedTime();
        String value = createdTime.getYear()+"년"+ createdTime.getMonthValue()+"월"+ createdTime.getDayOfMonth()+"일";
        return new MemberDetailResDto(this.id, this.name, this.email, this.password,value);
    }


    public MemberResDto listFromEntinty(){
        return new MemberResDto(this.id, this.name, this.email);
    }
}
