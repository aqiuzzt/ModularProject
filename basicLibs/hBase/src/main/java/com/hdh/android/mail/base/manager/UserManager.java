package com.hdh.android.mail.base.manager;

import android.text.TextUtils;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.bean.AccountExtendDTO;
import com.hdh.android.mail.base.Constant;
import com.hdh.android.mail.base.utils.ProfileStrorageUtil;

/**
 * 用户信息管理
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/30 11:30
 */
public class UserManager {

    private static UserManager sInstance;
    private AccountExtendDTO mAccountExtendDTO;
    private String mAccessToken;
    private String mPhoneNumber;
    private String mAccountId;
    private String mEmail;
    private int mUserRole;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (sInstance == null) {
            synchronized (UserManager.class) {
                sInstance = new UserManager();
            }
        }
        return sInstance;
    }

    public void setAccountProvider(AccountExtendDTO account) {
        this.mAccountExtendDTO = account;
        ProfileStrorageUtil.saveUserInfo(account);
    }

    public AccountExtendDTO getAccountProvider() {
        if (mAccountExtendDTO != null)
            return mAccountExtendDTO;
        return mAccountExtendDTO = ProfileStrorageUtil.getUserInfo();
    }

    public void setAccessToken(String accessToken) {
        this.mAccessToken = accessToken;
        ProfileStrorageUtil.setAccessToken(accessToken);
    }

    public String getAccessToken() {
        if (this.mAccessToken != null) return mAccessToken;
        return ProfileStrorageUtil.getAccessToken();
    }

    /**
     * 这个可能是账号ID
     *
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.mPhoneNumber = phoneNumber;
        ProfileStrorageUtil.setPhoneNumber(phoneNumber);
    }

    /**
     * 这个可能是账号ID
     *
     * @return
     */
    public String getPhoneNumber() {
        if (this.mPhoneNumber != null) return mPhoneNumber;
        return ProfileStrorageUtil.getPhoneNumber();
    }

    /**
     * 这个可能是账号ID
     *
     * @param mEmail
     */
    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
        ProfileStrorageUtil.setEmail(mEmail);
    }

    /**
     * 这个可能是账号ID
     *
     * @return
     */
    public String getEmail() {
        if (this.mEmail != null) return mEmail;
        return ProfileStrorageUtil.getEmail();
    }

    public int getmUserRole() {
        return ProfileStrorageUtil.getUerRole();
    }

    public void setmUserRole(int mUserRole) {
        this.mUserRole = mUserRole;
        ProfileStrorageUtil.setUserRole(mUserRole);
    }


    public String getAccountId() {
        if (mAccountId != null) return mAccountId;
        return ProfileStrorageUtil.getAccountId();
    }

    public void setAccountId(String accountId) {
        this.mAccountId = accountId;
        ProfileStrorageUtil.setAccountId(accountId);
    }

    public void clear() {
        mAccessToken = null;
        setAccessToken(null);
        setAccountProvider(null);
    }

    /**
     * 是否是商家
     *
     * @return
     */
    public boolean isSeller() {
        if (BaseApplication.get().getUserManager().getAccountProvider() != null
                && !TextUtils.isEmpty(String.valueOf(BaseApplication.get().getUserManager().getAccountProvider().role))
                && (BaseApplication.get().getUserManager().getAccountProvider().role == Constant.ROLE_MERCHANT)) {
            return true;
        }
        return false;
    }

    /**
     * 是否是军人家属
     *
     * @return ROLE_CONSUMER
     */
    public boolean isConsumer() {
        if (BaseApplication.get().getUserManager().getAccountProvider() != null
                && !TextUtils.isEmpty(String.valueOf(BaseApplication.get().getUserManager().getAccountProvider().role))
                && (BaseApplication.get().getUserManager().getAccountProvider().role == Constant.ROLE_VIP)) {
            return true;
        }
        return false;
    }


    /**
     * 是否登录
     *
     * @return
     */
    public boolean isLogin() {
        String accessToken = BaseApplication.get().getUserManager().getAccessToken();
        if (!TextUtils.isEmpty(accessToken)) {
            return true;
        }
        return false;
    }

    /**
     * 有token,但是认为没有完善信息,直接留在当前页面,需要手动登录
     *
     * @return ture 则没有实名认证
     */
    public boolean isVerified() {
        if (BaseApplication.get().getUserManager().getAccountProvider() != null
                && (BaseApplication.get().getUserManager().getAccountProvider().userCommonDTO == null
                || TextUtils.isEmpty(BaseApplication.get().getUserManager().getAccountProvider().userCommonDTO.identityNum)
                || "0".equals(BaseApplication.get().getUserManager().getAccountProvider().userCommonDTO.identityNum))) {

            return true;
        }
        return false;
    }

}
