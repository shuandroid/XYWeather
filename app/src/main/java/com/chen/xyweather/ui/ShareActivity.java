package com.chen.xyweather.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShareActivity extends BaseActivity {

    @Bind(R.id.share_present_location)
    protected TextView mPresentLocation;

    @Bind(R.id.share_gridview)
    protected GridView mGridView;

    private List<Map<String, Object>> dataList;

    private int[] image = {R.mipmap.cond_icon_cloudy, R.mipmap.cond_icon_foggy, R.mipmap.cond_icon_lightrain, R.mipmap.cond_icon_sun};

    private String[] location = {"cloudy", "foggy", "lightrain", "sun"};

    private SimpleAdapter adapter;

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        adapter = new SimpleAdapter(this, getData(), R.layout.item_gridview, new String[]{"image", "location"},
                new int[]{R.id.item_image, R.id.img_location});
        mGridView.setAdapter(adapter);
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < image.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", image[i]);
            map.put("location", location[i]);
            dataList.add(map);
        }

        return dataList;
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
