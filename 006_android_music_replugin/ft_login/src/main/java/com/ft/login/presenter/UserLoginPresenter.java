package com.ft.login.presenter;

import android.content.Context;
import android.content.Intent;

import com.ft.login.api.MockData;
import com.ft.login.api.RequestCenter;
import com.ft.login.inter.IUserLoginPresenter;
import com.ft.login.inter.IUserLoginView;
import com.ft.login.manager.UserManager;
import com.google.gson.Gson;
import com.lib.base.login.LoginPluginConfig;
import com.lib.base.login.model.LoginEvent;
import com.lib.base.login.model.User;
import com.lib.network.listener.DisposeDataListener;

import org.greenrobot.eventbus.EventBus;

/**
 * 登陆页面对应Presenter
 */
public class UserLoginPresenter implements IUserLoginPresenter, DisposeDataListener {

    private IUserLoginView mIView;
    private Context mContext;

    public UserLoginPresenter(IUserLoginView iView,Context context) {
        mIView = iView;
        mContext = context;
    }

    @Override
    public void login(String username, String password) {
        mIView.showLoadingView();
        RequestCenter.login( this);
    }

    @Override
    public void onSuccess(Object responseObj) {
        mIView.hideLoadingView();
        User user = (User) responseObj;
        UserManager.getInstance().setUser(user);
        //发送登陆Event
        // EventBus.getDefault().post(new LoginEvent());
        // todo 插件化要发送广播
        sendLoginBroadcast(user);
        mIView.finishActivity();
    }

    private void sendLoginBroadcast(User user) {
        Intent intent = new Intent();
        intent.setAction(LoginPluginConfig.ACTION.LOGIN_SUCCESS_ACTION);
        intent.putExtra(LoginPluginConfig.ACTION.KEY_DATA, new Gson().toJson(user));
        mContext.sendBroadcast(intent);
    }

    @Override
    public void onFailure(Object reasonObj) {
        mIView.hideLoadingView();
        onSuccess(new Gson().fromJson(MockData.LOGIN_DATA, User.class));
        mIView.showLoginFailedView();
    }
}
