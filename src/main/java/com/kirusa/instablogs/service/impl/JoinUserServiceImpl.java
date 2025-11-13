package com.kirusa.instablogs.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kirusa.instablogs.dto.APIRequest;
import com.kirusa.instablogs.dto.APIResponse;
import com.kirusa.instablogs.exception.InvalidDeviceIdException;
import com.kirusa.instablogs.repository.BloggerContactRepository;
import com.kirusa.instablogs.repository.BloggerRepository;
import com.kirusa.instablogs.repository.PendingRegistrationRepository;
import com.kirusa.instablogs.service.CountryService;
import com.kirusa.instablogs.service.NetworkService;
import com.kirusa.instablogs.service.RegistrationService;
import com.kirusa.instablogs.util.RegistrationSecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles the join_user API request (modernized Java 25 version).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JoinUserServiceImpl {

    private final BloggerRepository bloggerRepo;
    private final BloggerContactRepository contactRepo;
    private final PendingRegistrationRepository regRepo;
    private final BloggerServiceImpl bloggerSvc;
    private final RegistrationService regSvc;
    private final CountryService countrySvc;
    private final NetworkService networkSvc;

    /**
     * Entry point for the join_user flow (modernized).
     */
    @Transactional
    public APIResponse handleJoinUser(APIRequest req) {

        var contactId = req.getContactId();
        var deviceId  = req.getDeviceId();
        var osType    = req.getClientOs();

        if ("a".equalsIgnoreCase(osType)
                && (deviceId == null || !(deviceId.startsWith("rm") || deviceId.startsWith("iv") || deviceId.startsWith("vb"))))
            throw new InvalidDeviceIdException("Invalid Device ID: " + deviceId);

        var phoneEdited = Boolean.TRUE.equals(req.getPhoneNumEdited());
        var implicitVerify = !phoneEdited;

        var country = countrySvc.resolveCountry(req.getCountryCode(), req.getSimCountryIso());
        var network = networkSvc.resolveNodeNetwork(req.getSimNetworkOprMccMnc(),
                                                    req.getSimOprMccMnc(), country, req.getPhoneNumEdited());

        var contact = contactRepo.findById(contactId).orElse(null);
        var blogger = contact == null ? null : bloggerRepo.findById(contact.getBloggerId()).orElse(null);

        boolean newUser = true, newRMUser = true, chkReg = contact == null, signInOp = false;

        if (blogger != null) {
            newRMUser = !bloggerSvc.isReachMeUser(blogger.getBloggerId());
            newUser = !(bloggerSvc.isIVUser(blogger)
                    || bloggerSvc.isRingUser(blogger.getBloggerId())
                    || bloggerSvc.isBlogsUser(blogger.getBloggerId()));

            var devCnt = blogger.getDeviceCnt() == null ? 0 : blogger.getDeviceCnt();
            signInOp = devCnt > 0;
            chkReg = devCnt >= 0; // always check registration per legacy logic
        }

        var reg = regRepo.findByContactIdAndDeviceId(contactId, deviceId)
                .orElseGet(() -> regSvc.createPhoneRegistration(contactId, req, network));

        var action = (phoneEdited || chkReg) ? "otp_sent" : "";
        if (!action.isEmpty()) {
            regSvc.ensurePinNotExpiredAndSave(reg);
            regSvc.sendPin(reg);
        }

        var secureKey = RegistrationSecurityUtil.generateAdvancedRegSecureKey(reg, network);

        var response = APIResponse.builder()
                .status("ok")
                .regId(String.valueOf(reg.getRegId()))
                .regSecureKey(secureKey)
                .action(action)
                .newIvUser(newUser)
                .newReachMeUser(newRMUser)
                .phoneNumEdited(phoneEdited)
                .userVerified(implicitVerify)
                .pnsAppId("318755574741")
                .docsUrl("http://localhost:8080/vobolo/iv/docs/")
                .fbConnectUrl("http://localhost:8080/vobolo/iv/fbc/")
                .twConnectUrl("http://localhost:8080/vobolo/iv/twc/")
                .obdTimegapSec(45)
                .ringExpiryMin(30)
                .chatHostname("localhost")
                .chatPortSsl("1883")
                .chatUser("guest")
                .chatPassword("guest")
                .userContacts(blogger != null ? fetchContacts(blogger.getBloggerId()) : null)
                .build();

        log.info("join_user processed: contactId={} newUser={} action={}", contactId, newUser, action);
        return response;
    }

    /**
     * Fetches all contacts for a blogger (maps entity → DTO).
     */
    private List<APIResponse.UserContact> fetchContacts(Long bloggerId) {
        return contactRepo.findByBloggerId(bloggerId).stream()
                .map(c -> APIResponse.UserContact.builder()
                        .contactId(c.getContactId())
                        .contactType(c.getContactType())
                        .countryCode(c.getCountryCode())
                        .bloggerId(c.getBloggerId())
                        .isPrimary(Boolean.TRUE.equals(c.getIsPrimary()))
                        .status(c.getStatus())
                        .isVirtual(Boolean.TRUE.equals(c.getIsVirtual()))
                        .build())
                .toList();
    }
}
