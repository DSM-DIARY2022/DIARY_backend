package com.dsm.diary.service;

import com.dsm.diary.Exception.UnauthorizedException;
import com.dsm.diary.Facade.AuthFacade;
import com.dsm.diary.dto.request.FindAccountIdRequest;
import com.dsm.diary.dto.request.LoginRequest;
import com.dsm.diary.dto.request.SignupRequest;
import com.dsm.diary.entity.account.Account;
import com.dsm.diary.entity.refreshToken.RefreshTokenRepository;
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
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void signup(SignupRequest signupRequest){
        accountFacade.signup(signupRequest);
        accountFacade.sendSuccessEmail(signupRequest.getEmail());
    }

    @Transactional
    public TokenResponse login(LoginRequest loginRequest){
        Account account = accountFacade.getByAccountId(loginRequest.getAccountId());

        if (!passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
            throw new UnauthorizedException();
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

    @Transactional
    public void findAccountId(FindAccountIdRequest findAccountIdRequest){
        Account accountEmail = accountFacade.getByEmail(findAccountIdRequest.getEmail());
        Account accountName = accountFacade.getByName(findAccountIdRequest.getName());

        if(accountEmail.getEmail().equals(findAccountIdRequest.getEmail()) &&
                accountName.getAccountId().equals(findAccountIdRequest.getName())){
            accountFacade.sendAccountIdEmail(accountEmail.getEmail(), accountName.getAccountId());
        } else {
            throw new UnauthorizedException();
        }
    }

    /**
     *
     * 회원가입 : 성공하면 가입한 email로 메일 전송, 201
     *           이메일과 아이디가 중복되면 409, Signup request가 누락되면 400, 메일 전송 실패시 500
     * 로그인 : 성공하면 accessToken 이랑 refreshToken 반환, 200
     *          Login request가 누락되면 400, 찾을 수 없으면 404, 비밀번호 틀리면 401
     * 토큰 갱신 : 성공하면 accessToken 이랑 refreshToken 반환, 200
     *            refreshToken을 찾을 수 없으면 404
     * 아이디 찾기 : 성공하면 email로 아이디 전송, 200
     *              메일 전송실패시 500, 이메일과 아이디를 찾을 수 없으면 404
    *
     **/
}
