package com.rzr.keyboarddemo;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * create: Ren Zhongrui
 * date: 2018-06-07
 * description: AES加密工具类
 */

public class AESUtil {

    private static final String CipherMode = "AES/ECB/PKCS5Padding";

    /**
     *  AES加密
     * @param seed 密钥
     * @param content 要加密的内容
     * @return 加密后的16进制结果
     */
    public static String encrypt(String seed, String content) {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] result = encrypt(rawKey, content.getBytes());
        return byteToHex(result);
    }

    /**
     * AES解密
     *
     * @param seed 密钥
     * @param content 解密的内容
     * @return 解密后的字符串
     */
    public static String decrypt(String seed, String content) {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] contentByte = hexToByte(content);
        byte[] result = decrypt(rawKey, contentByte);
        return new String(result);
    }

    /**
     * 得到原始key
     *
     * @param seed
     * @return
     */
    private static byte[] getRawKey(byte[] seed) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "Crypto");
            secureRandom.setSeed(seed);
            keyGenerator.init(128, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES加密
     *
     * @param rawKey  原生key
     * @param content 要加密的内容
     * @return
     */
    private static byte[] encrypt(byte[] rawKey, byte[] content) {
        try {
            SecretKeySpec key = new SecretKeySpec(rawKey, "AES");
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param rawKey
     * @param content
     * @return
     */
    private static byte[] decrypt(byte[] rawKey, byte[] content) {
        try {
            SecretKeySpec key = new SecretKeySpec(rawKey, "AES");
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 节数组转成16进制字符串
     *
     * @param data
     * @return
     */
    private static String byteToHex(byte[] data) {
        StringBuffer sb = new StringBuffer(data.length * 2);
        String tmp = "";
        for (int n = 0; n < data.length; n++) {
            tmp = (java.lang.Integer.toHexString(data[n] & 0XFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString().toUpperCase(); // 转成大写
    }

    /**
     * 将16进制字符串转换成字节数组
     *
     * @param data
     * @return
     */
    private static byte[] hexToByte(String data) {
        if (data == null || data.length() < 2) {
            return new byte[0];
        }
        data = data.toLowerCase();
        int l = data.length() / 2;
        byte[] result = new byte[l];
        for (int i = 0; i < l; ++i) {
            String tmp = data.substring(2 * i, 2 * i + 2);
            result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
        }
        return result;
    }

}
