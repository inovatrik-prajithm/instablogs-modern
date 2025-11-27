package com.kirusa.instablogs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kirusa.instablogs.model.BloggerCategory;

public interface BloggerCategoryRepository extends JpaRepository<BloggerCategory, Integer> {

	// Get suggestion categories based on country logic
	@Query("""
			SELECT c FROM BloggerCategory c	
			WHERE (c.categoryId IS NOT NULL AND c.categoryId <> 99)
			AND (c.countryList IS NULL OR c.countryList LIKE %:country%)
			ORDER BY c.priority, c.categoryDesc
			""")
	List<BloggerCategory> loadSuggestionCategories(String country);
}
