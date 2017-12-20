package com.hdh.android.mail.base.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.Constant;
import com.hdh.common.util.HSON;
import com.hdh.android.mail.base.bean.City;
import com.hdh.android.mail.base.bean.AccountExtendDTO;

/**
 * 基本信息存储工具类
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/30 11:31
 */
public class ProfileStrorageUtil {
    private static ProfileStorage mStorage;
    private static ProfileStorage mKeepStorage;
    private static long diffTimestamp = -1L;

    public static final void init(@NonNull Context appContext) {
        appContext = appContext.getApplicationContext();
        if (mStorage != null) throw new IllegalStateException("请直接在Application中初始化!");
        mStorage = new ProfileStorage(appContext, Constant.Storage.PREFERENCE_NAME);
        mKeepStorage = new ProfileStorage(appContext, Constant.Storage.PREFERENCE_KEEP_NAME);
    }

    public static String getAccountId() {
        return mStorage.getString("user_account_id");
    }

    public static void setAccountId(String accountId) {
        mStorage.apply("user_account_id", accountId);
    }

    public static String getDefaultCategoryId() {
        return mStorage.getString("default_catogory_id");
    }

    public static void setDefaultCategoryId(String id) {
        mStorage.apply("default_catogory_id", id);
    }

    public static boolean hasSetPaymentPassword() {
        return mStorage.getBoolean("has_set_pay_psw", false);
    }

    public static void setPaymentPasswordStatus() {
        mStorage.commit("has_set_pay_psw", true);
    }

    public static String getCurrentCityId() {
        return mStorage.getString("current_city_id", "291"); //默认深圳
    }

    public static void setCurrentCityId(String cityId) {
        mStorage.commit("current_city_id", cityId);
    }

    public static City getCurrentCity() {
        City city = mStorage.getJsonObj("current_city", City.class);
        if (city == null) {
            city = new City();
            city.name = "深圳";
            city.id = "2140022054426476345";
        }
        return city;
    }


    public static void setCurrentCity(City city) {
        mStorage.commitJson("current_city", HSON.toJson(city));
    }

    public static String getAvatar() {
        return mStorage.getString("avatar", "");
    }

    /**
     * 头像链接存储
     *
     * @param avatar
     */
    public static void setAvatar(String avatar) {
        mStorage.apply("avatar", avatar);
    }

    public static String getNickname() {
        return mStorage.getString("nick", "");

    }

    public static String getShowName() {
        String name = getNickname();
        if (TextUtils.isEmpty(name)) {
            name = "ID:" + getAccountId();
        }
        return name;
    }

    public static void setNickname(String nickname) {
        mStorage.apply("nick", nickname);
    }

    public static void setPhoneNumber(String phone) {
        mKeepStorage.apply("phone_number", phone);
    }

    public static String getPhoneNumber() {
        return mKeepStorage.getString("phone_number");
    }

    public static void setEmail(String phone) {
        mKeepStorage.apply("account_email", phone);
    }

    public static String getEmail() {
        return mKeepStorage.getString("account_email");
    }

    public static void saveUserInfo(AccountExtendDTO account) {
        if (account != null) {
            if (!TextUtils.isEmpty(account.avatar)) {
                setAvatar(account.avatar);
            }
            if (!TextUtils.isEmpty(account.nickname)) {
                setNickname(account.nickname);
            }
            saveUserInfo(HSON.toJson(account));
        }
    }

    public static void saveUserInfo(String account) {
        mStorage.commitJson("user_all_info", account);
    }

    public static AccountExtendDTO getUserInfo() {
        return mStorage.getJsonObj("user_all_info", AccountExtendDTO.class);
    }

    public static void setAccessToken(String accessToken) {
        mStorage.commit("user_access_token", accessToken);
    }

    public static String getAccessToken() {
        return mStorage.getString("user_access_token");
    }

    public static void logout() {
        mStorage.clear();
    }

    public static void putDiffTimestamp(long timestamp) {
        mKeepStorage.apply("diff_time_stamp", timestamp);
    }


    public static long getDiffTimestamp() {
        return mKeepStorage.getLong("diff_time_stamp", -1L);
    }

    public static int getUerRole() {
        return mStorage.getInt("user_role");
    }

    public static void setUserRole(int userRole) {
        mStorage.apply("user_role", userRole);
    }

    public static void clearAllInfo() {
        mKeepStorage.clear();
        mStorage.clear();

    }

    public static void setOrderTansType(String tansType) {
        mStorage.apply("order_detail_tans_type", tansType);
    }

    /**
     * 订单配送方式
     *
     * @return
     */
    public static String getOrderTansType() {
        return mStorage.getString("order_detail_tans_type", "");
    }


    public static void setDeviceUid(String deviceUid) {
        mStorage.apply("up_device_id", deviceUid);
    }


    public static String getDeviceUid() {
        return mStorage.getString("up_device_id", "");
    }

    public static void logoutClear() {
        ProfileStrorageUtil.logout();
        BaseApplication.get().getUserManager().clear();
    }


    public static String getAppType(){
        return mStorage.getString("app_type", "hcwx");
    }

    public static void setAppType(String type){
        mStorage.apply("app_type", type);
    }
}
