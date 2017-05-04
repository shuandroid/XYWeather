package com.chen.xyweather.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChangePswActivity extends BaseActivity {

    @Bind(R.id.change_old_psw)
    protected EditText mOldPsw;

    @Bind(R.id.change_first_psw)
    protected EditText mFirstPassword;

    @Bind(R.id.change_second_psw)
    protected EditText mSecondPassword;

    @Bind(R.id.change_confirm)
    protected Button mChangeConfirm;


    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_change_psw);
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
