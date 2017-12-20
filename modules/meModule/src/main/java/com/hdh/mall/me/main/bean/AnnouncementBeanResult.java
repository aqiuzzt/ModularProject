package com.hdh.mall.me.main.bean;

import java.util.List;

/**
 * Created by albert on 2017/10/19.
 */

public class AnnouncementBeanResult {
    /**
     *  "pageCount":1,
     "start":0,
     "startOfPage":0,
     "resultCount":2,
     "pageSize":20,
     "data":[
     */
    private List<AnnouncementBean> data;

    public List<AnnouncementBean> getData() {
        return data;
    }

    public void setData(List<AnnouncementBean> data) {
        this.data = data;
    }
}
