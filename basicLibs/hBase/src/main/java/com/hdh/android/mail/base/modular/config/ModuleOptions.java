package com.hdh.android.mail.base.modular.config;

import android.content.Context;

/**
 * 具体管理那些app包含那些模块Module的配置，
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:34
 */
public class ModuleOptions {

    private ModuleBuilder builder;

    private ModuleOptions(ModuleOptions.ModuleBuilder builder) {
        this.builder = builder;
    }

    public boolean hasModule(String key) {
        return builder.containModule(key);
    }

    public String getModule(String key) {
        return builder.getModuleEntrance(key);
    }

    public Context getContext() {
        return builder.context;
    }

    public static class ModuleBuilder {
        private Context context;
        private ImmutableMap mModules;

        public ModuleBuilder(Context context) {
            this.context = context;
            mModules = new ImmutableMap();
        }

        public ModuleOptions.ModuleBuilder addModule(String key, String value) {
            mModules.add(key, value);
            return this;
        }

        public boolean containModule(String key) {
            return mModules.containsKey(key);
        }

        public String getModuleEntrance(String key) {
            return mModules.get(key);
        }

        public ModuleOptions build() {
            return new ModuleOptions(this);
        }

    }
}
