package com.kirusa.instablogs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kirusa.instablogs.model.Country;
import com.kirusa.instablogs.repository.CountryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryService {

    private final CountryRepository countryRepository;

    @Value("${instablogs.default-country-iso:IN}")
    private String defaultCountryIso;

    /**
     * Resolve the Country safely.
     * Priority: providedCountryCode → simCountryIso → defaultCountryIso
     */
    public Country resolveCountry(String providedCountryCode, String simCountryIso) {
        return findCountry(providedCountryCode)
                .or(() -> findCountry(simCountryIso))
                .or(() -> findCountry(defaultCountryIso))
                .orElseGet(() -> {
                    log.warn("No valid country found, using dummy fallback for ISO={}", defaultCountryIso);
                    var fallback = new Country();
                    fallback.setCountryCode(defaultCountryIso);
                    fallback.setSimCountryIso(defaultCountryIso.toLowerCase());
                    fallback.setCountryName("Default");
                    return fallback;
                });
    }

    private java.util.Optional<Country> findCountry(String code) {
        if (code == null || code.isBlank()) return java.util.Optional.empty();
        return java.util.Optional.ofNullable(
                countryRepository.findByCountryCode(code)
        ).or(() -> java.util.Optional.ofNullable(
                countryRepository.findBySimCountryIso(code)
        ));
    }
}
