package com.ruoyi.common.utils;

import java.util.Random;

/**
 * 随机数工具类
 *
 * @author mellon
 */
public class RandomUtil {

    //根据传递的参数生成对应长度的随机数字
    // 生成指定长度的随机数字字符串
    public static String generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // 第一位不能是0
        sb.append(random.nextInt(9) + 1);
        for (int i = 1; i < length; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}
