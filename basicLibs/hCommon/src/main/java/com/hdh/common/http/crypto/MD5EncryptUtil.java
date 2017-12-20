package com.hdh.common.http.crypto;


import com.hdh.common.http.util.HttpConstants;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by albertliu on 14/10/26.
 * MD5签名
 */
public class MD5EncryptUtil {

    /**
     * 加密次数，默认为2
     */
    private static int hashIterations = 2;

    /**
     * 盐是否不可用
     */
    private static boolean saltDisabled = false;

    /**
     * 加密密码。
     *
     * @param password 密码
     * @param salt     盐值
     * @return 加密之后的密码
     * @throws IllegalArgumentException 如果password参数为空，那么就抛出{@link IllegalArgumentException}
     */
    public static String encryptPassword(String password, String salt) throws IllegalArgumentException {
        if (saltDisabled) {
            salt = null;
        }
        return new Md5Hash(password, salt, hashIterations).toHex();
    }

    /**
     * 获取加密策略 例如： MD5。
     *
     * @return 加密策略
     */
    public static String getCredentialsStrategy() {
        return Md5Hash.ALGORITHM_NAME;
    }

    /**
     * 获取加密次数。
     *
     * @return 加密次数
     */
    public static int getHashIterations() {
        return hashIterations;
    }

    public void setHashIterations(int hashIterations) {
        MD5EncryptUtil.hashIterations = hashIterations;
    }

    /*-------------- provide setter methods  ------------------*/

    /**
     * 是否开启盐值加密。
     *
     * @return 返回<code>false</code>，表示需要盐值加密，返回<code>true</code>,表示不需要盐值加密
     */
    public static boolean saltDisabled() {
        return saltDisabled;
    }

    public static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes(HttpConstants.UTF8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 对字符串进行MD5加密
     *
     * @param data
     * @return 加密后的字符数组
     * @throws IOException
     */
    public static byte[] getMD5Digest(String data) throws IOException {
        byte[] bytes = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes(HttpConstants.UTF8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 对二进制进行MD5加密
     *
     * @param data
     * @return 加密后的字符数组
     * @throws IOException
     */
    public static byte[] getMD5Digest(byte[] data) throws IOException {
        byte[] bytes=null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 对字符串进行MD5加密，返回加密后的字符串
     *
     * @param data
     * @return 加密后的字符串
     * @throws IOException
     */
    public static String getMD5DigestInString(String data) throws IOException {
        return byte2hexNoUpperCase(getMD5Digest(data));
    }

    /**
     * 对二进制进行MD5加密，返回加密后的字符串
     *
     * @param data
     * @return 加密后的字符串
     * @throws IOException
     */
    public static String getMD5DigestInBytes(byte[] data) throws IOException {
        return byte2hexNoUpperCase(getMD5Digest(data));
    }

    /**
     * 对二进制进行MD5加密，返回加密后的字符串
     *
     * @param byteList
     * @return 加密后的字符串
     * @throws IOException
     */
    public static List<String> getMD5DigestListInBytes(List<byte[]> byteList) throws IOException {
        List<String> md5List = new ArrayList<>();
        for (int i = 0; i < byteList.size(); i++) {
            byte[] byteDate = byteList.get(i);
            String md5 = byte2hexNoUpperCase(getMD5Digest(byteDate));
            md5List.add(md5);
        }
        return md5List;
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    public static String byte2hexNoUpperCase(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }

    public void setSaltDisabled(boolean disabled) {
        saltDisabled = disabled;
    }
}
