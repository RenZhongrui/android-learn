package com.lib.base.login;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.lib.base.login.model.User;

/**
 * 登录组件对外暴露的所有接口功能
 */
public interface LoginService extends IProvider {

    User getUserInfo();

    void removeUser();

    boolean hasLogin();

    void login(Context context);

}
