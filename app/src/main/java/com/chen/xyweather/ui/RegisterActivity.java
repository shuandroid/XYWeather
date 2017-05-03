package com.chen.xyweather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.model.UserModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.register_phone)
    protected EditText mRegisterPhone;

    @Bind(R.id.register_first_password)
    protected EditText mFirstPassword;

    @Bind(R.id.register_second_password)
    protected EditText mSecondPassword;

    @Bind(R.id.register_ensure_password)
    protected EditText mEnsurePassword;

    @Bind(R.id.get_ensure)
    protected Button mGetEnsure;

    /**
     * 获取验证码
     */
    @OnClick(R.id.get_ensure)
    protected void getEnsure() {

    }


    @OnClick(R.id.register)
    protected void register() {

        attemptRegister();
    }

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_sign_up);

    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);

    }

    @Override
    protected void setupActionbar() {

        mToolbar.setTitle("注册");
        setSupportActionBar(mToolbar);

    }

    @Override
    protected void setupViews() {


    }

    /**
     * 注册入口
     */
    private void attemptRegister() {

        String registerPhone = mRegisterPhone.getText().toString();
        String firstPassword = mFirstPassword.getText().toString();
        String secondPassword = mSecondPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!isFirstEqualSecond(firstPassword, secondPassword)) {
            showCreditToast(R.layout.toast_ensure);
            focusView = mSecondPassword;
            cancel = true;
        } else if (!TextUtils.isEmpty(firstPassword) && !isPasswordValid(firstPassword)){
            showCreditToast(R.layout.toast_password);
            focusView = mFirstPassword;
            cancel = true;
        } else if(TextUtils.isEmpty(registerPhone) || registerPhone.length() != 11) {
            showCreditToast(R.layout.toast_phone);
            focusView = mRegisterPhone;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            UserModel user = new UserModel();
            user.setUsername(registerPhone);
            user.setMobilePhoneNumber(registerPhone);

            user.setPassword(firstPassword);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    } else {
                        //失败的逻辑

                    }

                }
            });
        }




    }


    private boolean isFirstEqualSecond(String fisrtPassword, String secondPassword) {
        return fisrtPassword.equals(secondPassword);
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    private void showCreditToast(int layout) {
        View view = LayoutInflater.from(this).inflate(layout, null);
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    @Override
    protected void tintStatusBarApi21() {

    }

    @Override
    protected void tintStatusBarApi19() {

    }


}
