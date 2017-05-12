package com.chen.xyweather.ui;

import android.content.Intent;
import android.widget.ImageView;

import com.chen.xyweather.R;
import com.chen.xyweather.utils.StatusUtils;
import com.chen.xyweather.base.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhoutao on 17-5-11.
 *
 */
public class ImageBrowserActivity extends BaseActivity {
    String url;

    @Bind(R.id.imageView)
    protected ImageView imageView;

    @Override
    protected void setupContentView() {
        setContentView(R.layout.image_brower_layout);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(ImageBrowserActivity.this));

        ImageLoader.getInstance().displayImage(url, imageView, StatusUtils.normalImageOptions);
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
