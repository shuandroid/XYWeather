package com.chen.xyweather.ui.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.model.UserHelper;
import com.chen.xyweather.model.UserInstance;
import com.chen.xyweather.model.UserModel;
import com.chen.xyweather.utils.DebugLog;
import com.chen.xyweather.utils.UiUtil;
import com.chen.xyweather.utils.UtilManger;
import com.chen.xyweather.view.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.chatkit.LCChatKit;

public class InfoActivity extends BaseActivity {

    private static final int REQUEST_PSW = 1;
    private static final int REQUEST_NICK = 2;
    private static final int REQUEST_IMAGE = 3;
    private static final int REQUEST_OK = 10;

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


    @OnClick(R.id.info_user_img)
    protected void changeAvatar() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @OnClick(R.id.change_nick_name)
    protected void changeName() {
        Intent intent = new Intent(this, ChangeNameActivity.class);
        startActivityForResult(intent, REQUEST_NICK);
    }

    @OnClick(R.id.password)
    protected void changePsw() {

        Intent intent = new Intent(this, ChangePswActivity.class);
        startActivityForResult(intent, REQUEST_PSW);
    }

    @OnClick(R.id.logout_ensure)
    protected void logOut() {
        LCChatKit.getInstance().close(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
            }
        });
        UserModel.logOut();
        mUserHelper.refresh();
        finish();
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
        initUser();

    }

    private void initUser() {
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
        String temp;
        if (resultCode == REQUEST_OK) {
            switch (requestCode) {
                case REQUEST_NICK:
                    temp = data.getStringExtra("nick_name");
                    mUserHelper.getmUser().setNickName(temp);
                    mUserHelper.getmUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                mInfoUsername.setText(mUserHelper.getmUser().getNickName());
                            } else {
                                UtilManger.handleError(InfoActivity.this, e.getCode());
                            }
                        }
                    });
                    mInfoUsername.setText(temp);
                    break;
                case REQUEST_IMAGE:
                    mUserHelper.changeAvatar(UiUtil.decodeUriAsBitmap(data.getData(), this), this);
                    if (null != mUserHelper.getmAvatar()) {
                        mUserImage.setImageBitmap(mUserHelper.getmAvatar());
                    }
                    break;
                case REQUEST_PSW:
                    String oldPassword;
                    temp = data.getStringExtra("password");
                    oldPassword = data.getStringExtra("oldPassword");
                    DebugLog.e("now_password" + temp);
                    mUserHelper.getmUser().updatePasswordInBackground(oldPassword, temp, new UpdatePasswordCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                UtilManger.showCreditToast(R.layout.toast_success, InfoActivity.this);
                            } else {
                                DebugLog.e("password error " + e + e.getCode());
                                UtilManger.handleError(InfoActivity.this, e.getCode());
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void tintStatusBarApi21() {

    }

    @Override
    protected void tintStatusBarApi19() {

    }
}
