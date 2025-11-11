package com.kirusa.instablogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kirusa.instablogs.model.Blogger;

public interface BloggerRepository extends JpaRepository<Blogger, Long> {
}
