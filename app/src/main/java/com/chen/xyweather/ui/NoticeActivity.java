package com.chen.xyweather.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NoticeActivity extends BaseActivity {

    @Bind(R.id.precipitation)
    protected com.suke.widget.SwitchButton mPre;

    @Bind(R.id.disaster)
    protected com.suke.widget.SwitchButton mDisaster;

    @Bind(R.id.friend)
    protected com.suke.widget.SwitchButton mFriend;

    @Bind(R.id.interactive)
    protected com.suke.widget.SwitchButton mIntera;


    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);

    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void setupActionbar() {

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
