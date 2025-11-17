package com.kirusa.instablogs.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kirusa.instablogs.dto.APIRequest;
import com.kirusa.instablogs.dto.APIResponse;
import com.kirusa.instablogs.model.PendingRegistration;
import com.kirusa.instablogs.model.UserDevice;
import com.kirusa.instablogs.service.ConfigService;
import com.kirusa.instablogs.service.ContactService;
import com.kirusa.instablogs.service.PromoService;
import com.kirusa.instablogs.service.RegistrationService;
import com.kirusa.instablogs.service.UserDeviceService;
import com.kirusa.instablogs.util.RegistrationSecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerifyUserServiceImpl {

    private final RegistrationService registrationService;
    private final BloggerServiceImpl bloggerService;
    private final UserDeviceService userDeviceService;
    private final PromoService promoSvc;
    //private final VoicemailService voicemailSvc;
    private final ContactService contactSvc;
    private final ConfigService config;
    //private final SupportService supportSvc;
    private final InvitationServiceImpl invitationService;

    @Transactional
    public APIResponse handleVerifyUser(APIRequest request) {

        // Step 1️⃣ Validate secure key and pin
        PendingRegistration reg = registrationService.validateForVerification(
                request.getRegSecureKey(),
                request.getPin(),
                request.getIsSelfVerified()
        );

        // Step 2️⃣ Create or update blogger
        var blogger = bloggerService.createOrUpdateBlogger(reg);

        // Step 3️⃣ Create or update device
        UserDevice device = userDeviceService.createOrUpdate(blogger, reg);

        // Step 4️⃣ Generate secure user key
        String userSecureKey = RegistrationSecurityUtil.generateUserSecureKey(
                blogger.getBloggerId(),
                reg.getContactId(),
                device.getIvUserDeviceId()
        );
        
        String inviteUrl = invitationService.generateAndSaveInvite(
                blogger.getBloggerId(),
                "REGISTRATION",       // or whatever invite type
                device.getIvUserDeviceId(),
                device.getAppType()
        );
        
        blogger.setInviteRefCode(invitationService.generateReferralCode(blogger.getBloggerId()));

        // 🧠 Build full API response (legacy-compatible structure)
        var response = APIResponse.builder()
                .status("ok")
                .userSecureKey(userSecureKey)
                //.voicemailInfo(voicemailSvc.buildVoicemailInfo(blogger))
                .loginId(blogger.getLoginId())
                .inviteUrl(inviteUrl)
                .invRefCode(blogger.getInviteRefCode())
                .ivUserId(blogger.getBloggerId())
                .ivUserDeviceId(device.getIvUserDeviceId())
                .screenName(blogger.getDisplayName())
                .countryIsd(blogger.getCountryCode())
                .phoneLen(blogger.getPrimaryNumber() != null ? blogger.getPrimaryNumber().length() : 0)
                .pnsAppId("318755574741")
                .userContacts(contactSvc.getUserContacts(blogger))
                .docsUrl(config.getDocsUrl())
                .mqttHostName(config.getMqttHost())
                .mqttPortSsl(config.getMqttPort())
                .mqttUser(config.getMqttUser())
                .mqttPassword(config.getMqttPassword())
                //.ivSupportContactIds(supportSvc.getSupportContactsJson())
                .isProfilePicSet(blogger.getIsProfilePicSet())
                .profilePicUri(blogger.getProfilePicUri())
                .thumbnailProfilePicUri(blogger.getThumbnailProfilePicUri())
                .facebookConnection(blogger.getFacebookConnection())
                .twitterConnection(blogger.getTwitterConnection())
                .fbConnected(blogger.getFacebookConnection())
                .twConnected(blogger.getTwitterConnection())
                .fbPostEnabled(blogger.getFbPostEnabled())
                .twPostEnabled(blogger.getTwPostEnabled())
                .fbConnectUrl(config.getFbConnectUrl())
                .twConnectUrl(config.getTwConnectUrl())
                .vsmsAllowed(true)
                .lastFetchedMsgId(0)
                .lastFetchedContactTrno(0)
                .lastFetchedMsgId(0)
                .lastFetchedProfileTrno(0)
                .sendEmailForIv(true)
                .sendSmsForIv(true)
                .sendEmailForVb(true)
                .sendSmsForVb(true)
                .sendEmailForVsms(true)
                .sendSmsForVsms(true)
                .build();
        log.info("✅ User verified: regId={}, bloggerId={}", reg.getRegId(), blogger.getBloggerId());
        return response;
    }
}
