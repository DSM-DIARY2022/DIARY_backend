package com.dsm.diary.controller;

import com.dsm.diary.dto.request.FindAccountIdRequest;
import com.dsm.diary.dto.request.LoginRequest;
import com.dsm.diary.dto.request.SignupRequest;
import com.dsm.diary.security.jwt.dto.TokenResponse;
import com.dsm.diary.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody @Valid SignupRequest signupRequest){
        authService.signup(signupRequest);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/reissue")
    public TokenResponse reissue(String refreshToken){
        return authService.reissue(refreshToken);
    }

    @PostMapping("/find/id")
    public void findAccountId(FindAccountIdRequest findAccountIdRequest){
        authService.findAccountId(findAccountIdRequest);
    }

}
