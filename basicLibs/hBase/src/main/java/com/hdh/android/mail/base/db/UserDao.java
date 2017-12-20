package com.hdh.android.mail.base.db;

import com.hdh.android.mail.base.bean.User;

/**
 * 用户信息
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/30 10:26
 */
public class UserDao extends BaseDao<User> {
    
    @Override
    public Class<User> buildClass() {
        return User.class;
    }
}
