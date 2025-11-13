package com.kirusa.instablogs.util;

import com.kirusa.instablogs.model.KvsmsNodeNetwork;
import com.kirusa.instablogs.model.PendingRegistration;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

public final class RegistrationSecurityUtil {

    private static final String SECRET_KEY = "_2gt^&17h*lmkk9)";
    private static final String HMAC_KEY = "KirusaHmacKey#2025!";

    private RegistrationSecurityUtil() {}

    public static String generateAdvancedRegSecureKey(PendingRegistration r, KvsmsNodeNetwork n) {
        try {
            var cc = n != null ? n.getCountryCode() : "NA";
            var ct = r.getContactType() != null ? r.getContactType() : "P";
            var raw = "%d:%s:%s:%s:%d:%s:%s".formatted(
                    r.getRegId(), r.getPin(), r.getContactId(),
                    cc, Instant.now().toEpochMilli(), ct,
                    UUID.randomUUID().toString().replace("-", "")
            );

            var hash = MessageDigest.getInstance("SHA-256").digest(raw.getBytes(StandardCharsets.UTF_8));
            var aes = CryptoUtil.encrypt(Base64.getEncoder().encodeToString(hash), SECRET_KEY);
            var sig = hmac((String) aes, HMAC_KEY); // 👈 added cast to String

            return Base64.getUrlEncoder().withoutPadding()
                    .encodeToString((aes + "." + sig).getBytes(StandardCharsets.UTF_8));

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
}
