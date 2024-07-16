package com.beyond.basic.controller;


import com.beyond.basic.domain.CommonResDto;
import com.beyond.basic.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response/entity")
public class ResponseEntityController {

//    ✨case1.✨ @ResponseStatus 어노테이션 방식

    @GetMapping("/anmotation1")
    @ResponseStatus(HttpStatus.OK)
    public String annotation1(){
        return "ok";
    }

    @GetMapping("/anmotation2")
    @ResponseStatus(HttpStatus.CREATED)
    public Member annotation2(){
//        (가정)객체 생성 후 DB저장 성공
        Member member = new Member("hong", "hong@naver.com", "1233");
        return member;
    }



//    ✨case2.✨ 메서드 체이닝 방식 : ResponseEntity의 클래스메서드 사용
    @GetMapping("/chanining1")
    public ResponseEntity<Member> chanining1(){
        Member member = new Member("hong", "hong@naver.com", "1233");
        return ResponseEntity.ok(member);
    }

    @GetMapping("/chanining2")
    public ResponseEntity<Member> chanining2(){
        Member member = new Member("hong", "hong@naver.com", "1233");
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/chanining3")
    public ResponseEntity<Member> chanining3(){
        return ResponseEntity.notFound().build();
    }



//    ✨case3.✨ ResponseEntity객체를 직접 custom하여 생성하는 방식
//    제일 직관적이고 활용도도 높음 앞으로 이렇게 쓸거임
    @GetMapping("/custom1")
    public ResponseEntity<Member> custom1(){
        Member member = new Member("hong", "hong@naver.com", "1233");
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }

    @GetMapping("/custom2")
    public ResponseEntity<CommonResDto> custom2(){
        Member member = new Member("hong", "hong@naver.com", "1233");
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "member is successfully created", member);
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
    }

}
