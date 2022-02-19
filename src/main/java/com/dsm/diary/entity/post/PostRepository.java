package com.dsm.diary.entity.post;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {
    Optional<Post> findById(Long id);
}
