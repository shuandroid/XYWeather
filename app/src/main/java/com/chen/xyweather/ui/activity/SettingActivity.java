package com.chen.xyweather.ui.activity;


import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.model.UserModel;
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

    @Bind(R.id.toolbar_title)
    protected TextView mTitle;


    @OnClick(R.id.s_notice)
    protected void notice() {
        startActivity(new Intent(SettingActivity.this, NoticeActivity.class));
    }


    @OnClick(R.id.s_changebg)
    protected void changebg() {
        Toast.makeText(this, "暂无背景可替换", Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.s_cleardata)
    protected void clearData() {
        DataCleanManager.clearAllCache(this);
        mCache.setText("0KB");
    }

    @OnClick(R.id.s_account)
    protected void account() {
        if (UserModel.getCurrentUser() != null) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_setting);

    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionbar() {
        mTitle.setText("设置");
    }

    @Override
    protected void setupViews() {
        getCache();
    }

    private void getCache() {
        try {
            mCache.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void tintStatusBarApi21() {

    }

    @Override
    protected void tintStatusBarApi19() {

    }


}
