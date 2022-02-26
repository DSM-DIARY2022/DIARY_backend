package com.dsm.diary.service;

import com.dsm.diary.Exception.UnauthorizedException;
import com.dsm.diary.Facade.AuthFacade;
import com.dsm.diary.dto.request.LoginRequest;
import com.dsm.diary.dto.request.SignupRequest;
import com.dsm.diary.entity.account.Account;
import com.dsm.diary.entity.refreshToken.RefreshTokenRepository;
import com.dsm.diary.security.jwt.JwtProperties;
import com.dsm.diary.security.jwt.JwtTokenProvider;
import com.dsm.diary.security.jwt.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthFacade accountFacade;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void signup(SignupRequest signupRequest){
        accountFacade.signup(signupRequest);
        accountFacade.sendEmail(signupRequest.getEmail());
    }

    @Transactional
    public TokenResponse login(LoginRequest loginRequest){
        Account account = accountFacade.getByAccountId(loginRequest.getAccountId());

        if (!passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
            throw new IllegalArgumentException("Login Failed");
        }

        return jwtTokenProvider.generateToken(account.getAccountId());
    }

    @Transactional
    public TokenResponse reissue(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .filter(token -> jwtTokenProvider.isRefresh(refreshToken))
                .map(token -> jwtTokenProvider.generateToken(token.getId()))
                .orElseThrow(UnauthorizedException::new);
    }
}
