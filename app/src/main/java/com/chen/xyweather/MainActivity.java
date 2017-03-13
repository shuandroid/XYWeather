package com.chen.xyweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chen.xyweather.base.BaseActivity;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void findViews() {

        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionbar() {

    }

    @Override
    protected void setupViews() {

    }
}
