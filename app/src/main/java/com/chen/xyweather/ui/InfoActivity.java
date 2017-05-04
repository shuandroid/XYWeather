package com.chen.xyweather.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoActivity extends BaseActivity {

    @Bind(R.id.tv_info_username)
    protected TextView mInfoUsername;
    @OnClick(R.id.tv_info_username)
    protected void changeName(){

    }

    @Bind(R.id.tv_info_psw)
    protected TextView mInfoPsw;

    @OnClick(R.id.tv_info_psw)
    protected void changePsw(){

    }

    @Bind(R.id.logout_ensure)
    protected Button mBtnLogout;

    @OnClick(R.id.logout_ensure)
    protected void logOut(){

    }


    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_info);
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
