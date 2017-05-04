package com.chen.xyweather.model.listen;

/**
 * Created by chen on 17-5-4.
 * listener
 */
public interface UserListener {

    void OnUserLogin();

    void OnUserLogout();

    void OnUserSignUp();

    void OnUserVerify();

    void OnUserUpdate();

    void OnUserDownloadAvatar();

    void OnUserRequestVerifyCode();

    void OnUserRequestResetCode();

    void OnUserResetPassword();

    void HandleError(int code);
}
