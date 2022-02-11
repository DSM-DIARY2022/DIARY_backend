package com.dsm.diary.entity.account;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account { // 계정

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 이메일
    @Column(nullable = false, unique = true)
    private String email;

    // 닉네임
    @Column(nullable = false)
    private String name;

    // 아이디
    @Column(nullable = false, length =  10, unique = true)
    private String accountId;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    @Builder
    public Account(String email, String name, String accountId, String password){
        this.email = email;
        this.name = name;
        this.accountId = accountId;
        this.password = password;
    }

}
