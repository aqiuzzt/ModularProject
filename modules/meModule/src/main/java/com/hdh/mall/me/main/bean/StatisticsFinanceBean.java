package com.hdh.mall.me.main.bean;

/**
 * Created by albert on 2017/10/20.
 */

public class StatisticsFinanceBean {

    /**
     *
     * └recommendedAmount	default value	N	推荐业绩
     └roleId	default value	N	角色id
     └agentArea	default value	N	区域
     └index	default value	N	排名
     └repoAmount	default value	N	收货业绩
     └shopAmount	default value	N	支付业绩
     *
     */

    private String recommendedAmount;
    private String roleId;
    private String agentArea;
    private String index;
    private String repoAmount;
    private String shopAmount;

    public String getRecommendedAmount() {
        return recommendedAmount;
    }

    public void setRecommendedAmount(String recommendedAmount) {
        this.recommendedAmount = recommendedAmount;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAgentArea() {
        return agentArea;
    }

    public void setAgentArea(String agentArea) {
        this.agentArea = agentArea;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getRepoAmount() {
        return repoAmount;
    }

    public void setRepoAmount(String repoAmount) {
        this.repoAmount = repoAmount;
    }

    public String getShopAmount() {
        return shopAmount;
    }

    public void setShopAmount(String shopAmount) {
        this.shopAmount = shopAmount;
    }
}
