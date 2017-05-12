package com.chen.xyweather.ui.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.utils.DebugLog;
import com.chen.xyweather.utils.UtilManger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeNameActivity extends BaseActivity {

    @Bind(R.id.new_name)
    protected EditText mNewName;

    @Bind(R.id.clear_name)
    protected ImageView mClearName;

    @Bind(R.id.change_name)
    protected Button mChangeName;

    @Bind(R.id.toolbar_title)
    protected TextView mTitle;

    @OnClick(R.id.clear_name)
    protected void clearText() {
        mNewName.setText("");
        mNewName.requestFocus();
    }

    @OnClick(R.id.change_name)
    protected void changeName() {
        if (mNewName.getText().toString().equals("")) {
            UtilManger.showCreditToast(R.layout.toast_null, this);
        } else {
            Intent intent = new Intent(this, InfoActivity.class);
            DebugLog.e("nick_name" + mNewName.getText().toString());
            intent.putExtra("nick_name", mNewName.getText().toString());
            setResult(10, intent);
            finish();
        }

    }

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_change_name);
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);

    }

    @Override
    protected void setupActionbar() {
        mTitle.setText("修改昵称");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
