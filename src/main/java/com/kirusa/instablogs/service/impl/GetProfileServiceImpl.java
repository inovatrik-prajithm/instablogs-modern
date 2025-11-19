package com.kirusa.instablogs.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kirusa.instablogs.dto.APIResponse;
import com.kirusa.instablogs.model.Blogger;
import com.kirusa.instablogs.repository.BloggerRepository;
import com.kirusa.instablogs.service.ConfigService;
import com.kirusa.instablogs.service.ContactService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Modern Java 25 style: Get profile handler.
 *
 * - Uses APIRequest and APIResponse DTOs (same style as join_user/verify_user)
 * - Returns a legacy-compatible profile response (most fields from old handler)
 *
 * Notes:
 * - APIRequest currently (per DTO you provided) does not contain `for_user_id`.
 *   If you add `for_user_id` to APIRequest, uncomment the related section below to
 *   allow fetching another user's profile.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetProfileServiceImpl {

    private final BloggerRepository bloggerRepository;
    private final ContactService contactService;
    private final ConfigService config;

    @Transactional(readOnly = true)
    public APIResponse getProfileById(Long bloggerId) {

        // 1) Fetch blogger
        Blogger blogger = bloggerRepository.findById(bloggerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bloggerId: " + bloggerId));

        // 2) Get device (optional)
       // Optional<UserDevice> deviceOpt = userDeviceRepository.findTopByBloggerIdOrderByIvUserDeviceIdDesc(bloggerId);

        // 3) Build response (same fields as POST API)
        APIResponse.APIResponseBuilder builder = APIResponse.builder()
                .status("ok")
                .ivUserId(blogger.getBloggerId())
                .loginId(blogger.getLoginId())
                .screenName(blogger.getDisplayName())
                .countryIsd(blogger.getCountryCode())
                .phoneLen(blogger.getPrimaryNumber() != null ? blogger.getPrimaryNumber().length() : 0)
                .displayName(blogger.getDisplayName())
                .bloggerType(blogger.getBloggerType())
                .followersCnt(blogger.getFollowersCnt())
                .followingsCnt(blogger.getFollowingsCnt())
                .isProfilePicSet(blogger.getIsProfilePicSet())
                .thumbnailProfilePicUri(blogger.getThumbnailProfilePicUri())
                .profilePicUri(blogger.getProfilePicUri())
                .facebookConnection(Boolean.TRUE.equals(blogger.getFacebookConnection()))
                .twitterConnection(Boolean.TRUE.equals(blogger.getTwitterConnection()))
                .fbConnected(Boolean.TRUE.equals(blogger.getFacebookConnection()))
                .twConnected(Boolean.TRUE.equals(blogger.getTwitterConnection()))
                .fbPostEnabled(Boolean.TRUE.equals(blogger.getFbPostEnabled()))
                .twPostEnabled(Boolean.TRUE.equals(blogger.getTwPostEnabled()))
                .pnsAppId(config.getPnsAppId())
                .docsUrl(config.getDocsUrl())
                .mqttHostName(config.getMqttHost())
                .mqttPortSsl(config.getMqttPort())
                .mqttUser(config.getMqttUser())
                .mqttPassword(config.getMqttPassword())
                .userContacts(contactService.getUserContacts(blogger))
                .lastFetchedMsgId(0)
                .lastFetchedContactTrno(0)
                .lastFetchedProfileTrno(0)
                .sendEmailForIv(true)
                .sendSmsForIv(true)
                .sendEmailForVb(true)
                .sendSmsForVb(true)
                .sendEmailForVsms(true)
                .sendSmsForVsms(true);

        // Add iv_user_device_id if exists
        //deviceOpt.ifPresent(d -> builder.ivUserDeviceId(d.getIvUserDeviceId()));

        return builder.build();
    }

}
