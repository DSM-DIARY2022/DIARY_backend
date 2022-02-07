package com.dsm.diary.entity.account;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account { // 계정

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 이메일
    @Column(nullable = false)
    private String email;

    // 닉네임
    @Column(nullable = false)
    private String accountName;

    // 아이디
    @Column(nullable = false, length =  10)
    private String accountId;

    // 비밀번호
    @Column(nullable = false)
    private String accountPwd;

}
