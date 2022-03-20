package com.dsm.diary.service;

import com.dsm.diary.dto.request.FindAccountIdRequest;
import com.dsm.diary.dto.request.LoginRequest;
import com.dsm.diary.dto.request.SignupRequest;
import com.dsm.diary.entity.account.Account;
import com.dsm.diary.entity.account.AccountRepository;
import com.dsm.diary.entity.refreshToken.RefreshToken;
import com.dsm.diary.entity.refreshToken.RefreshTokenRepository;
import com.dsm.diary.exception.NotFoundException;
import com.dsm.diary.exception.ServerErrorException;
import com.dsm.diary.exception.UnauthorizedException;
import com.dsm.diary.facade.AuthFacade;
import com.dsm.diary.security.jwt.JwtProperties;
import com.dsm.diary.security.jwt.JwtTokenProvider;
import com.dsm.diary.security.jwt.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${mail.username}")
    private String emailUsername;

    private final AuthFacade accountFacade;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void signup(SignupRequest signupRequest) {
        accountFacade.accountIdAlreadyExists(signupRequest.getAccountId());
        accountFacade.emailAlreadyExists(signupRequest.getEmail());

        accountRepository.findByEmail(signupRequest.getEmail());
        signupRequest.encodePassword(passwordEncoder.encode(signupRequest.getPassword()));

        accountRepository.save(
                Account.builder()
                        .email(signupRequest.getEmail())
                        .name(signupRequest.getName())
                        .accountId(signupRequest.getAccountId())
                        .password(signupRequest.getPassword())
                        .build());

        sendSuccessEmail(signupRequest.getEmail());
    }

    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {
        Account account = accountFacade.getByAccountId(loginRequest.getAccountId());

        if (!passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
            throw new UnauthorizedException();
        }

        return jwtTokenProvider.generateToken(account.getAccountId());
    }

    @Transactional
    public TokenResponse reissue(String refreshToken) {
        RefreshToken updateRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(NotFoundException::new);
        updateRefreshToken.update(jwtProperties.getRefreshExp());

        return jwtTokenProvider.generateToken(updateRefreshToken.getId());
    }

    @Transactional(readOnly = true)
    public void findAccountId(FindAccountIdRequest findAccountIdRequest) {
        accountRepository.findByAccountIdAndEmail(findAccountIdRequest.getName(), findAccountIdRequest.getEmail())
                .orElseThrow(NotFoundException::new);

        sendAccountIdEmail(findAccountIdRequest.getName(), findAccountIdRequest.getEmail());
    }

    private void sendAccountIdEmail(String email, String accountId) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(emailUsername);
            message.addRecipients(Message.RecipientType.TO, email);
            message.setSubject("[아이디 찾기]");
            message.setText("회원님의 아이디는 " + accountId + "입니다.");
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.getStackTrace();
            throw new ServerErrorException();
        }
    }

    private void sendSuccessEmail(String email) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(emailUsername);
            message.addRecipients(Message.RecipientType.TO, email);
            message.setSubject("[회원가입 성공]");
            message.setText("회원가입에 성공하셨습니다.");
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.getStackTrace();
            throw new ServerErrorException();
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
