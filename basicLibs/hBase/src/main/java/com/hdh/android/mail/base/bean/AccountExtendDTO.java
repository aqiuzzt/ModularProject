package com.hdh.android.mail.base.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 账号扩展信息
 */

public class AccountExtendDTO {
    @SerializedName("id")
    public String accountId;
    @SerializedName("others")
    public String others;
    @SerializedName("state")
    public int state;
    @SerializedName("createdAt")
    public long createdAt;
    @SerializedName("updatedAt")
    public long updatedAt;
    /**
     * 头像
     */
    @SerializedName("avatar")
    public String avatar;
    /**
     * 昵称
     */
    @SerializedName("nickname")
    public String nickname;
    /**
     * 未评价订单数量
     */
    @SerializedName("notEvaluate")
    public int notEvaluate;
    /**
     * 账号状态.0:无效状态;1:未审核;2:已审核,正常;3:封禁;4:冻结
     */
    @SerializedName("status")
    public int status;
    /**
     * 最近一次购买时间
     */
    @SerializedName("lastBuy")
    public long lastBuy;
    /**
     * 未支付数量
     */
    @SerializedName("unpaid")
    public int unpaid;
    /**
     * 未收货订单数量
     */
    @SerializedName("notReceived")
    public int notReceived;
    /**
     * 第一次购买时间
     */
    @SerializedName("firstBuy")
    public long firstBuy;
    /**
     * 已完成订单数量
     */
    @SerializedName("completed")
    public int completed;
    /**
     * 登录类型 1微信 2QQ 3微博 11手机
     */
    @SerializedName("loginType")
    public String loginType;
    /**
     * 支付账号信息,数组,两个账号,表示不同币种的账号
     */
    @SerializedName("financeAccounts")
    public List<AccountFinanceInfo> financeAccounts;
    /**
     * 登录令牌accessToken
     */
    @SerializedName("accessToken")
    public String accessToken;
    /**
     * IM登录密码
     */
    @SerializedName("imToken")
    public String imToken;
    /**
     * 用户基础信息
     */
    @SerializedName("userCommonDTO")
    public BaseUser userCommonDTO;

    @SerializedName("accountCommonDTO")
    public AccountCommonDTO accountCommonDTO;

    @SerializedName("role")
    public int role;

}
