package com.ft.home.view.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ft.home.R;
import com.ft.home.constant.Constant;
import com.ft.home.model.CHANNEL;
import com.ft.home.view.home.adpater.HomePagerAdapter;
import com.lib.audio.core.AudioController;
import com.lib.audio.model.AudioBean;
import com.lib.base.login.LoginImpl;
import com.lib.base.login.model.LoginEvent;
import com.lib.image.loader.app.ImageLoaderManager;
import com.lib.ui.base.BaseActivity;
import com.lib.ui.pager_indictor.ScaleTransitionPagerTitleView;
import com.lib.update.UpdateHelper;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private static final CHANNEL[] CHANNELS =
            new CHANNEL[]{CHANNEL.MY, CHANNEL.DISCORY, CHANNEL.FRIEND};

    private DrawerLayout mDrawerLayout;
    private View mToggleView;
    private View mSearchView;
    private ViewPager mViewPager;
    private HomePagerAdapter mAdapter;
    private View mDrawerQrcodeView;
    private View mDrawerShareView;
    private View unLogginLayout;
    private ImageView mPhotoView;

    private ArrayList<AudioBean> mLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        EventBus.getDefault().register(this);
        initView();
        initData();
    }

    private void initView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggleView = findViewById(R.id.toggle_view);
        mToggleView.setOnClickListener(this);
        mSearchView = findViewById(R.id.search_view);
        mSearchView.setOnClickListener(this);
        //初始化adpater
        mAdapter = new HomePagerAdapter(getSupportFragmentManager(), CHANNELS);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(mAdapter);
        initMagicIndicator();

        unLogginLayout = findViewById(R.id.unloggin_layout);
        unLogginLayout.setOnClickListener(this);
        mPhotoView = findViewById(R.id.avatr_view);
        findViewById(R.id.exit_layout).setOnClickListener(this);
        findViewById(R.id.check_update_view).setOnClickListener(this);
        mDrawerShareView = findViewById(R.id.home_music);
        mDrawerShareView.setOnClickListener(this);
        findViewById(R.id.online_music_view).setOnClickListener(this);
    }

    private void initData() {
        mLists.add(new AudioBean("100001", "http://sp-sycdn.kuwo.cn/resource/n2/85/58/433900159.mp3",
                "以你的名字喊我", "周杰伦", "七里香", "电影《不能说的秘密》主题曲,尤其以最美的不是下雨天,是与你一起躲过雨的屋檐最为经典",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559698076304&di=e6e99aa943b72ef57b97f0be3e0d2446&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fblog%2F201401%2F04%2F20140104170315_XdG38.jpeg",
                "4:30"));
        mLists.add(
                new AudioBean("100002", "http://sq-sycdn.kuwo.cn/resource/n1/98/51/3777061809.mp3", "勇气",
                        "梁静茹", "勇气", "电影《不能说的秘密》主题曲,尤其以最美的不是下雨天,是与你一起躲过雨的屋檐最为经典",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559698193627&di=711751f16fefddbf4cbf71da7d8e6d66&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D213168965%2C1040740194%26fm%3D214%26gp%3D0.jpg",
                        "4:40"));
        mLists.add(
                new AudioBean("100003", "http://sp-sycdn.kuwo.cn/resource/n2/52/80/2933081485.mp3", "灿烂如你",
                        "汪峰", "春天里", "电影《不能说的秘密》主题曲,尤其以最美的不是下雨天,是与你一起躲过雨的屋檐最为经典",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559698239736&di=3433a1d95c589e31a36dd7b4c176d13a&imgtype=0&src=http%3A%2F%2Fpic.zdface.com%2Fupload%2F201051814737725.jpg",
                        "3:20"));
        mLists.add(
                new AudioBean("100004", "http://sr-sycdn.kuwo.cn/resource/n2/33/25/2629654819.mp3", "小情歌",
                        "五月天", "小幸运", "电影《不能说的秘密》主题曲,尤其以最美的不是下雨天,是与你一起躲过雨的屋檐最为经典",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559698289780&di=5146d48002250bf38acfb4c9b4bb6e4e&imgtype=0&src=http%3A%2F%2Fpic.baike.soso.com%2Fp%2F20131220%2Fbki-20131220170401-1254350944.jpg",
                        "2:45"));

        // AudioHelper.startMusicService(mLists);
        AudioController.getInstance().setQueue(mLists);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.exit_layout) {
            finish();
            System.exit(0);
            return;
        }
        if (id == R.id.unloggin_layout) {
            /*     if (!UserManager.getInstance().hasLogined()) {
                    LoginActivity.start(this);
                } else {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }*/
            if (!LoginImpl.getInstance().hasLogin()) {
                LoginImpl.getInstance().login(this);
            } else {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
            return;
        }
        if (id == R.id.toggle_view) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
            return;
        }
        if (id == R.id.check_update_view) {
            checkUpdate();
            return;
        }
        if (id == R.id.home_music) {
            goToMusic();
            return;
        }
        if (id == R.id.online_music_view) {
            //跳到指定webactivity
            gotoWebView("https://www.imooc.com");
            return;
        }
    }

    // 初始化指示器
    private void initMagicIndicator() {
        MagicIndicator magicIndicator = findViewById(R.id.magic_indicator);
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return CHANNELS == null ? 0 : CHANNELS.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(CHANNELS[index].getKey());
                simplePagerTitleView.setTextSize(19);
                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                simplePagerTitleView.setNormalColor(Color.parseColor("#999999"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#333333"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                return 1.0f;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    /**
     * 处理登陆事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        unLogginLayout.setVisibility(View.GONE);
        mPhotoView.setVisibility(View.VISIBLE);
  /*      ImageLoaderManager.getInstance()
                .displayImageForCircle(mPhotoView, UserManager.getInstance().getUser().data.photoUrl);*/
        ImageLoaderManager.getInstance()
                .displayImageForCircle(mPhotoView, LoginImpl.getInstance().getUserInfo().data.photoUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //启动检查更新
    private void checkUpdate() {
        UpdateHelper.checkUpdate(this);
    }

    private void goToMusic() {
        ARouter.getInstance().build(Constant.Router.ROUTER_MUSIC_ACTIVIYT).navigation();
    }

    private void gotoWebView(String url) {
        ARouter.getInstance()
                .build(Constant.Router.ROUTER_WEB_ACTIVIYT)
                .withString("url", url)
                .navigation();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }
}
