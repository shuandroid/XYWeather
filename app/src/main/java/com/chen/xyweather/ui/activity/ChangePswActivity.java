package com.chen.xyweather.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.utils.UtilManger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePswActivity extends BaseActivity {

    @Bind(R.id.change_old_psw)
    protected EditText mOldPsw;

    @Bind(R.id.change_first_psw)
    protected EditText mFirstPassword;

    @Bind(R.id.change_second_psw)
    protected EditText mSecondPassword;

    @Bind(R.id.change_confirm)
    protected Button mChangeConfirm;

    @Bind(R.id.toolbar_title)
    protected TextView mTitle;

    @OnClick(R.id.change_confirm)
    protected void changeConfirm() {
        attempChange();
    }

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_change_psw);
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionbar() {

        mTitle.setText("修改密码");
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

    private void attempChange() {
        String password = mOldPsw.getText().toString();
        String passwordFirst = mFirstPassword.getText().toString();
        String passwordSecond = mSecondPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!passwordFirst.equals(passwordSecond)) {
            cancel = true;
            UtilManger.showCreditToast(R.layout.toast_ensure, this);
            focusView = mSecondPassword;
            focusView.requestFocus();
        }

        if (cancel) {

        } else {
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra("password", passwordFirst);
            intent.putExtra("oldPassword", password);
            setResult(10, intent);
            finish();
        }



    }
    private void isPasswordVaild (String password) {

    }

}
