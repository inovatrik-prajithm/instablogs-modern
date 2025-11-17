package com.kirusa.instablogs.util;

import com.kirusa.instablogs.model.KvsmsNodeNetwork;
import com.kirusa.instablogs.model.PendingRegistration;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

public final class RegistrationSecurityUtil {

    private static final String SECRET_KEY = "_2gt^&17h*lmkk9)";
    private static final String HMAC_KEY = "KirusaHmacKey#2025!";

    private RegistrationSecurityUtil() {}

    /**
     * 🔐 Simplified version for local/integration testing.
     * Generates a regSecureKey that includes the plain regId inside encrypted data,
     * so that the verification flow can extract regId properly.
     */
    public static String generateAdvancedRegSecureKey(PendingRegistration r, KvsmsNodeNetwork n) {
        try {
            var cc = (n != null ? n.getCountryCode() : "NA");
            var ct = (r.getContactType() != null ? r.getContactType() : "P");

            // 👇 Plain text content (regId is directly stored)
            var raw = "%d:%s:%s:%s:%d:%s:%s".formatted(
                    r.getRegId(),
                    r.getPin(),
                    r.getContactId(),
                    cc,
                    Instant.now().toEpochMilli(),
                    ct,
                    UUID.randomUUID().toString().replace("-", "")
            );

            // 👇 Direct encryption (no SHA-256 hashing)
            var aes = CryptoUtil.encrypt(raw, SECRET_KEY);
            var sig = hmac(aes, HMAC_KEY);

            // ✅ Return in dot-separated form
            return aes + "." + sig;

        } catch (Exception e) {
            throw new RuntimeException("regSecureKey generation failed: " + e.getMessage(), e);
        }
    }

    private static String hmac(String data, String key) throws Exception {
        var mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * Generate user secure key for existing user
     */
    public static String generateUserSecureKey(Long userId, String contactId, Long userDeviceId) {
        if (userId == null || contactId == null || userDeviceId == null) {
            throw new IllegalArgumentException("Invalid input for user secure key generation");
        }
        try {
            String raw = userId + ":" + contactId + ":" + userDeviceId;
            return CryptoUtil.encrypt(raw);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate userSecureKey: " + e.getMessage(), e);
        }
    }
}
