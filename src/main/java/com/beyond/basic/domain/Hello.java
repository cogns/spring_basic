package com.beyond.basic.domain;

// 롬복 라이브러리를 통해 getter, setter 어노테이션 사용

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
// lombok 사용하는 것
//@Getter
//@Setter
@Data
//@NoArgsConstructor : 기본생성자
//@AllArgsConstructor : 모든매개변수를 사용한 생성자를 만드는 어노테이션
public class Hello {
    private String name;
    private String email;
    private String password;

//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }


//    @Override
//    public String toString(){
//        return "이름은 "+this.Name+" 이메일은 "+this.Email;
//    }

}
