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
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.model.UserModel;
import com.chen.xyweather.utils.UtilManger;

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

        String phone = mRegisterPhone.getText().toString();

        if (isPhone(phone)) {
            requestCode(phone);
        }
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

    private boolean isPhone(String phone) {
        if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
            return true;
        }
        return false;
    }

    /**
     * 获取验证码
     */
    private void requestCode(String phone) {

        UserModel.requestMobilePhoneVerifyInBackground(phone, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {

                }
            }
        });
    }

    private void getVerification() {
//        UserModel.verifyMobilePhoneInBackground();
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
            UtilManger.showCreditToast(R.layout.toast_ensure, this);
            focusView = mSecondPassword;
            cancel = true;
        } else if (!TextUtils.isEmpty(firstPassword) && !isPasswordValid(firstPassword)){
            UtilManger.showCreditToast(R.layout.toast_password, this);
            focusView = mFirstPassword;
            cancel = true;
        } else if(TextUtils.isEmpty(registerPhone) || registerPhone.length() != 11) {
            UtilManger.showCreditToast(R.layout.toast_phone, this);
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


    @Override
    protected void tintStatusBarApi21() {

    }

    @Override
    protected void tintStatusBarApi19() {

    }


}
