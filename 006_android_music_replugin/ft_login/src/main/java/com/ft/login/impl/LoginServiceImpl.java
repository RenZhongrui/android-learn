package com.ft.login.impl;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ft.login.LoginActivity;
import com.ft.login.manager.UserManager;
import com.lib.base.login.LoginService;
import com.lib.base.login.model.User;

/**
 * 组件化登录service的接口实现
 */
@Route(path = "/login/login_service")
public class LoginServiceImpl implements LoginService {

    @Override
    public User getUserInfo() {
        return UserManager.getInstance().getUser();
    }

    @Override
    public void removeUser() {
        UserManager.getInstance().removeUser();
    }

    @Override
    public boolean hasLogin() {
        return UserManager.getInstance().hasLogined();
    }

    @Override
    public void login(Context context) {
        LoginActivity.start(context);
    }

    @Override
    public void init(Context context) {
        Log.e("init: ", "LoginServiceAidlImpl init");
    }
}
