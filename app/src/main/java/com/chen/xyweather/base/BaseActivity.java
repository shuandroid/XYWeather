package com.chen.xyweather.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chen.xyweather.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupContentView();

        findViews();

        setupActionbar();

        setupViews();

    }

    protected abstract void setupContentView();

    protected abstract void findViews();

    protected abstract void setupActionbar();

    protected abstract void setupViews();

    @Override
    protected void onPause() {
        super.onPause();
        // TODO: 17-3-6
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);// avoid mem leak
        //TODO clear views listener ,
    }
}
