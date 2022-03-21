package com.dsm.diary.facade;

import com.dsm.diary.entity.account.Account;
import com.dsm.diary.entity.account.AccountRepository;
import com.dsm.diary.exception.AlreadyExistsException;
import com.dsm.diary.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final AccountRepository accountRepository;

    public Account getByAccountId(String accountId) {
        return accountRepository.findByAccountId(accountId)
                .orElseThrow(NotFoundException::new);
    }

    public void accountIdAlreadyExists(String accountId) {
        if (accountRepository.findByEmail(accountId).isPresent()) {
            throw new AlreadyExistsException();
        }
    }

    public void emailAlreadyExists(String email) {
        if (accountRepository.findByEmail(email).isPresent()) {
            throw new AlreadyExistsException();
        }
    }
}
