package com.chen.xyweather.ui;


import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.utils.DataCleanManager;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingActivity extends BaseActivity {

    @Bind(R.id.s_cache)
    protected TextView mCache;

    @Bind(R.id.s_notice)
    protected RelativeLayout mNotice;

    @Bind(R.id.s_changebg)
    protected RelativeLayout mChangebg;

    @Bind(R.id.s_cleardata)
    protected RelativeLayout mClearData;

    @Bind(R.id.s_account)
    protected RelativeLayout mAccount;

    @Bind(R.id.s_update)
    protected RelativeLayout mUpdate;

    @OnClick(R.id.s_notice)
    protected void notice() {
    }


    @OnClick(R.id.s_changebg)
    protected void changebg() {

    }


    @OnClick(R.id.s_cleardata)
    protected void clearData() {
        DataCleanManager.clearAllCache(this);
        mCache.setText("0KB");
    }

    @OnClick(R.id.s_account)
    protected void account(){
        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.s_update)
    protected void update(){

    }

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        try {
            mCache.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
