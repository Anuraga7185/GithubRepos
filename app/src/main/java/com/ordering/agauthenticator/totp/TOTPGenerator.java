package com.ordering.agauthenticator.totp;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class TOTPGenerator {
    private static final String HMAC_ALGO = "HmacSHA1"; // or HmacSHA256, HmacSHA512
    private static final int TIME_STEP = 30; // 30 seconds
    private static final int OTP_DIGITS = 6; // 6-digit OTP

    public static String generateTOTP(String secretKey, long time) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            byte[] timeBytes = ByteBuffer.allocate(8).putLong(time / TIME_STEP).array();

            Mac mac = Mac.getInstance(HMAC_ALGO);
            mac.init(new SecretKeySpec(keyBytes, HMAC_ALGO));
            byte[] hash = mac.doFinal(timeBytes);

            int offset = hash[hash.length - 1] & 0xF;
            int otp = ((hash[offset] & 0x7F) << 24) |
                    ((hash[offset + 1] & 0xFF) << 16) |
                    ((hash[offset + 2] & 0xFF) << 8) |
                    (hash[offset + 3] & 0xFF);

            return String.format("%06d", otp % (int) Math.pow(10, OTP_DIGITS));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }
}
