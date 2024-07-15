package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
