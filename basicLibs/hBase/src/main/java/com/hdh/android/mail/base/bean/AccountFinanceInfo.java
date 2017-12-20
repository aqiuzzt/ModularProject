package com.hdh.android.mail.base.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 账号金额信息
 */
public class AccountFinanceInfo implements Serializable {

    public static final int TYPE_BALANCE= 3;//优待金

    /**
     * 3: 银积分(原优待金)
     * 7: 购物豆
     * <p>
     * 消费积分
     * 10: 红积分
     * 11: 黄积分
     * 12: 蓝积分
     * 13: 绿积分
     * 14: 橙积分
     * 15: 紫积分
     */
    public static final int TYPE_PEA = 7;
    public static final int TYPE_SILIVER_INTEGRAL = 3;
    public static final int TYPE_RED_INTEGRAL = 10;
    public static final int TYPE_YELLOW_INTEGRAL = 11;
    public static final int TYPE_BLUE_INTEGRAL = 12;
    public static final int TYPE_GREEN_INTEGRAL = 13;
    public static final int TYPE_ORANGE_INTEGRAL = 14;
    public static final int TYPE_PURPLE_INTEGRAL = 15;

    public static final int TYPE_RED_STAR = 8;//红星 废弃
    /**
     * id
     */
    @SerializedName("id")
    public String cardholderId;
    /**
     * 账号
     */
    @SerializedName("accountId")
    public String accountId;
    /**
     * 手机号
     */
    @SerializedName("mobilePhone")
    public String mobilePhone;
    /**
     * 余额
     */
    @SerializedName("balance")
    public String balance;
    /**
     * 冻结金额
     */
    @SerializedName("freezeAmount")
    public String freezeAmount;

    /**
     * 创建时间
     */
    @SerializedName("createdAt")
    public long createdAt;
    /**
     * 密码设置状态
     */
    @SerializedName("pwdStatus")
    public boolean pwdStatus;
    /**
     * 账号类型
     */
    @SerializedName("accountType")
    public int accountType;

}
