package com.beyond.basic.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
//    1대1의 경우 OneToOne을 설정하고 unique=true로 설정
    @ManyToOne
    @JoinColumn(name = "member_id")
//    JPA의 영속성(Persistence)컨텍스트에 의해 Member가 관리
    private Member member;

    public PostResDto fromEntinty(){
        return new PostResDto(this.id, this.title);
    }
}
