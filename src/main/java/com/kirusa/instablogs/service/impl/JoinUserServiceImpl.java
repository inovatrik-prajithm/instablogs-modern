package com.kirusa.instablogs.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.kirusa.instablogs.dto.APIRequest;
import com.kirusa.instablogs.dto.APIResponse;
import com.kirusa.instablogs.model.Blogger;
import com.kirusa.instablogs.model.BloggerContact;
import com.kirusa.instablogs.model.PendingRegistration;
import com.kirusa.instablogs.repository.BloggerContactRepository;
import com.kirusa.instablogs.repository.BloggerRepository;
import com.kirusa.instablogs.repository.PendingRegistrationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinUserServiceImpl {

    private final BloggerRepository bloggerRepository;
    private final BloggerContactRepository bloggerContactRepository;
    private final PendingRegistrationRepository pendingRegistrationRepository;
    private final BloggerServiceImpl bloggerService;

    public APIResponse handleJoinUser(APIRequest request) {

        String contactId = request.getContactId();
        String deviceId = request.getDeviceId();
        Boolean phoneNumEdited = request.getPhoneNumEdited() != null ? request.getPhoneNumEdited() : false;
        
        String clientAppVer = request.getClientAppVer();
        String clientOs = request.getClientOs();
        String countryCode = request.getCountryCode();

        boolean newUser = true;
        boolean newReachMeUser = true;
        boolean signInOp = false;
        boolean chkRegistration = false;

        // Fetch BloggerContact
        BloggerContact bloggerContact = bloggerContactRepository.findById(contactId).orElse(null);
        Blogger blogger = null;
        if (bloggerContact != null) {
            Optional<Blogger> bloggerOpt = bloggerRepository.findById(bloggerContact.getBloggerId());
            if (bloggerOpt.isPresent()) {
                blogger = bloggerOpt.get();

                // Check if this is a ReachMe user
                newReachMeUser = !bloggerService.isReachMeUser(blogger.getBloggerId());

                // Determine new IV / Ring / Blogs user
                newUser = !(bloggerService.isIVUser(blogger) ||
                            bloggerService.isRingUser(blogger.getBloggerId()) ||
                            bloggerService.isBlogsUser(blogger.getBloggerId()));

                // sign-in operation if device count > 0
                signInOp = (blogger.getDeviceCnt() != null && blogger.getDeviceCnt() > 0);

                // If no devices connected, mark for registration
                if (blogger.getDeviceCnt() == 0) {
                    chkRegistration = true;
                    signInOp = false;
                }
            } else {
                // No Blogger found, new registration
                chkRegistration = true;
            }
        } else {
            // No BloggerContact found, new registration
            chkRegistration = true;
        }

        // Handle PendingRegistration
        PendingRegistration registration = pendingRegistrationRepository
                .findByContactIdAndDeviceId(contactId, deviceId)
                .orElseGet(() -> registerNewUser(contactId, request));

        // Implicit verification: if phone not edited and existing user
        boolean implicitVerification = !phoneNumEdited && !signInOp;

        // Handle OTP logic
        String action = "";
        if (phoneNumEdited || chkRegistration) {
            action = "OTP_SENT";
            sendPhoneValidationCode(registration);
        }

        // Generate secure key
        String regSecureKey = generateRegSecureKey(registration);

        // Build response
        APIResponse response = new APIResponse();
        response.setRegId(String.valueOf(registration.getRegId()));
        response.setRegSecureKey(regSecureKey);
        response.setAction(action);
        response.setNewIvUser(newUser);
        response.setNewReachMeUser(newReachMeUser);
        response.setPhoneNumEdited(phoneNumEdited);
        response.setUserVerified(implicitVerification);

        // Optionally: fetch user contacts if blogger exists
        if (blogger != null) {
            Set<Object> contacts = fetchContacts(blogger.getBloggerId());
            response.setUserContacts(contacts);
        }

        return response;
    }

    private PendingRegistration registerNewUser(String contactId, APIRequest request) {

        String generatedPin = generateRandomPin(); // implement your pin generator
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime pinExpiry = now.plusMinutes(30); // example expiry, adjust as needed

        PendingRegistration registration = PendingRegistration.builder()
                .contactId(contactId)
                .contactType("PHONE") // hardcoded default
                .deviceModel(request.getDeviceModel())
                .deviceMacAddr(request.getDeviceMacAddr())
                .deviceDensity(request.getDeviceDensity())
                .deviceResolution(request.getDeviceResolution())
                .osType(request.getClientOs())
                .osVer(request.getClientOsVer())
                .ivClientVer(request.getClientAppVer())
                .phoneNumEdited(Boolean.TRUE.equals(request.getPhoneNumEdited()))
                .countryCode(request.getCountryCode())
                .encryptedPin(generatedPin)
                .pinExpiry(pinExpiry)
                .isVerified(false)
                .regDate(now)
                .signIn(false)
                .attempts(0)
                .regSecureKey("DUMMY")
                .appType("DEFAULT_APP") // set default or map from APIRequest if exists
                .build();

        return pendingRegistrationRepository.save(registration);
    }

    private String generateRandomPin() {
        // Simple 4-digit OTP, replace with real generator
        int pin = (int)(Math.random() * 9000) + 1000;
        return String.valueOf(pin);
    }

    private void sendPhoneValidationCode(PendingRegistration registration) {
        // Mock method: implement OTP sending logic here
        System.out.println("Sending OTP " + registration.getPin() + " to " + registration.getContactId());
    }

    private String generateRegSecureKey(PendingRegistration registration) {
        return registration.getContactId() + "_" + registration.getPin();
    }

    private Set<Object> fetchContacts(Long bloggerId) {
        // Mock method: fetch user contacts logic here
        return Set.of();
    }
}
