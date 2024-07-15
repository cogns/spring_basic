package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberReqDto {
    private String name;
    private String email;
    private String password;


//    dto에서 entinty로 변환
//    추후에는 빌더패턴으로 변환
    public Member toEntinty(){
        Member member = new Member(this.name, this.email, this.password);
        return member;
    }
}
