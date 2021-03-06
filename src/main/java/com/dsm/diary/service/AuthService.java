package com.dsm.diary.service;

import com.dsm.diary.dto.request.FindAccountIdRequest;
import com.dsm.diary.dto.request.LoginRequest;
import com.dsm.diary.dto.request.SignupRequest;
import com.dsm.diary.entity.account.Account;
import com.dsm.diary.entity.account.AccountRepository;
import com.dsm.diary.entity.refreshToken.RefreshToken;
import com.dsm.diary.entity.refreshToken.RefreshTokenRepository;
import com.dsm.diary.exception.BadRequestException;
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

        accountRepository.save(
                Account.builder()
                        .email(signupRequest.getEmail())
                        .name(signupRequest.getName())
                        .accountId(signupRequest.getAccountId())
                        .password(passwordEncoder.encode(signupRequest.getPassword()))
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

        if(!jwtTokenProvider.getBody(refreshToken).get("typ").equals("refresh")){
            throw new BadRequestException();
        }

        RefreshToken updateRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(NotFoundException::new);

        String newRefreshToken = jwtTokenProvider.generateRefreshToken(updateRefreshToken.getId());
        updateRefreshToken.update(newRefreshToken);

        String newAccessToken = jwtTokenProvider.generateAccessToken(updateRefreshToken.getId());

        return new TokenResponse(newAccessToken, newRefreshToken);
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
            message.setSubject("[????????? ??????]");
            message.setText("???????????? ???????????? " + accountId + "?????????.");
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
            message.setSubject("[???????????? ??????]");
            message.setText("??????????????? ?????????????????????.");
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.getStackTrace();
            throw new ServerErrorException();
        }
    }
    /**
     *
     * ???????????? : ???????????? ????????? email??? ?????? ??????, 201
     *           ???????????? ???????????? ???????????? 409, Signup request??? ???????????? 400, ?????? ?????? ????????? 500
     * ????????? : ???????????? accessToken ?????? refreshToken ??????, 200
     *          Login request??? ???????????? 400, ?????? ??? ????????? 404, ???????????? ????????? 401
     * ?????? ?????? : ???????????? accessToken ?????? refreshToken ??????, 200
     *            refreshToken??? ?????? ??? ????????? 404
     * ????????? ?????? : ???????????? email??? ????????? ??????, 200
     *              ?????? ??????????????? 500, ???????????? ???????????? ?????? ??? ????????? 404
     *
     **/
}
