package com.hdh.common.http.crypto;


import com.hdh.common.util.LogUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:SignUtils
 * @Description: 签名工具类
 * @Author Albert
 * @Date:2013-1-21 上午9:53:51
 * @Remarks:
 * @Version:V1.1
 */
public class SignUtils {
    /**
     * MD5签名算法:<br/>
     * 使用<code>secret</code>对paramValues按以下算法进行签名： <br/>
     * uppercase(hex(sha1(secretkey1value1key2value2...secret))
     *
     * @param paramValueMap       参数名和参数值对应的Map
     * @param ignoreParamNameList 不参与签名运算的参数名
     * @param secret              秘钥
     * @param isHeadSecret        拼接的字符串头部是不是secret（默认情况下只有尾部是secret）
     * @return
     */
    public static String signUseMD5(Map<String, Object> paramValueMap,
                                    List<String> ignoreParamNameList, String secret, boolean isHeadSecret) {
        String sign = null;
        try {
            String signString = AssemblerUtil.assembleSignString(paramValueMap, ignoreParamNameList,
                    secret, isHeadSecret);
            LogUtil.d("sign-before: " + signString);
            byte[] sha1Digest = MD5EncryptUtil.getMD5Digest(signString);
            sign = MD5EncryptUtil.byte2hex(sha1Digest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sign;
    }

}
