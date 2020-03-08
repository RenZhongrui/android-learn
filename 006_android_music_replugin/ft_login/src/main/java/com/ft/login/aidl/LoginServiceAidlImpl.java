package com.ft.login.aidl;

import android.os.RemoteException;

import com.ft.login.manager.UserManager;
import com.lib.base.login.ILoginService;
import com.lib.base.login.model.User;


public class LoginServiceAidlImpl extends ILoginService.Stub {

    @Override
    public boolean hasLgoin() {
        return UserManager.getInstance().hasLogined();
    }

    @Override
    public void removeUser() {
        UserManager.getInstance().removeUser();
    }

    @Override
    public User getUserInfo() {
        return UserManager.getInstance().getUser();
    }
}
