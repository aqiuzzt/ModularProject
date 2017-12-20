package com.hdh.mall.me.utils;

import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StrikethroughSpan;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.bean.GoodsBean;
import com.hdh.common.util.MoneyUtil;
import com.hdh.mall.me.R;


/**
 * 商品 view工具类
 * Created by jsy on 17/3/19.
 */

public class ViewUtil {
    /**
     * 设置支付密码 六位
     *
     * @param editText
     */
    public static void setPayEditText(EditText editText) {
        editText.setSingleLine();
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        InputFilter[] filters = {new InputFilter.LengthFilter(6)};
        editText.setFilters(filters);
    }



    /**
     * 显示价钱  x
     *
     * @param textView
     * @param priceByCents
     */
    public static void showPrice(TextView textView, int priceByCents) {
        SpannableString spanString = new SpannableString("¥" + MoneyUtil.getMoneyYuan(priceByCents));
        spanString.setSpan(new AbsoluteSizeSpan(BaseApplication.get().getResources().getDimensionPixelSize(R.dimen.sp_12)),
                0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spanString);
    }

    public static void showPrice(TextView textView, long priceByCents) {
        SpannableString spanString = new SpannableString("¥" + MoneyUtil.getMoneyYuan(priceByCents));
        spanString.setSpan(new AbsoluteSizeSpan(BaseApplication.get().getResources().getDimensionPixelSize(R.dimen.sp_12)),
                0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spanString);
    }


    /**
     * 显示价钱 不显示符号
     *
     * @param textView
     * @param priceByCents
     */
    public static void showPriceWithouNoSymbol(TextView textView, int priceByCents) {
        SpannableString spanString = new SpannableString(MoneyUtil.getMoneyYuan(priceByCents));
        textView.setText(spanString);
    }

    public static void showPriceWithouNoSymbol(TextView textView, long priceByCents) {
        SpannableString spanString = new SpannableString(MoneyUtil.getMoneyYuan(priceByCents));
        textView.setText(spanString);
    }


    public static void showOriginNum(TextView textView, int num) {
        SpannableString spanString = new SpannableString(num + "");
        spanString.setSpan(new StrikethroughSpan(), 0, spanString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spanString);
    }

    public static void showOriginNum(TextView textView, long num) {
        SpannableString spanString = new SpannableString(num + "");
        spanString.setSpan(new StrikethroughSpan(), 0, spanString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spanString);
    }

    public static void showOriginNumNoLine(TextView textView, int num) {
        SpannableString spanString = new SpannableString(num + "");
        textView.setText(spanString);
    }

    public static void showOriginNumNoLine(TextView textView, long num) {
        SpannableString spanString = new SpannableString(num + "");
        textView.setText(spanString);
    }

    /**
     * 显示原始价钱
     *
     * @param textView
     * @param priceByCents
     */
    public static void showOriginPrice(TextView textView, int priceByCents) {
        SpannableString spanString = new SpannableString("¥" + MoneyUtil.getMoneyYuan(priceByCents));
        spanString.setSpan(new StrikethroughSpan(), 1, spanString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spanString);
    }

    public static void showOriginPrice(TextView textView, long priceByCents) {
        SpannableString spanString = new SpannableString("¥" + MoneyUtil.getMoneyYuan(priceByCents));
        spanString.setSpan(new StrikethroughSpan(), 1, spanString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spanString);
    }


    public static void showOriginPriceRedStar(TextView textView, long priceByCents) {
        SpannableString spanString = new SpannableString("" + priceByCents);
        spanString.setSpan(new StrikethroughSpan(), 0, spanString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spanString);
    }

    /**
     * 显示原始价钱 没有符号
     *
     * @param textView
     * @param priceByCents
     */
    public static void showOriginPriceNoSymbol(TextView textView, int priceByCents) {
        SpannableString spanString = new SpannableString(MoneyUtil.getMoneyYuan(priceByCents));
        spanString.setSpan(new StrikethroughSpan(), 1, spanString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spanString);
    }

    public static void showOriginPriceNoSymbol(TextView textView, long priceByCents) {
        SpannableString spanString = new SpannableString(MoneyUtil.getMoneyYuan(priceByCents));
        spanString.setSpan(new StrikethroughSpan(), 0, spanString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spanString);
    }

    /**
     * 设置商品类别
     * <p>
     * 1：支付宝
     * 2：微信
     * 3：银积分(原优待金)
     * 7：购物豆
     * 8：金积分(原红星)
     * 10: 红积分
     * 11: 黄积分
     * 12: 蓝积分
     * 13: 绿积分
     * 14: 橙积分
     * 15: 紫积分
     *
     * @param goodView
     * @param type
     */
    public static void setGoodPriceType(ImageView goodView, int type) {
        switch (type) {
            case GoodsBean.GOOD_TYPE_GLOD:
                goodView.setBackgroundResource(R.drawable.ic_good_glod);
                break;
            case GoodsBean.GOOD_TYPE_SLIVER:
                goodView.setBackgroundResource(R.drawable.ic_good_sliver);
                break;
            case GoodsBean.GOOD_TYPE_PEA:
                goodView.setBackgroundResource(R.drawable.ic_good_pea);
                break;
            case GoodsBean.GOOD_TYPE_RED:
                goodView.setBackgroundResource(R.drawable.ic_good_red);
                break;
            case GoodsBean.GOOD_TYPE_YELLOW:
                goodView.setBackgroundResource(R.drawable.ic_good_yellow);
                break;
            case GoodsBean.GOOD_TYPE_BLUE:
                goodView.setBackgroundResource(R.drawable.ic_good_blue);
                break;
            case GoodsBean.GOOD_TYPE_GREEN:
                goodView.setBackgroundResource(R.drawable.ic_good_green);
                break;
            case GoodsBean.GOOD_TYPE_ORANGE:
                goodView.setBackgroundResource(R.drawable.ic_good_orange);
                break;
            case GoodsBean.GOOD_TYPE_PURPLE:
                goodView.setBackgroundResource(R.drawable.ic_good_pruple);
                break;
            default:
                goodView.setBackgroundResource(R.drawable.ic_good_money);
                break;
        }
    }

    public static void setGoodPricePayType(ImageView goodView, int type) {
        switch (type) {
            case GoodsBean.GOOD_TYPE_GLOD:
                goodView.setBackgroundResource(R.drawable.ic_cart_glod);
                break;
            case GoodsBean.GOOD_TYPE_SLIVER:
                goodView.setBackgroundResource(R.drawable.ic_cart_silver);
                break;
            case GoodsBean.GOOD_TYPE_PEA:
                goodView.setBackgroundResource(R.drawable.ic_cart_pea);
                break;
            case GoodsBean.GOOD_TYPE_RED:
                goodView.setBackgroundResource(R.drawable.ic_cart_red);
                break;
            case GoodsBean.GOOD_TYPE_YELLOW:
                goodView.setBackgroundResource(R.drawable.ic_cart_yellow);
                break;
            case GoodsBean.GOOD_TYPE_BLUE:
                goodView.setBackgroundResource(R.drawable.ic_cart_blue);
                break;
            case GoodsBean.GOOD_TYPE_GREEN:
                goodView.setBackgroundResource(R.drawable.ic_cart_green);
                break;
            case GoodsBean.GOOD_TYPE_ORANGE:
                goodView.setBackgroundResource(R.drawable.ic_cart_orange);
                break;
            case GoodsBean.GOOD_TYPE_PURPLE:
                goodView.setBackgroundResource(R.drawable.ic_cart_pruple);
                break;
            default:
                goodView.setBackgroundResource(R.drawable.ic_cart_money);
                break;
        }
    }



}
