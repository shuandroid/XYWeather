package com.chen.xyweather.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.suke.widget.SwitchButton;

public class NoticeActivity extends BaseActivity {

    @Bind(R.id.precipitation)
    protected SwitchButton mPre;

    @Bind(R.id.disaster)
    protected SwitchButton mDisaster;

    @Bind(R.id.friend)
    protected SwitchButton mFriend;

    @Bind(R.id.interactive)
    protected SwitchButton mIntera;

    @Bind(R.id.toolbar_title)
    protected TextView mTitle;

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_notice);
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionbar() {
        mTitle.setText("通知设置");
    }

    @Override
    protected void setupViews() {

    }

    @Override
    protected void tintStatusBarApi21() {

    }

    @Override
    protected void tintStatusBarApi19() {

    }
}
