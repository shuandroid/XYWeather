package com.chen.xyweather.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChangeNameActivity extends BaseActivity {

    @Bind(R.id.new_name)
    protected EditText mNewName;

    @Bind(R.id.clear_name)
    protected ImageView mClearName;

    @Bind(R.id.change_name)
    protected Button mChangeName;



    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_change_name);
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
