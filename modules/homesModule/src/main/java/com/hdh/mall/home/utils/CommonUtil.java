package com.hdh.mall.home.utils;


/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/5 10:32
 */

public class CommonUtil {
    public static String mappingWithdraw(int status) {
        String str = null;
        switch (status) {
            case 1:
                str = "未处理";
                break;
            case 2:
                str = "处理中";
                break;
            case 3:
                str = "回购成功";
                break;
            case 4:
                str = "回购失败";
                break;
            default:
                str = "UNKNOWN";
        }
        return str;
    }


    /**
     * 商家视角订单状态
     * <p>
     * 1.待提交凭证
     * 2.待审核
     * 3.已取消
     * 8.已审核
     * 9.审核不通过
     *
     * @param status
     * @return
     */
    public static String mappingMerchantOrderStatus(int status) {
        String str = null;
        switch (status) {
            case 1:
                str = "待提交凭证";
                break;
            case 2:
                str = "待审核";
                break;
            case 3:
                str = "已取消";
                break;
            case 8:
                str = "已审核";
                break;
            case 9:
                str = "审核不通过";
                break;
            case 10:
                str = "已驳回";
                break;

        }
        return str;
    }


    /**
     * 红星专区活动详情商家类别mapp
     *
     * @param type
     * @return
     */
    public static String mappingRedActivityMerchantType(int type) {
        String str = null;
        switch (type) {
            case 2:
                str = "一级商家红星类";
                break;
            case 3:
                str = "二级商家红星类";
                break;
            case 4:
                str = "三级商家红星类";
                break;

        }
        return str;
    }



}
