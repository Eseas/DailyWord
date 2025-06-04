package com.dailyword.common.util;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;
import java.security.SecureRandom;

public class UuidUtils {

    private static final SecureRandom random = new SecureRandom();
    private static final String REF_CODE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 표준 UUID 생성 (36자리 문자열)
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString();  // 예: 52cba1cb-9017-4bb3-a9f6-7f31e01dcf8c
    }

    /**
     * UUID 기반 짧은 refCode 생성 (base64 + substring, 8자리)
     */
    public static String generateShortRefCodeFromUuid() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());

        // Base64 URL-safe 인코딩 → 길이 22~24, 잘라서 8자리로
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(buffer.array())
                .substring(0, 8); // 예: X9a7B3fP
    }

    /**
     * 랜덤 refCode 생성 (알파벳+숫자 조합, 8자리)
     */
    public static String generateRandomRefCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(REF_CODE_CHARS.charAt(random.nextInt(REF_CODE_CHARS.length())));
        }
        return sb.toString();  // 예: Ab1zX7Qd
    }
}
