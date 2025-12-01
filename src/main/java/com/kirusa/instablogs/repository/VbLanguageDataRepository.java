package com.kirusa.instablogs.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kirusa.instablogs.model.VbLanguageData;

public interface VbLanguageDataRepository extends JpaRepository<VbLanguageData, Long> {

	@Query("SELECT v FROM VbLanguageData v")
	Set<VbLanguageData> findAllLanguages(); // same behaviour as old DAO
}
