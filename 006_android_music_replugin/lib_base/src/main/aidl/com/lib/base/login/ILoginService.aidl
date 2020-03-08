package com.lib.base.login;

import com.lib.base.login.model.User;

interface ILoginService {

    //是否登陆
    boolean hasLgoin();

    void removeUser();

    User getUserInfo();
}
