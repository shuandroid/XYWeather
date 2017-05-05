package com.chen.xyweather.model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.LogInCallback;
import com.chen.xyweather.ui.MainActivity;
import com.chen.xyweather.utils.UiUtil;

import java.security.PublicKey;

/**
 * Created by chen on 17-5-4.
 * 用户
 */
public class UserHelper {
    private UserModel mUser;
    public enum USER_STATUS {
        VALID, INVALID
    }

    private String mPhone;
    private Bitmap mAvatar;
    private String mNickname;

    private USER_STATUS mUserStatus = USER_STATUS.INVALID;

    public UserHelper () {

        refresh();
    }

    public USER_STATUS getmUserStatus() {
        return mUserStatus;
    }

    public Bitmap getmAvatar() {
        return mAvatar;
    }

    public String getmPhone() {
        return mPhone;
    }

    public UserModel getmUser() {
        return mUser;
    }

    public String getmNickname() {
        return mNickname;
    }

    public void refresh() {
        mUser = UserModel.getCurrentUser(UserModel.class);

        if (mUser == null) {
            mUserStatus = USER_STATUS.INVALID;
        } else {
            mUserStatus = USER_STATUS.VALID;
            mPhone = mUser.getMobilePhoneNumber();
            mNickname = mUser.getNickName();
            downloadAvatar();
        }
    }

    private void downloadAvatar() {
        if (null != mUser.getAvatar()) {
            mUser.getAvatar().getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, AVException e) {
                    if (null == e) {
                        if (bytes != null) {
                            mAvatar = UiUtil.bytesToBitmap(bytes);
                        }
                    }
                }
            });
        }
    }

}
