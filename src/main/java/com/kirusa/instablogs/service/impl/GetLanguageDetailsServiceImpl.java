package com.kirusa.instablogs.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirusa.instablogs.dto.APIResponse;
import com.kirusa.instablogs.model.VbLanguageData;
import com.kirusa.instablogs.repository.VbLanguageDataRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetLanguageDetailsServiceImpl {

    private final VbLanguageDataRepository languageRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional(readOnly = true)
    public APIResponse getLanguageDetails() {

        Set<VbLanguageData> dataSet = languageRepo.findAllLanguages();

        log.info("Loaded {} languages", dataSet.size());

        List<APIResponse.LanguageItem> items = dataSet.stream()
                .map(lang -> {
                    Map<String, String> jsonMap = null;

                    try {
                    	jsonMap = objectMapper.readValue(lang.getLangData(),
                    	        new TypeReference<Map<String, String>>() {});
                    } catch (Exception e) {
                        log.error("Failed to parse lang_data for {}", lang.getCountryCode(), e);
                    }

                    return APIResponse.LanguageItem.builder()
                            .countryCode(lang.getCountryCode())
                            .countryName(lang.getCountryName())
                            .langCode(lang.getLangCode())
                            .langName(lang.getLangName())
                            .langData(jsonMap)
                            .build();
                })
                .collect(Collectors.toList());

        return APIResponse.builder()
                .status("ok")
                .languageList(items)
                .build();
    }
}
