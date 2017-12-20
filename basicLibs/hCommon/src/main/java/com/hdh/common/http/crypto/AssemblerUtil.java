package com.hdh.common.http.crypto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Albert.Liu on 15/9/9.
 */
public class AssemblerUtil {


    /**
     * 组装用于前面的字符串
     *
     * @param paramValueMap
     * @param ignoreParamNameList
     * @param secret
     * @param isHeadSecret
     * @return
     */
    public static String assembleSignString(Map<String, Object> paramValueMap,
                                            List<String> ignoreParamNameList, String secret, boolean isHeadSecret) {

        List<String> filteredAndSortedParamNames = filterAndSortedParamNames(paramValueMap, ignoreParamNameList);
        StringBuilder assembledString = new StringBuilder();
        if (isHeadSecret) {
            assembledString.append(secret);
        }
        for (String paramName : filteredAndSortedParamNames) {
            Object paramValue = paramValueMap.get(paramName);
            if (paramValue != null) {
                assembledString.append(paramName).append(paramValue);
            }
        }
        assembledString.append(secret);

        return assembledString.toString();
    }


    /**
     * 对入参进行过滤
     *
     * @param paramValueMap       参数名及参数值Map
     * @param ignoreParamNameList 不参与签名运算的参数名
     * @return
     */
    private static List<String> filterParamNames(Map<String, Object> paramValueMap,
                                                 List<String> ignoreParamNameList) {
        List<String> filteredParamNames = new ArrayList<>(paramValueMap.size());
        filteredParamNames.addAll(paramValueMap.keySet());
        if (ignoreParamNameList != null && ignoreParamNameList.size() > 0) {
            for (String ignoreParamName : ignoreParamNameList) {
                filteredParamNames.remove(ignoreParamName);
            }
        }
        return filteredParamNames;
    }


    /**
     * 对入参进行过滤
     *
     * @param paramValueMap       参数名及参数值Map
     * @param ignoreParamNameList 不参与签名运算的参数名
     * @return
     */
    private static List<String> filterParamNamesWithObjectValue(Map<String, Object> paramValueMap,
                                                                List<String> ignoreParamNameList) {
        List<String> filteredParamNames = new ArrayList<>(paramValueMap.size());
        filteredParamNames.addAll(paramValueMap.keySet());
        if (ignoreParamNameList != null && ignoreParamNameList.size() > 0) {
            for (String ignoreParamName : ignoreParamNameList) {
                filteredParamNames.remove(ignoreParamName);
            }
        }
        return filteredParamNames;
    }


    /**
     * 对入参进行过滤,并对过滤后的参数进行排序
     * 使用<code>secret</code>对paramValues按以下算法进行签名： <br/>
     * uppercase(hex(sha1(secretkey1value1key2value2...secret))
     *
     * @param paramValueMap
     * @param ignoreParamNameList
     * @return
     */
    private static List<String> filterAndSortedParamNames(Map<String, Object> paramValueMap,
                                                          List<String> ignoreParamNameList) {
        List<String> filteredParamNames = filterParamNames(paramValueMap, ignoreParamNameList);
        Collections.sort(filteredParamNames);
        return filteredParamNames;
    }
}
