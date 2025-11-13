package com.kirusa.instablogs.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kirusa.instablogs.dto.APIRequest;
import com.kirusa.instablogs.model.KvsmsNodeNetwork;
import com.kirusa.instablogs.model.PendingRegistration;
import com.kirusa.instablogs.repository.PendingRegistrationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final PendingRegistrationRepository pendingRegistrationRepository;
    public static final int PIN_EXPIRY_MINUTES = 30;

    @Transactional
    public PendingRegistration createPhoneRegistration(String contactId, APIRequest request, KvsmsNodeNetwork node) {
        Objects.requireNonNull(contactId, "contactId required");
        var now = LocalDateTime.now();
        var pin = generateRandomPin();

        var reg = PendingRegistration.builder()
                .contactId(contactId)
                .contactType("p")
                .deviceId(request.getDeviceId())
                .deviceModel(request.getDeviceModel())
                .deviceMacAddr(request.getDeviceMacAddr())
                .deviceResolution(request.getDeviceResolution())
                .deviceDensity(request.getDeviceDensity())
                .emeiMeidEsn(request.getImeiMeidEsn())
                .isVerified(false)
                .ivClientVer(request.getClientAppVer())
                .oprInfoEdited(request.getOprInfoEdited())
                .osType(request.getClientOs())
                .osVer(request.getClientOsVer())
                .countryCode(request.getCountryCode())
                .loginId(request.getLoginName())
                .displayName(determineDisplayName(request.getLoginName(), contactId))
                .phoneNumEdited(Boolean.TRUE.equals(request.getPhoneNumEdited()))
                .encryptedPin(pin)
                .pinExpiry(now.plusMinutes(PIN_EXPIRY_MINUTES))
                .regDate(now)
                .simOperator(request.getSimOprMccMnc())
                .simOperatorNm(request.getSimOprNm())
                .simNetworkOperator(request.getSimNetworkOprMccMnc())
                .simNetworkOperatorNm(request.getSimNetworkOprNm())
                .simSerialNumber(request.getSimSerialNum())
                .kvsmsNodeId(node != null ? String.valueOf(node.getKvsmsNodeId()) : null)
                .kvsmsNetworkId(node != null ? String.valueOf(node.getKvsmsNetworkId()) : null)
                .regSecureKey("DUMMY")
                .signIn(false)
                .attempts(0)
                .appType(request.getAppType() != null ? request.getAppType() : "DEFAULT_APP")
                .build();

        var saved = pendingRegistrationRepository.save(reg);
        log.info("Created PendingRegistration regId={} contact={}", saved.getRegId(), contactId);
        return saved;
    }

    @Transactional
    public void ensurePinNotExpiredAndSave(PendingRegistration reg) {
        if (reg == null) return;
        var now = LocalDateTime.now();
        if (reg.getPinExpiry() == null || reg.getPinExpiry().isBefore(now)) {
            var newPin = generateRandomPin();
            reg.setEncryptedPin(newPin);
            reg.setPinExpiry(now.plusMinutes(PIN_EXPIRY_MINUTES));
            reg.setAttempts(0);
        }
        reg.setPinSentBy(null);
        pendingRegistrationRepository.save(reg);
    }

    public void sendPin(PendingRegistration reg) {
        log.info("Mock sendPin: otp={} to {}", reg.getEncryptedPin(), reg.getContactId());
    }

    private String determineDisplayName(String loginName, String contactId) {
        return (loginName == null || loginName.isBlank()) ? contactId : loginName;
    }

    private String generateRandomPin() {
        var pin = (int) (Math.random() * 9000) + 1000;
        return String.valueOf(pin);
    }
}
