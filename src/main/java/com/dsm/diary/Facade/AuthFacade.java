package com.dsm.diary.Facade;

import com.dsm.diary.Exception.AlreadyExistsException;
import com.dsm.diary.Exception.NotFoundException;
import com.dsm.diary.Exception.ServerErrorException;
import com.dsm.diary.dto.request.SignupRequest;
import com.dsm.diary.entity.account.Account;
import com.dsm.diary.entity.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class AuthFacade {
    @Value("${mail.username}")
    private String emailUsername;

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    public void signup(SignupRequest signupRequest){
        accountIdAlreadyExists(signupRequest.getAccountId());
        emailAlreadyExists(signupRequest.getEmail());

        accountRepository.findByEmail(signupRequest.getEmail());
        signupRequest.encodePassword(passwordEncoder.encode(signupRequest.getPassword()));

        accountRepository.save(
                new Account(
                        signupRequest.getEmail(),
                        signupRequest.getName(),
                        signupRequest.getAccountId(),
                        signupRequest.getPassword()
                )
        );
    }

    public void sendSuccessEmail(String email){
        MimeMessage message = javaMailSender.createMimeMessage();

        try{
            message.setFrom(emailUsername);
            message.addRecipients(Message.RecipientType.TO,email);
            message.setSubject("[회원가입 성공]");
            message.setText("회원가입에 성공하셨습니다.");
            javaMailSender.send(message);
        }catch (MessagingException e){
            e.getStackTrace();
            throw new ServerErrorException();
        }
    }

    public void sendAccountIdEmail(String email, String accountId){
        MimeMessage message = javaMailSender.createMimeMessage();

        try{
            message.setFrom(emailUsername);
            message.addRecipients(Message.RecipientType.TO, email);
            message.setSubject("[아이디 찾기]");
            message.setText("회원님의 아이디는 " + accountId + "입니다." );
            javaMailSender.send(message);
        }catch (MessagingException e){
            e.getStackTrace();
            throw new ServerErrorException();
        }
    }


    public Account getByAccountId(String accountId){
        return accountRepository.findByAccountId(accountId)
                .orElseThrow(NotFoundException::new);
    }

    public Account getByEmail(String email){
        return accountRepository.findByEmail(email)
                .orElseThrow(NotFoundException::new);
    }

    public Account getByName(String name){
        return accountRepository.findByName(name)
                .orElseThrow(NotFoundException::new);
    }

    private void accountIdAlreadyExists(String accountId){
        if(accountRepository.findByEmail(accountId).isPresent()) {
            throw new AlreadyExistsException();
        }
    }

    private void emailAlreadyExists(String email){
        if(accountRepository.findByEmail(email).isPresent()){
            throw new AlreadyExistsException();
        }
    }
}
