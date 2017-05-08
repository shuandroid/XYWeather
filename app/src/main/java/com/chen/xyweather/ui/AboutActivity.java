package com.chen.xyweather.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    protected TextView mTextView;

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        mTextView.setText("关于我们");
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
