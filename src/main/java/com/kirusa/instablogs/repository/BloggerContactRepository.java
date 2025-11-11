package com.kirusa.instablogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kirusa.instablogs.model.BloggerContact;

public interface BloggerContactRepository extends JpaRepository<BloggerContact, String> {

}
