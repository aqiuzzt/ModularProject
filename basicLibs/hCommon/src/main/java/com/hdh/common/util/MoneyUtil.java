package com.hdh.common.util;


import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 
 * 
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:34
 */

public class MoneyUtil {

    public static String getMoneyYuan(int moneyCents) {
        if (moneyCents == 0) {
            return "0.00";
        } else {
            return CommonUtils.formatMoney(String.format("%.2f", new BigDecimal(moneyCents).divide(new BigDecimal("100")).doubleValue()), 2);
        }
    }

    public static String getMoneyYuanWithoutCent(int moneyCents) {
        if (moneyCents == 0) {
            return "0";
        } else {
            return new BigDecimal(moneyCents).divide(new BigDecimal("100")).intValue() + "";
        }
    }

    public static String getMoneyYuan(String moneyStrCents) {
        double moneyCents = Double.parseDouble(moneyStrCents);
        if (moneyCents == 0) {
            return "0.00";
        } else {
            return CommonUtils.formatMoney(String.format("%.2f", new BigDecimal(moneyStrCents).divide(new BigDecimal("100")).doubleValue()), 2);
        }
    }

    public static String getMoneyYuanDouble(String moneyStrCents) {
        double moneyCents = Double.parseDouble(moneyStrCents);
        if (moneyCents == 0) {
            return "0.00";
        } else {
            return CommonUtils.formatMoney(String.format("%.2f", new BigDecimal(moneyStrCents).divide(new BigDecimal("100")).doubleValue()), 2);
        }
    }

    public static String getMoneyYuan(long moneyCents) {
        if (moneyCents == 0) return "0.00";
        else {
            return CommonUtils.formatMoney(String.format("%.2f", new BigDecimal(moneyCents).divide(new BigDecimal("100")).doubleValue()), 2);
        }
    }

    public static String getMoneyYuan(double moneyCents) {
        if (moneyCents == 0) return "0.00";
        else {
            return CommonUtils.formatMoney(String.format("%.2f", new BigDecimal(moneyCents).divide(new BigDecimal("100")).doubleValue()), 2);
        }
    }

    public static float getMoneyYuanFloat(int moneyStrCents) {
        if (moneyStrCents == 0) {
            return 0;
        } else {
            return new BigDecimal(moneyStrCents).divide(new BigDecimal("100")).floatValue();
        }
    }

    public static int getMoneyCent(float moneyYuan) {
        return new BigDecimal(String.valueOf(moneyYuan)).multiply(new BigDecimal("100")).intValue();
    }

    public static float getMultiplyResult(String num1, String num2) {
        return new BigDecimal(num1).multiply(new BigDecimal(num2)).floatValue();
    }

    public static int getMoneyCent(String yuanFloatStr) {
        return new BigDecimal(yuanFloatStr).multiply(new BigDecimal("100")).intValue();
    }

    /**
     * 直接转字符串
     *
     * @param value
     * @return
     */
    public static String getTotalMoney(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("0");
        return decimalFormat.format(value);
    }

    /**
     * 直接转字符串,两位小数
     *
     * @param value
     * @return
     */
    public static String getTotalMoneyDoubble(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(value);
    }

    /**
     * @param number1
     * @param number2
     * @return number1-number2
     */
    public static float minusFloat(float number1, float number2) {
        return new BigDecimal(String.valueOf(number1)).subtract(new BigDecimal(String.valueOf(number2))).floatValue();
    }

    /**
     * @param number1
     * @param number2
     * @return number1-number2
     */
    public static String minusFloatString(float number1, float number2) {
        return new BigDecimal(String.valueOf(number1)).subtract(new BigDecimal(String.valueOf(number2))).toString();
    }


}
