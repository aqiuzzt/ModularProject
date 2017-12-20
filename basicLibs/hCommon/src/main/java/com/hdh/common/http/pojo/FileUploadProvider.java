package com.hdh.common.http.pojo;

import java.util.Map;

/**
 * Desc: 文件实体
 * Author:Martin
 * Date:2016/7/23
 */
public class FileUploadProvider {
    public String appId;//appId
    public int fileType;//1图片 2视频 3其它文件
    public int businessId;//业务类型
    public String fileName;//文件名
    public String id;//文件id
    public String url;//文件地址
    public String etag;//md5值
    public String title;//原始文件名

    //oss需要的参数
    public String accessKeyId;
    public String accessKeySecret;
    public String securityToken;
    public String expiration;
    public String endpoint;
    public String bucket;
    public String objectKey;
    public Map callBack;
    public Map params;

}
