package com.hdh.android.mail.base.modular.router;


import com.hdh.android.mail.base.modular.config.ModuleOptions;


/**
 * Module管理
 * <p>
 * Map是使用对应module的Provider的path作为key和value
 * 以及相关的数据结构，用来对ARouter的进行二次封装和管理模块
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:18
 */
public class ModuleManager {

    private ModuleOptions options;

    private ModuleManager() {
    }

    private static class ModuleManagerHolder {
        private static final ModuleManager instance = new ModuleManager();
    }

    public static ModuleManager getInstance() {
        return ModuleManagerHolder.instance;

    }

    public void init(ModuleOptions options) {
        if (this.options == null && options != null) {
            this.options = options;
        }
    }

    public ModuleOptions getOptions() {
        return options;
    }

    public boolean hasModule(String key) {
        return options.hasModule(key);
    }

}
