package com.chen.xyweather.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity {


    @Bind(R.id.sign_up_username)
    protected EditText et_sign_up_username;

    @Bind(R.id.sign_up_first_password)
    protected EditText et_sign_up_f_password;

    @Bind(R.id.sign_up_second_password)
    protected EditText et_sign_up_s_password;

    @Bind(R.id.sign_up_pin)
    protected EditText et_sign_up_pin;

    @Bind(R.id.btn_getpin_sign_up)
    protected Button btn_getpin;

    @OnClick(R.id.btn_getpin_sign_up)
    protected void getPin(){

    }

    @Bind(R.id.sign_up_ensure)
    protected Button btn_signup;

    @OnClick(R.id.sign_up_ensure)
    protected void signUp(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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
