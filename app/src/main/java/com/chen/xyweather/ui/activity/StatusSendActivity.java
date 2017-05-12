package com.chen.xyweather.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.utils.StatusService;
import com.chen.xyweather.utils.StatusUtils;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

/**
 *  Created by zhoutao  2017/5/11
 */
public class StatusSendActivity extends BaseActivity {
  private static final int IMAGE_PICK_REQUEST = 0;
  Context context;

  @Bind(R.id.editText)
  EditText editText;

  @Bind(R.id.image)
  ImageView imageView;

  @Bind(R.id.imageAction)
  Button imageAction;
  boolean haveImage = false;
  Bitmap bitmap;


  @Override
  protected void setupContentView() {
    context = this;
    setContentView(R.layout.status_send_layout);
    ButterKnife.bind(this);
    setButtonAndImage();
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

  void setButtonAndImage() {
    imageView.setImageBitmap(bitmap);
    if (haveImage) {
      imageAction.setText("取消图片");
      imageView.setVisibility(View.VISIBLE);
    } else {
      imageAction.setText("添加图片");
      imageView.setVisibility(View.INVISIBLE);
    }
  }

  @OnClick(R.id.send)
  protected void send() {
    String text = editText.getText().toString();
    if (TextUtils.isEmpty(text) == false || bitmap != null) {
      final ProgressDialog dialog = StatusUtils.showSpinnerDialog(this);
      StatusService.sendStatus(text, bitmap, new SaveCallback() {
        @Override
        public void done(AVException e) {
          dialog.dismiss();
          if (StatusUtils.filterException(context, e)) {
            setResult(RESULT_OK);
            finish();
          }
        }
      });
    }
  }

  public static void pickImage(Activity activity, int requestCode) {
    Intent intent = new Intent(Intent.ACTION_PICK, null);
    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
    activity.startActivityForResult(intent, requestCode);
  }

  @OnClick(R.id.imageAction)
  void imageAction() {
    if (haveImage == false) {
      pickImage(this, IMAGE_PICK_REQUEST);
    } else {
      bitmap = null;
      haveImage = false;
      setButtonAndImage();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
      if (requestCode == IMAGE_PICK_REQUEST) {
        Uri uri = data.getData();
        try {
          bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
          haveImage = true;
          setButtonAndImage();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}