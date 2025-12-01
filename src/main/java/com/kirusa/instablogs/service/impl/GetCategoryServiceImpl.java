package com.kirusa.instablogs.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kirusa.instablogs.dto.APIResponse;
import com.kirusa.instablogs.model.Blogger;
import com.kirusa.instablogs.model.BloggerCategory;
import com.kirusa.instablogs.repository.BlogRepository;
import com.kirusa.instablogs.repository.BloggerCategoryRepository;
import com.kirusa.instablogs.repository.BloggerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetCategoryServiceImpl {

    private final BloggerRepository bloggerRepo;
    private final BloggerCategoryRepository categoryRepo;
    private final BlogRepository blogRepo;

    @Transactional(readOnly = true)
    public APIResponse getCategoryById(Long bloggerId) {

        log.info("Fetching category list for bloggerId={}", bloggerId);

        Blogger blogger = bloggerRepo.findById(bloggerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bloggerId: " + bloggerId));

        List<BloggerCategory> availableCategories =
                categoryRepo.loadSuggestionCategories("," + blogger.getCountryCode() + ",");

        List<BloggerCategory> validCategories = availableCategories.stream()
                .filter(cat -> {
                    List<Long> blogIds = blogRepo.findBlogsInCategory(cat.getCategoryId());
                    return blogIds != null && !blogIds.isEmpty();
                })
                .toList();

        List<APIResponse.CategoryItem> responseList = validCategories.stream()
                .map(c -> APIResponse.CategoryItem.builder()
                        .categoryId(c.getCategoryId())
                        .categoryDesc(c.getCategoryDesc())
                        .build())
                .toList();

        return APIResponse.builder()
                .status("ok")
                .ivUserId(blogger.getBloggerId())
                .categoryList(responseList)
                .build();
    }
}

