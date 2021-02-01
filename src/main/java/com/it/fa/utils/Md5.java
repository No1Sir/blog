package com.it.fa.utils;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
    public static String MD5encode(String source) {
        /**
         * md5加密
         * @param source 数据源
         * @return 加密字符串
         */
        //检查字符串是否为空
        if (StringUtils.isBlank(source)) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] encode = messageDigest.digest(source.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte anEncode : encode) {

                String hex = Integer.toHexString(0xff & anEncode);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ignored) {
        }
        return "";
    }
}
