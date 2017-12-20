package com.hdh.mall.me.main.bean;

/**
 * Created by albert on 2017/10/19.
 */

public class AnnouncementBean {
    /**
     * "id":"17101717503611689",
     "updatedAt":1508233836000,
     "author":"Albert",
     "parentId":"2140011530374131246",
     "title":"17号17：50测试公告",
     "category":"2140011530374131246",
     "readNum":0,
     "createdAt":1508233836000,
     "userId":"1705311116584345281",
     "state":1,
     "displayOrder":100,
     "postStatus":"publish",
     "type":3
     */

    private String id;
    private String updatedAt;
    private String author;
    private String parentId;
    private String title;
    private String category;
    private String readNum;
    private String createdAt;
    private String userId;
    private int state;
    private String displayOrder;
    private String postStatus;
    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReadNum() {
        return readNum;
    }

    public void setReadNum(String readNum) {
        this.readNum = readNum;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
