package com.chen.xyweather.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.ui.recycler.RefreshableRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShareWeatherActivity extends BaseActivity {


    @Bind(R.id.swipe_refresh)
    protected SwipeRefreshLayout mSwipeRefresh;

    @Bind(R.id.status_list)
    protected RefreshableRecyclerView mRecyclerView;

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_share_weather);
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
        initRecycler();
    }

    private void initRecycler() {

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


    }

    @Override
    protected void tintStatusBarApi21() {

    }

    @Override
    protected void tintStatusBarApi19() {

    }
}
