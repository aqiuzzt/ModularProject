package com.hdh.android.mail.base.bean;

import java.util.List;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/4 11:17
 */

public class DictionaryBean {

    /**
     * children	default value	N	子目录数组
     └type	default value	N	类型
     └name	default value	N	名称
     └parentId	default value	N	父id
     └description	default value	N	描述
     └level	default value	N	层次
     └displayOrder	default value	N	顺序
     └imgUrl	default value	N	图片url链接
     └activeState	default value	N	活跃状态。1.启用 2.禁用
     └operLogList	default value	N	操作日志列表
     └isOnline	default value	N	是否上线。1.线上 2.线下
     */

    /**
     * {
     "code": "200",
     "data": [
     {
     "isOnline": 1,
     "id": "123123123",
     "parentId": "2140011052228819000",
     "level": 0,
     "others": "",
     "description": "商城首页默认分类",
     "name": "2140011052228819088",
     "state": 1,
     "displayOrder": 10,
     "type": "goods_home_page",
     "activeState": 1
     }
     ],
     "debug": "queryDictionaryListByTypeAndLevel",
     "msg": "",
     "success": true
     }
     */

    public int isOnline;
    public String id;
    public String parentId;
    public int level;
    public String others;
    public String description;
    public String name;
    public int state;
    public int displayOrder;
    public String type;
    public int activeState;
    public List<?> children;
}
