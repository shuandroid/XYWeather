package com.chen.xyweather.ui;

import android.content.Intent;
import android.widget.ImageView;

import com.chen.xyweather.R;
import com.chen.xyweather.utils.StatusUtils;
import com.chen.xyweather.base.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by lzw on 14-9-21.
 */
public class ImageBrowserActivity extends BaseActivity {
  String url;
  ImageView imageView;


  @Override
  protected void setupContentView() {
    setContentView(R.layout.image_brower_layout);
    imageView = (ImageView) findViewById(R.id.imageView);
    Intent intent = getIntent();
    url = intent.getStringExtra("url");
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
