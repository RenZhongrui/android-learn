package com.ft.home.impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ft.home.view.home.HomeActivity;
import com.lib.base.home.HomeService;

@Route(path = "/home/home_service")
public class HomeServiceImpl implements HomeService {

    @Override
    public void startActivity(Context context) {
        HomeActivity.startActivity(context);
    }

    @Override
    public void init(Context context) {

    }
}
