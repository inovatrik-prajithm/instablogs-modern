package com.kirusa.instablogs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kirusa.instablogs.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {

	@Query("SELECT b.blogId FROM Blog b WHERE b.blogCategoryId = :categoryId")
	List<Long> findBlogsInCategory(Integer categoryId);
}
