package com.chen.xyweather.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ForgotPswActivity extends BaseActivity {

    @Bind(R.id.recovery_phone)
    protected EditText mRecoveryPhone;

    @Bind(R.id.recovery_first_password)
    protected EditText mFirstPassword;

    @Bind(R.id.recovery_second_password)
    protected EditText mSecondPassword;

    @Bind(R.id.recovery_ensure_password)
    protected EditText mEnsurePassword;

    @Bind(R.id.recovery_get_ensure)
    protected Button mGetEnsure;

    @Bind(R.id.toolbar_title)
    protected TextView mTitle;

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_forgot);
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionbar() {
        mTitle.setText("重设密码");
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
