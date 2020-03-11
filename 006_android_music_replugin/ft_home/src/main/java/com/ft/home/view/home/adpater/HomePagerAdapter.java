package com.ft.home.view.home.adpater;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ft.home.model.CHANNEL;
import com.ft.home.view.discory.DiscoryFragment;
import com.ft.home.view.friend.FriendFragment;
import com.qihoo360.replugin.RePlugin;


public class HomePagerAdapter extends FragmentPagerAdapter {

    private CHANNEL[] mList;

    public HomePagerAdapter(FragmentManager fm, CHANNEL[] datas) {
        super(fm);
        mList = datas;
    }

    //这种方式，避免一次性创建所有的framgent
    @Override
    public Fragment getItem(int position) {
        int type = mList[position].getValue();
        switch (type) {
            case CHANNEL.MINE_ID:
                return getCorrectFragment("ft_mine", "com.ft.mine.MineFragment");
            case CHANNEL.DISCORY_ID:
                return DiscoryFragment.newInstance();
            case CHANNEL.FRIEND_ID:
                return FriendFragment.newInstance();
        /*    case CHANNEL.VIDEO_ID:
                return VideoFragment.newInstance();*/
        }
        return null;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.length;
    }

    /**
     * 获取插件中的fragment
     */
    private Fragment getCorrectFragment(String pluginName, String className) {
        Fragment fragment = null;
        //拿到插件Context
        Context pluginContext = RePlugin.fetchContext(pluginName);
        if (pluginContext != null) {
            //获取插件的ClassLoader，注：会强转失败，宿主和插件的classloader实例不一样
            ClassLoader classLoader = RePlugin.fetchClassLoader(pluginName);
            try {
                fragment = classLoader.loadClass(className).asSubclass(Fragment.class).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fragment;
    }
}
