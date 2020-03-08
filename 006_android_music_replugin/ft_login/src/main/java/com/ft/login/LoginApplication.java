package com.ft.login;

import android.app.Application;

import com.ft.login.aidl.LoginServiceAidlImpl;
import com.lib.base.login.LoginPluginConfig;
import com.qihoo360.replugin.RePlugin;

public class LoginApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //对外注册登陆功能接口供其它插件调用
        RePlugin.registerPluginBinder(LoginPluginConfig.KEY_INTERFACE, new LoginServiceAidlImpl());
    }
}
