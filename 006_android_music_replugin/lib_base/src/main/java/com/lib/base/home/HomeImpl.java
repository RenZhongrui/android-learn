package com.lib.base.home;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * create: Ren Zhongrui
 * date: 2020-03-06
 * description:
 */
public class HomeImpl {

    @Autowired(name = "/home/home_service")
    protected HomeService homeService;

    private static HomeImpl mHomeImpl = null;

    public static HomeImpl getInstance() {
        if (mHomeImpl == null) {
            synchronized (HomeImpl.class) {
                if (mHomeImpl == null) {
                    mHomeImpl = new HomeImpl();
                }
            }
        }
        return mHomeImpl;
    }

    private HomeImpl() {
        ARouter.getInstance().inject(this);
    }

    public void startActivity(Context context) {
        homeService.startActivity(context);
    }

}
