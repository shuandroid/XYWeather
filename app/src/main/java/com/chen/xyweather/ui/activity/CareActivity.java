package com.chen.xyweather.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.model.UserHelper;
import com.chen.xyweather.model.UserModel;
import com.chen.xyweather.ui.adapter.CareViewPagerAdapter;
import com.chen.xyweather.ui.fragment.ChatFragment;
import com.chen.xyweather.ui.fragment.DiscoverFragment;
import com.chen.xyweather.utils.UserCacheUtils;
import com.chen.xyweather.view.pager.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.leancloud.chatkit.activity.LCIMConversationFragment;

public class CareActivity extends BaseActivity {

    protected List<Fragment> fragments;
    protected String [] mTabTitle;

    @Bind(R.id.toolbar_title)
    protected TextView mTitle;

    @Bind(R.id.tab_layout)
    protected TabLayout mTab;

    @Bind(R.id.view_pager)
    protected ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_care);
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionbar() {
        mTitle.setText("特别关心");
    }

    @Override
    protected void setupViews() {
        initFragment();
        initViewPager();
        UserCacheUtils.cacheUser(UserModel.getCurrentUser());
    }



    private void initFragment() {
        mTabTitle = getResources().getStringArray(R.array.tab_title);
        fragments = new ArrayList<>();
        fragments.add(new LCIMConversationFragment());
        fragments.add(new DiscoverFragment());

    }

    private void initViewPager() {
        CareViewPagerAdapter careViewPagerAdapter = new CareViewPagerAdapter(getSupportFragmentManager(),
                mTabTitle, fragments);
        mViewPager.setAdapter(careViewPagerAdapter);
        mTab.setupWithViewPager(mViewPager);
        mTab.setTabsFromPagerAdapter(careViewPagerAdapter);

    }


    @Override
    protected void tintStatusBarApi21() {

    }

    @Override
    protected void tintStatusBarApi19() {

    }
}
