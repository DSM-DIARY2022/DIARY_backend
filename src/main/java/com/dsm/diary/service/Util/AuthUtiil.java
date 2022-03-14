package com.dsm.diary.service.Util;

import com.dsm.diary.Exception.ForbiddenException;
import com.dsm.diary.Exception.NotFoundException;
import com.dsm.diary.Exception.UnauthorizedException;
import com.dsm.diary.entity.account.Account;
import com.dsm.diary.entity.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthUtiil {

    private final AccountRepository accountRepository;

    public String getAccountId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("로그인이 되지 않았습니다.");
        }
        return authentication.getName();
    }

    public Account getAccount() {
        return accountRepository.findByAccountId(getAccountId())
                .orElseThrow(()-> new NotFoundException("사용자를 찾을 수 없습니다."));
    }

    public void verificationAccount(Account account) {
        if(!(getAccount() == account)) {
            throw new ForbiddenException("일기의 작성자가 아니므로 일기를 볼 수 없습니다.");
        }
    }

}
