package com.learn.music.view.home.adpater;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.learn.music.model.CHANNEL;
import com.learn.music.view.discory.DiscoryFragment;
import com.learn.music.view.friend.FriendFragment;
import com.learn.music.view.mine.MineFragment;

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
                return MineFragment.newInstance();
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
}
