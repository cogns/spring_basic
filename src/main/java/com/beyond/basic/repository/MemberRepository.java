package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberReqDto;

import java.util.*;

public interface MemberRepository {

    Member save(Member member);

    List<Member> findAll();

    Optional<Member> findById(Long id);

}

