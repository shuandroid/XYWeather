package com.chen.xyweather.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.SaveCallback;
import com.chen.xyweather.utils.DebugLog;
import com.chen.xyweather.utils.UiUtil;
import com.chen.xyweather.utils.UtilManger;

/**
 * Created by chen on 17-5-4.
 * 用户
 */
public class UserHelper {
    private static UserModel mUser;

    public enum USER_STATUS {
        VALID, INVALID
    }

    private static String mPhone;
    private static Bitmap mAvatar;
    private static String mNickname;

    private static Context mContext;

    private static USER_STATUS mUserStatus = USER_STATUS.INVALID;

    public UserHelper() {

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
            DebugLog.e("user is null");
            mUserStatus = USER_STATUS.INVALID;
        } else {
            DebugLog.e("user is status");
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

    public void changeAvatar(final byte[] bytes, final Context context) {
        final AVFile avFile = new AVFile(mUser.getObjectId(), bytes);
        avFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (null == e) {
                    saveAvatar(avFile, bytes);
                } else {
                    UtilManger.handleError(context, e.getCode());
                }
            }
        });
    }


    private void saveAvatar(AVFile avFile, final byte[] bytes) {
        mUser.setAvatar(avFile);
        mUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (null == e) {
                    // TODO: 17-5-14  
                    DebugLog.e("error or " );
                    mAvatar = UiUtil.bytesToBitmap(bytes);
                }
//                if (null == e) {
//                    avatar = BasicUtil.bytesToBitmap(bytes);
//                    for (UserListener listener : listeners) listener.OnUserUpdate();
//                } else for (UserListener listener : listeners) {
//                    listener.HandleError(e.getCode());
//                }
            }
        });
    }

    public void update() {
        mUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {

                } else {
                    e.getCode();
                }
            }
        });
    }

}
