package com.hdh.android.mail.base.bean;

import java.io.Serializable;

/**
 * 用户信息类 后期跟进项目中角色区分开来
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:35
 */
public class UserInfo implements Serializable {
    private String name;
    private int age;

    public UserInfo() {
    }

    public UserInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
