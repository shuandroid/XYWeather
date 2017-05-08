package com.chen.xyweather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.model.UserHelper;
import com.chen.xyweather.model.UserInstance;
import com.chen.xyweather.model.UserModel;
import com.chen.xyweather.view.CircleImageView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoActivity extends BaseActivity {

    private static int REQUEST_PSW = 1;
    private static int REQUEST_NICK = 2;
    private static int REQUEST_IMAGE = 3;



    private UserHelper mUserHelper;

    @Bind(R.id.toolbar_title)
    protected TextView mToolbarTitle;

    @Bind(R.id.tv_info_num)
    protected TextView mPhone;

    @Bind(R.id.tv_info_username)
    protected TextView mInfoUsername;

    @Bind(R.id.info_user_img)
    protected CircleImageView mUserImage;

    @Bind(R.id.tv_info_psw)
    protected TextView mInfoPsw;

    @Bind(R.id.logout_ensure)
    protected Button mBtnLogout;


    @OnClick(R.id.tv_info_username)
    protected void changeName() {


    }

    @OnClick(R.id.password)
    protected void changePsw() {

        Intent intent = new Intent(this, ChangePswActivity.class);
        startActivityForResult(intent, REQUEST_PSW);
    }

    @OnClick(R.id.logout_ensure)
    protected void logOut() {

    }

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_info);
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionbar() {

        mToolbarTitle.setText(R.string.info);
    }

    @Override
    protected void setupViews() {

        mUserHelper = UserInstance.getInstance();
        mInfoUsername.setText(mUserHelper.getmUser().getNickName() == null ?
                mUserHelper.getmPhone() : mUserHelper.getmUser().getNickName());
        mPhone.setText(mUserHelper.getmPhone());
        if (mUserHelper.getmAvatar() != null) {
            mUserImage.setImageBitmap(mUserHelper.getmAvatar());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: 17-5-5 针对不同的修改，做处理
    }

    @Override
    protected void tintStatusBarApi21() {

    }

    @Override
    protected void tintStatusBarApi19() {

    }
}
