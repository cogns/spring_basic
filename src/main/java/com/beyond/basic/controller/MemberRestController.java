package com.beyond.basic.controller;

import com.beyond.basic.domain.*;
import com.beyond.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//RestController 의 경우 모든 메서드 상단에 @ResponseBody가 붙는 효과 발생
@RestController
@RequestMapping("/rest")
public class MemberRestController {

    private final MemberService memberService;

    @Autowired
    public MemberRestController(MemberService memberService) {
        this.memberService = memberService; // 이름이 같아서 (다형성x) this 사용
    }


    @GetMapping("/")
    public String home() {
        return "member/home";
    }


    @GetMapping("/member/list")
//    public List<MemberResDto> memberList() {
//        List<MemberResDto> memberList = memberService.memberList();
//        return memberList;
//    }

    public ResponseEntity<CommonResDto> memberList() {
        Member member = new Member();
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "member is successfully created", member);
        return new ResponseEntity<>(commonResDto, HttpStatus.OK);

    }


    @GetMapping("/member/detail/{id}")
    public MemberDetailResDto memberDetail(@PathVariable Long id) {
        return memberService.memberDetail(id);
    }


    @PostMapping("/member/create")
    public String createMemberPost(@RequestBody MemberReqDto dto) {
        try {
            memberService.memberCreate(dto);
            return "ok";
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return "error";
        }
    }

//    수정은 2가지 요청방식 : PUT, PATCH 요청
//    patch요청은 부분수정, put요청은 덮어쓰기
    @PatchMapping("/member/pw/update")
    public String memberList(@RequestBody MemberUpdateDto dto){
        memberService.pwUpdate(dto);
        return "ok";
    }


    @DeleteMapping("/member/delete/{id}")
    public String memberDelete(@PathVariable Long id){
        memberService.delete(id);
        return "ok";
    }

}
