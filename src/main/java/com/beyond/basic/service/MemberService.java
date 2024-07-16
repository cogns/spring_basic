package com.beyond.basic.service;

import com.beyond.basic.domain.*;
import com.beyond.basic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//        input값의 검증 및 실질적인 비지니스 로직은 서비스 계층에서 수행

@Service //서비스 계층임을 표현함과 동시에 싱글톤객체로 생성
//Transactional어노테이션을 통해 모든 메서드에 트랜잭션을 적용하고,(각 메서드마다 하나의 트랜잭션으로 묶는다는뜻) 만약 예외가 발생시 롤백처리 자동화
@Transactional(readOnly = true)
public class MemberService {

//    비다형성 설계
    private final MyMemberRepository memberRepository; //밑에 모르고 쓸 수 있으니 final 삽입
    @Autowired //싱글톤객체를 주입(DI) 받는가라는 것을 의미
    public MemberService(MyMemberRepository memoryRepository){
        this.memberRepository = memoryRepository;
    }


    public void memberCreate(MemberReqDto dto){
        if (dto.getPassword().length() < 4){
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        Member member = dto.toEntinty();
//        아래 4개의 코드를 변수 toEntinty()를 통해 위에 하나의 코드로 정리 가능.
//        Member member = new Member();
//        member.setName(dto.getName());
//        member.setEmail(dto.getEmail());
//        member.setPassword(dto.getPassword());
        memberRepository.save(member);
//        Transactional 롤백처리 테스트
        if (member.getName().equals("kim")){
            throw new IllegalArgumentException("잘못된 입력 입니다.");
        }
    }



    public List<MemberResDto> memberList(){
        List<MemberResDto> memberResDtos = new ArrayList<>();
        List<Member> memberList = memberRepository.findAll();
        for (Member member : memberList){
//            MemberResDto dto = new MemberResDto();
//            dto.setId(member.getId());
//            dto.setName(member.getName());
//            dto.setEmail(member.getEmail());
//            memberResDtos.add(dto);
            memberResDtos.add(member.listFromEntinty());
        }
        return memberResDtos;
    }



    public MemberDetailResDto memberDetail(Long id){
        Optional<Member> optMember = memberRepository.findById(id);
//        MemberDetailResDto memberDetailResDto = new MemberDetailResDto();
//        클랑언트에게 적절한 예외메시지와 상태코드를 주는것이 주요목적. 또한, 예외를 강제 발생시킴으로써 적절한 롤백처리 하는것도 주요목적
        Member member = optMember.orElseThrow(()->new EntityNotFoundException("없는 회원입니다."));
//        memberDetailResDto.setId(member.getId());
//        memberDetailResDto.setName(member.getName());
//        memberDetailResDto.setEmail(member.getEmail());
//        memberDetailResDto.setPassword(member.getPassword());
//        LocalDateTime createdTime = member.getCreatedTime();
//        String value = createdTime.getYear()+"년"+ createdTime.getMonthValue()+"월"+ createdTime.getDayOfMonth()+"일";
//        memberDetailResDto.setCreatedTime(value);
        System.out.println("글쓴이의 쓴 글의 개수 : "+member.getPosts().size());
        for (Post p : member.getPosts()){
            System.out.println("글의 제목 : "+p.getTitle());
        }
        return member.detailFromEntinty();
    }

    public void pwUpdate(MemberUpdateDto dto){
        Member member = memberRepository.findById(dto.getId()).orElseThrow(()-> new EntityNotFoundException("member is not found"));
        if (member!=null){
            member.updatePw(dto.getPassword());

//            기존객체를 조회 후 수정한 다음에 save시에는 jpa update 실행 (외울것)
            memberRepository.save(member);
        }
    }

    public void delete(Long id){
        Member member = memberRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("member is not found"));
        memberRepository.delete(member); //완전삭제
//        member.updateDelYn("Y");
//        memberRepository.delete(member);
    }
}
