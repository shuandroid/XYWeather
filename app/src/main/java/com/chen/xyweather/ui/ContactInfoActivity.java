package com.chen.xyweather.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactInfoActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    protected TextView mTitle;

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_contact_info);
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionbar() {
        mTitle.setText("详细资料");
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
