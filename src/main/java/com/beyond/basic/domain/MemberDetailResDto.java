package com.beyond.basic.domain;

import lombok.Data;

@Data

public class MemberDetailResDto {
    private Long id;
    private String name;
    private String email;
    private String password;
}