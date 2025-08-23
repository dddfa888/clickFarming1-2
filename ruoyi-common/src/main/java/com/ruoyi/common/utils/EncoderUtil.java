package com.ruoyi.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码加密工具类
 *
 * @author bigmall
 */
public class EncoderUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 加密
     *
     * @param password 待加密明文
     * @return 加密后的密文
     */
    public static String encoder(String password) {
        return encoder.encode(password);
    }
    /**
     * 对比
     */
    public static boolean matches(String a,String b) {
        return  encoder.matches(a, b);
    }

}
