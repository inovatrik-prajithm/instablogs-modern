package com.kirusa.instablogs.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kirusa.instablogs.dto.APIResponse;
import com.kirusa.instablogs.model.Blogger;
import com.kirusa.instablogs.model.BloggerContact;
import com.kirusa.instablogs.repository.BloggerContactRepository;
import com.kirusa.instablogs.service.ContactService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final BloggerContactRepository bloggerContactRepo;

    @Override
    public List<APIResponse.UserContact> getUserContacts(Blogger blogger) {

        if (blogger == null || blogger.getBloggerId() == null) {
            log.warn("⚠ getUserContacts() called with null blogger or bloggerId");
            return List.of();
        }

        List<BloggerContact> contacts =
                bloggerContactRepo.findByBloggerId(blogger.getBloggerId());

        log.info("Fetched {} contacts for bloggerId={}", contacts.size(), blogger.getBloggerId());

        return contacts.stream()
                .map(c -> APIResponse.UserContact.builder()
                        .contactId(c.getContactId())
                        .contactType(c.getContactType())
                        .countryCode(c.getCountryCode())
                        .bloggerId(c.getBloggerId())
                        .loginId(c.getLoginId())
                        .isPrimary(Boolean.TRUE.equals(c.getIsPrimary()))
                        .status(c.getStatus())
                        .isVirtual(Boolean.TRUE.equals(c.getIsVirtual()))
                        .build()
                ).toList();

    }
}
