package com.lib.base.home;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface HomeService extends IProvider {

    void startActivity(Context context);
}
