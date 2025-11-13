package com.kirusa.instablogs.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public final class CryptoUtil {

    private static final String KEY = "_2gt^&17h*lmkk9)";
    private static final String CRC = "T*9$~]";
    private static final String AES = "AES";

    private CryptoUtil() {}

    public static String encrypt(String input) {
        return encrypt(input, KEY);
    }

    public static String encrypt(String input, String customKey) {
        return input == null ? null : process(input, customKey, Cipher.ENCRYPT_MODE);
    }

    public static String decrypt(String encoded) {
        return decrypt(encoded, KEY);
    }

    public static String decrypt(String encoded, String customKey) {
        return encoded == null ? null : process(encoded, customKey, Cipher.DECRYPT_MODE);
    }

    private static String process(String text, String key, int mode) {
        try {
            var digest = MessageDigest.getInstance("SHA-256").digest(key.getBytes(StandardCharsets.UTF_8));
            var keySpec = new SecretKeySpec(digest, 0, 16, AES);
            var cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(mode, keySpec);

            byte[] result;
            if (mode == Cipher.ENCRYPT_MODE) {
                var input = (CRC + text).getBytes(StandardCharsets.UTF_8);
                result = cipher.doFinal(input);
                return Base64.getEncoder().encodeToString(result);
            } else {
                var decoded = Base64.getDecoder().decode(text);
                var output = new String(cipher.doFinal(decoded), StandardCharsets.UTF_8);
                return output.startsWith(CRC) ? output.substring(CRC.length()) : output;
            }

        } catch (Exception e) {
            throw new RuntimeException("Crypto operation failed: " + e.getMessage(), e);
        }
    }
}
