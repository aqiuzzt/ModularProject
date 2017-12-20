package com.hdh.common.util;

import java.security.MessageDigest;

/**
 * 
 * md5获取摘要
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:33
 */
public class MD5 {
    private static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public MD5() {
    }

    public static String hexdigest(String var0) {
        String var1 = null;

        try {
            var1 = hexdigest(var0.getBytes());
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return var1;
    }

    public static String hexdigest(byte[] var0) {
        String var1 = null;

        try {
            MessageDigest var2 = MessageDigest.getInstance("MD5");
            var2.update(var0);
            byte[] var3 = var2.digest();
            char[] var4 = new char[32];
            int var5 = 0;

            for (int var6 = 0; var6 < 16; ++var6) {
                byte var7 = var3[var6];
                var4[var5++] = hexDigits[var7 >>> 4 & 15];
                var4[var5++] = hexDigits[var7 & 15];
            }

            var1 = new String(var4);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return var1;
    }

    public static void main(String[] var0) {
        System.out.println(hexdigest("c"));
    }
}