package com.kirusa.instablogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kirusa.instablogs.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

	Country findByCountryCode(String countryCode);

	Country findBySimCountryIso(String simCountryIso);
}
