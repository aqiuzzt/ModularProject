package com.hdh.common.util.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * SHA-1 加密 不可逆（Secure Hash Algorithm，安全散列算法）
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/22 09:52
 */
public class SHAUtils {

    private SHAUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * SHA-512 加密
     *
     * @param data
     * @return
     */
    public static String encryptSHA(byte[] data) {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA-512");
            sha.update(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] resultBytes = sha.digest();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < resultBytes.length; i++) {
            if (Integer.toHexString(0xFF & resultBytes[i]).length() == 1) {
                builder.append("0").append(
                        Integer.toHexString(0xFF & resultBytes[i]));
            } else {
                builder.append(Integer.toHexString(0xFF & resultBytes[i]));
            }
        }
        return builder.toString();
    }

}
