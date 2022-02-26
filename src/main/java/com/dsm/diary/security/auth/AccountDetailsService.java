package com.dsm.diary.security.auth;

import com.dsm.diary.Exception.NotFoundException;
import com.dsm.diary.entity.account.AccountRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Getter
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new AccountDetails(
                accountRepository.findByEmail(email)
                        .orElseThrow(NotFoundException::new)
        );
    }
}
