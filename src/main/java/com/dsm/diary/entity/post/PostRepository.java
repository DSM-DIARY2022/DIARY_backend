package com.dsm.diary.entity.post;

import com.dsm.diary.entity.account.Account;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post , Long> {
    Optional<Post> findById(Long id);
    List<Post> findAllByAccountOrderByCreatedDateDesc(Account account);
    List<Post> findAllByAccountAndFeelingOrderByCreatedDateDesc(Account account, Integer feeling);
    List<Post> findAllByCreatedDateOrderByCreatedDateDesc(LocalDate date);
    List<Post> findAllByCreatedDate(LocalDate date);
}