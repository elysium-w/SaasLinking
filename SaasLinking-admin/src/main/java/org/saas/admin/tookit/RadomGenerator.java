package org.saas.admin.tookit;

import java.security.SecureRandom;


/**
 * 生成分组ID
 */
public final class RadomGenerator {
    public static String generateRandomDigits(){
        return generateRandomDigits(6);
    }
    public static String generateRandomDigits(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomDigit = random.nextInt(10); // 0~9之间的随机数
            sb.append(randomDigit);
        }
        return sb.toString();
    }

}
