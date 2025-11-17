package com.kirusa.instablogs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kirusa.instablogs.model.BloggerContact;

public interface BloggerContactRepository extends JpaRepository<BloggerContact, String> {

	// Single row (contact_id is PRIMARY KEY)
	@Query(value = "SELECT * FROM vb_blogger_contact WHERE TRIM(contact_id) = :contactId", nativeQuery = true)
	BloggerContact findOneByContactId(@Param("contactId") String contactId);

	// Multiple rows (if needed elsewhere)
	List<BloggerContact> findAllByContactId(String contactId);

	// Blogger ID based (list)
	List<BloggerContact> findByBloggerId(Long bloggerId);

}
