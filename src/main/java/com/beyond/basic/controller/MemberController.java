package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberDetailResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.service.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequiredArgsConstructor //의존성주입방법3.
public class MemberController {

//    의존성 주입(DI)방법1. 생성자주입방식(가장많이 사용하는 방식)
//    장점 : 1)final을 통해 상수로 사용가능 2)다형성 구현가능 3)순환참조방지
//    생성자가 한개밖에 없을때는 Autowired생략가능
    private final MemberService memberService;
    @Autowired //싱글톤객체를 주입(DI) 받는가라는 것을 의미
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }



////    의존성주입방법2. 필드주입방식(Autorwired만 사용)
//    @Autowired
//    private  MemberService memberService;


//    의존성주입방법3. 어노테이션(RequiredArgs)을 이용하는 방식 //3번이 장점이 많으니 실무에서는 3번을 쓰는게 편하다. but 다양성은 쉽지않음.
//    @RequiredArgsConstructor : @NonNull어노테이션, final 키워드가 붙어있는 대상으로 생성자 생성
//    @NonNull
//    private final MemberService memberService;



    @GetMapping("/")
    public String home(){
        return "member/home";
    }



//    ✨회원 목록 조회
    @GetMapping("/member/list")
    public String memberList(Model model){
        List<MemberResDto> memberList = memberService.memberList();
        model.addAttribute("memberList", memberList);
        return "member/member-list";
    }



//    ✨회원상세조회 : memberDetail
//    url(uri) : member/1, member/2
//    화면명 : member-detail

    @GetMapping("/member/detail/{id}")
//    int 또는 long으로 받을 경우 스프링에서 자동으로 형변환(String -> Long)
    public String memberDetail(@PathVariable Long id, Model model){
        MemberDetailResDto memberDetailResDto = memberService.memberDetail(id);
        model.addAttribute("member", memberDetailResDto);
        return "member/member-detail";
    }



//    ✨회원가입화면 주고
//    url : member/create
    @GetMapping("/member/create")
    public String memberCreate(){
        return "member/member-create";
    }


//    ✨회원가입데이터를 받는다.
//    url : member/create
//    neme, email, password
    @PostMapping("/member/create")
    public String memberCreatePost(MemberReqDto dto, Model model){
        try {
            memberService.memberCreate(dto);
//         화면 리턴이 아닌 url재호출
            return "redirect:/member/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/member-error";
        }
    }


}
