package com.beyond.basic.service;

import com.beyond.basic.domain.Post;
import com.beyond.basic.domain.PostResDto;
import com.beyond.basic.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PostService {
    private final PostRepository postRepository;
    @Autowired
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public List<PostResDto> postList(){
        List<Post> postList = postRepository.findAll();
        List<PostResDto> postResDtoList = new ArrayList<>();
        for (Post p : postList){
            postResDtoList.add(p.fromEntinty());
            System.out.println("저자의 이메일은 : "+p.getMember().getEmail());
        }

        return postResDtoList;
    }
}
