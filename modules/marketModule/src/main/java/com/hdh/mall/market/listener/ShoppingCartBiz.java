package com.hdh.mall.market.listener;

import android.widget.ImageView;

import com.hdh.android.mail.base.bean.ShoppingCartBean;
import com.hdh.common.util.LogUtil;
import com.hdh.mall.market.R;
import com.hdh.mall.market.bean.ShoppingCartClassifyBean;
import com.hdh.mall.market.utils.DecimalUtil;

import java.util.ArrayList;
import java.util.List;


public class ShoppingCartBiz {
    private static final String LOG_TAG = "ShoppingCartBiz";

    /**
     * 选择全部，点下全部按钮，改变所有商品选中状态
     */
    public static boolean selectAll(List<ShoppingCartClassifyBean> list, boolean isSelectAll, ImageView ivCheck) {
        isSelectAll = !isSelectAll;
        ShoppingCartBiz.checkEditItem(isSelectAll, ivCheck);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setGroupSelected(isSelectAll);
            for (int j = 0; j < list.get(i).getItems().size(); j++) {
                list.get(i).getItems().get(j).isChildSelected = isSelectAll;
            }
        }
        return isSelectAll;
    }

    /**
     * 族内的所有组，是否都被选中，即全选
     *
     * @param list
     * @return
     */
    private static boolean isSelectAllGroup(List<ShoppingCartClassifyBean> list) {
        for (int i = 0; i < list.size(); i++) {
            boolean isSelectGroup = list.get(i).isGroupSelected();
            if (!isSelectGroup) {
                return false;
            }
        }
        return true;
    }

    /**
     * 组内所有子选项是否全部被选中
     *
     * @param list
     * @return
     */
    private static boolean isSelectAllChild(List<ShoppingCartBean> list) {
        for (int i = 0; i < list.size(); i++) {
            boolean isSelectGroup = list.get(i).isChildSelected;
            if (!isSelectGroup) {
                return false;
            }
        }
        return true;
    }

    /**
     * 单选一个，需要判断整个组的标志，整个族的标志，是否被全选，取消，则
     * 除了选择全部和选择单个可以单独设置背景色，其他都是通过改变值，然后notify；
     *
     * @param list
     * @param groudPosition
     * @param childPosition
     * @return 是否选择全部
     */
    public static boolean selectOne(List<ShoppingCartClassifyBean> list, int groudPosition, int childPosition) {
        boolean isSelectAll;
        boolean isSelectedOne = !(list.get(groudPosition).getItems().get(childPosition).isChildSelected);
        list.get(groudPosition).getItems().get(childPosition).isChildSelected = isSelectedOne;//单个图标的处理
        boolean isSelectCurrentGroup = isSelectAllChild(list.get(groudPosition).getItems());
        list.get(groudPosition).setGroupSelected(isSelectCurrentGroup);//组图标的处理
        isSelectAll = isSelectAllGroup(list);
        return isSelectAll;
    }

    /**
     * 选择组分类
     * 且对改组下面的子商品置为选择状态
     *
     * @param list
     * @param groudPosition
     * @return
     */
    public static boolean selectGroup(List<ShoppingCartClassifyBean> list, int groudPosition) {
        boolean isSelectAll;
        boolean isSelected = !(list.get(groudPosition).isGroupSelected());
        list.get(groudPosition).setGroupSelected(isSelected);
        for (int i = 0; i < list.get(groudPosition).getItems().size(); i++) {
            list.get(groudPosition).getItems().get(i).isChildSelected = isSelected;
        }
        isSelectAll = isSelectAllGroup(list);
        return isSelectAll;
    }

    /**
     * 获取已选择的购物商品列表
     * @param list
     * @return
     */
    public static ArrayList<ShoppingCartBean> getSelectList(List<ShoppingCartClassifyBean> list) {
        ArrayList<ShoppingCartBean> shoppingCartBeanList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isGroupSelected()) {
                shoppingCartBeanList.addAll(list.get(i).getItems());
            } else {
                for (int j = 0; j < list.get(i).getItems().size(); j++) {
                    if (list.get(i).getItems().get(j).isChildSelected) {
                        shoppingCartBeanList.add(list.get(i).getItems().get(j));
                    }
                }
            }
            LogUtil.i(LOG_TAG, "getSelectList size: " + shoppingCartBeanList.size());
        }
        return shoppingCartBeanList;
    }

    /**
     * 勾与不勾选中选项
     *
     * @param isSelect 原先状态
     * @param ivCheck
     * @return 是否勾上，之后状态
     */
    public static boolean checkItem(boolean isSelect, ImageView ivCheck) {
        if (isSelect) {
            ivCheck.setImageResource(R.drawable.market_ic_checkbox_checked);
        } else {
            ivCheck.setImageResource(R.drawable.market_ic_checkbox_unchecked_blue);
        }
        return isSelect;
    }

    /**
     * 编辑状态全选
     * @param isSelect
     * @param ivCheck
     * @return
     */
    public static boolean checkEditItem(boolean isSelect, ImageView ivCheck) {
        if (isSelect) {
            ivCheck.setImageResource(R.drawable.market_ic_checkbox_checked);
        } else {
            ivCheck.setImageResource(R.drawable.market_ic_edit_cart_delete);
        }
        return isSelect;
    }

    /**=====================上面是界面改动部分，下面是数据变化部分=========================*/

    /**
     * 获取结算信息，肯定需要获取总价和数量，但是数据结构改变了，这里处理也要变；
     *
     * @return 0=选中的商品数量；1=选中的商品总价 2=选择的类型
     */
    public static String[] getShoppingCount(List<ShoppingCartClassifyBean> listGoods) {
        String[] infos = new String[3];
        String selectedCount = "0";
        String selectedMoney = "0";
        String goodType = "0";
        for (int i = 0; i < listGoods.size(); i++) {
            for (int j = 0; j < listGoods.get(i).getItems().size(); j++) {
                boolean isSelectd = listGoods.get(i).getItems().get(j).isChildSelected;
                if (isSelectd) {
                    long price = listGoods.get(i).getItems().get(j).discountPrice;
                    long num = listGoods.get(i).getItems().get(j).count;
                    String countMoney = DecimalUtil.multiply(String.valueOf(price), String.valueOf(num));
                    selectedMoney = DecimalUtil.add(selectedMoney, countMoney);
                    selectedCount = DecimalUtil.add(selectedCount, "1");
                    goodType = listGoods.get(i).getItems().get(j).supportPayType + "";
                }
            }
        }
        LogUtil.i(LOG_TAG, "selectedCount:" + selectedCount + " selectedMoney:" + selectedMoney);
        infos[0] = selectedCount;
        infos[1] = selectedMoney;
        infos[2] = goodType;
        return infos;
    }


    /**
     * 是否有选择商品
     *
     * @param listGoods
     * @return
     */
    public static boolean hasSelectedGoods(List<ShoppingCartClassifyBean> listGoods) {
        String count = getShoppingCount(listGoods)[0];
        if ("0".equals(count)) {
            return false;
        }
        return true;
    }

    /**
     * 添加某商品的数量到数据库（非通用部分，都有这个动作，但是到底存什么，未可知）
     *
     * @param productID 此商品的规格ID
     * @param num       此商品的数量
     */
    public static void addGoodToCart(String productID, String num) {
    }

    /**
     * 删除某个商品,即删除其ProductID
     *
     * @param productID 规格ID
     */
    public static void delGood(String productID) {
    }

    /**
     * 删除全部商品
     */
    public static void delAllGoods() {
    }


    /**
     * 更新购物车的单个商品数量
     *
     * @param productID
     * @param num
     */
    public static void updateGoodsNumber(String productID, String num) {
    }




}
