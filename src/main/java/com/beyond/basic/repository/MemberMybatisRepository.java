package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//해당 레파지토리를 mybatis기술을 쓰겠다라는 의미
@Mapper
public interface MemberMybatisRepository extends MemberRepository{
    List<Member> findAll();
    Member save();
    Optional<Member> findById(Long id);
}
