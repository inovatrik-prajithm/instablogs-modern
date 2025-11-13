package com.kirusa.instablogs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kirusa.instablogs.model.BloggerContact;

public interface BloggerContactRepository extends JpaRepository<BloggerContact, String> {
	
    List<BloggerContact> findByBloggerId(Long bloggerId);


}
