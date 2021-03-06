package com.dsm.diary.entity.account;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findByAccountId(String accountId);
    Optional<Account> findByEmail(String email);
    Optional<Account> findByAccountIdAndEmail(String accountId, String email);
}
