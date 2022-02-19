package com.dsm.diary.service.Util;

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
            throw new UnauthorizedException();
        }
        return authentication.getName();
    }

    public Account getAccount() {
        return accountRepository.findByAccountId(getAccountId())
                .orElseThrow(NotFoundException::new);
    }

}
