package com.chen.xyweather.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_forgot);
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
