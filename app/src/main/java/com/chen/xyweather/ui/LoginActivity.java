package com.chen.xyweather.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.utils.MapUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.login_ensure)
    protected Button btn_login;

    @OnClick(R.id.login_ensure)
    protected void test(){
        Toast.makeText(this, MapUtil.getLocation(this),Toast.LENGTH_SHORT).show();
    }

    @Bind(R.id.login_username)
    protected EditText username;

    @Bind(R.id.login_password)
    protected EditText password;

    @Bind(R.id.forget_password)
    protected TextView tv_forget_password;

    @OnClick(R.id.forget_password)
    protected void setForget_password(){

    }

    @Bind(R.id.sign_up)
    protected TextView tv_sign_up;

    @OnClick(R.id.sign_up)
    protected void setSign_up(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void setupContentView() {

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
