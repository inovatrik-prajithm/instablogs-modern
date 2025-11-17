package com.kirusa.instablogs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kirusa.instablogs.model.Blogger;

public interface BloggerRepository extends JpaRepository<Blogger, Long> {
    Optional<Blogger> findByPrimaryNumber(String primaryNumber);

    Optional<Blogger> findByEmail(String email);
}
