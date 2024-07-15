package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository

// 다형성을 처리하지 않은 버전의 MemberSpringDataJpaRepository.java
public interface MyMemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email); //런타임 시점에 이거 만들어줘! 라고 말한 것
    List<Member> findByName(String name);
}
