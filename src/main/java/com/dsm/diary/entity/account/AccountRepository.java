package com.dsm.diary.entity.account;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
<<<<<<< Updated upstream

=======
    Optional<Account> findByAccountId(String accountId);
    Optional<Account> findByEmail(String email);
>>>>>>> Stashed changes
}
