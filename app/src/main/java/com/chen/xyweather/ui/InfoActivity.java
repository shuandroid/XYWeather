package com.chen.xyweather.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.chen.xyweather.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoActivity extends AppCompatActivity {

    @Bind(R.id.tv_info_username)
    protected TextView tv_info_username;
    @OnClick(R.id.tv_info_username)
    protected void changeName(){

    }

    @Bind(R.id.tv_info_psw)
    protected TextView tv_info_psw;

    @OnClick(R.id.tv_info_psw)
    protected void changePsw(){

    }

    @Bind(R.id.logout_ensure)
    protected Button btn_logout;

    @OnClick(R.id.logout_ensure)
    protected void logOut(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
    }
}
