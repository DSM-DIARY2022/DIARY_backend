package com.dsm.diary.service;

import com.dsm.diary.Exception.ServerErrorException;
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
}
