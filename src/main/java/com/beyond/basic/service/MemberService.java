package com.beyond.basic.service;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberDetailResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//        input값의 검증 및 실질적인 비지니스 로직은 서비스 계층에서 수행

@Service //서비스 계층임을 표현함과 동시에 싱글톤객체로 생성
//Transactional어노테이션을 통해 모든 메서드에 트랜잭션을 적용하고,(각 메서드마다 하나의 트랜잭션으로 묶는다는뜻) 만약 예외가 발생시 롤백처리 자동화
@Transactional
public class MemberService {

//    비다형성 설계
    private final MyMemberRepository memberRepository; //밑에 모르고 쓸 수 있으니 final 삽입
    @Autowired //싱글톤객체를 주입(DI) 받는가라는 것을 의미
    public MemberService(MyMemberRepository memoryRepository){
        this.memberRepository = memoryRepository;
    }


    public void memberCreate(MemberReqDto dto){
        if (dto.getPassword().length() < 8){
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());
        memberRepository.save(member);
    }



    public MemberDetailResDto memberDetail(Long id){
        Optional<Member> optMember = memberRepository.findById(id);
        MemberDetailResDto memberDetailResDto = new MemberDetailResDto();
//        클랑언트에게 적절한 예외메시지와 상태코드를 주는것이 주요목적. 또한, 예외를 강제 발생시킴으로써 적절한 롤백처리 하는것도 주요목적
        Member member = optMember.orElseThrow(()->new EntityNotFoundException("없는 회원입니다."));
        memberDetailResDto.setId(member.getId());
        memberDetailResDto.setName(member.getName());
        memberDetailResDto.setEmail(member.getEmail());
        memberDetailResDto.setPassword(member.getPassword());
        return memberDetailResDto;
    }



    public List<MemberResDto> memberList(){
        List<MemberResDto> memberResDtos = new ArrayList<>();
        List<Member> memberList = memberRepository.findAll();
        for (Member member : memberList){
            MemberResDto dto = new MemberResDto();
            dto.setId(member.getId());
            dto.setName(member.getName());
            dto.setEmail(member.getEmail());
            memberResDtos.add(dto);
        }
        return memberResDtos;
    }
}
