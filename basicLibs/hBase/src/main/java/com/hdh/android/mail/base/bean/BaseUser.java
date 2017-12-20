package com.hdh.android.mail.base.bean;

import com.google.gson.annotations.SerializedName;
import com.hdh.android.mail.base.db.LitePalSupport;

public class BaseUser extends LitePalSupport {
    @SerializedName("id")
    public String userId;
    @SerializedName("others")
    public String others;
    @SerializedName("createdAt")
    public long createdAt;
    @SerializedName("updatedAt")
    public long updatedAt;
    /**
     * 生日
     */
    @SerializedName("birth")
    public long birth;
    /**
     * 籍贯
     */
    @SerializedName("nativePlace")
    public String nativePlace;
    /**
     * 证件类型
     */
    @SerializedName("identityType")
    public Integer identityType;
    /**
     * 真实姓名
     */
    @SerializedName("realName")
    public String realName;
    /**
     * 性别: 0.其他，1.男，2.女
     */
    @SerializedName("gender")
    public int gender;
    /**
     * 证件号码
     */
    @SerializedName("identityNum")
    public String identityNum;
    /**
     * 常用联系电话
     */
    @SerializedName("telNum")
    public String telNum;
    /**
     * 备用联系电话
     */
    @SerializedName("telStandbyNum")
    public String telStandbyNum;
    /**
     * 邮箱
     */
    @SerializedName("email")
    public String email;

}
