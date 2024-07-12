package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

//MemberRepository가 되기 위해서는 JpaRepository를 상속해야하고, 상속시에 Entity명과 entity의 PK타입을 명시
//MemberRepository는 JpaRepository를 상속함으로써 JpaRepository의 주요 기능을 상속
//JpaRepository가 인터페이스임에도 해당 기능을 상속해서 사용할 수 있는 이유는 hibernate구현체가 미리 구현돼 있기 때문.
//런타임시점에 사용자가 인터페이스에 정의한 메서드를 프록시(대리인)객체가 리플렉션기술을 통해 메서드를 구현
public interface MemberSpringDataJpaRepository extends JpaRepository<Member, Long> { // 인터페이스 하고 인터페이스가 상속 관계가 선언 되면 메서드들을 그대로 가져온다

    Optional<Member> findByEmail(String email);
}
