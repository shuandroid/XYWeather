package com.chen.xyweather.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.model.UserHelper;
import com.chen.xyweather.model.UserModel;
import com.chen.xyweather.utils.DebugLog;
import com.chen.xyweather.utils.UtilManger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.chatkit.LCChatKit;

public class LoginActivity extends BaseActivity {


    private UserHelper mUserHelper;

    @Bind(R.id.login_ensure)
    protected Button mLogin;

    @OnClick(R.id.login_ensure)
    protected void login() {
        attemptLogin();
    }

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.login_username)
    protected EditText mUsername;

    @Bind(R.id.login_password)
    protected EditText mPassword;

    @Bind(R.id.forget_password)
    protected TextView mForgetPassword;

    /**
     * 忘记密码
     */
    @OnClick(R.id.forget_password)
    protected void forgetPassword() {


    }

    @Bind(R.id.sign_up)
    protected TextView mSignUp;

    /**
     * 注册
     */
    @OnClick(R.id.sign_up)
    protected void signUp() {
        DebugLog.e("sign up");
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }


    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void findViews() {
        mUserHelper = new UserHelper();
        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionbar() {

    }

    @Override
    protected void setupViews() {

        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return false;
            }
        });

    }

    /**
     * 登录的入口
     */
    private void attemptLogin() {
        mUsername.setError(null);
        mPassword.setError(null);
        final String username = mUsername.getText().toString();
        final String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();

        } else {
            //尝试登录
            UserModel.logInInBackground(username, password, new LogInCallback<UserModel>() {
                @Override
                public void done(UserModel userModel, AVException e) {
                    //登录成功
                    if (e == null) {
                        if (userModel != null) {
                            mUserHelper.refresh();
                            imLogin();
                        }
                    } else {
                        UtilManger.handleError(LoginActivity.this, e.getCode());
                    }
                }
            }, UserModel.class);
        }
    }

    public void imLogin() {
        LCChatKit.getInstance().open(UserModel.getCurrentUserId(), new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {

                    Toast.makeText(LoginActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    DebugLog.e("lcchat client" + LCChatKit.getInstance().getClient());
                    LoginActivity.this.finish();
                } else {
                    DebugLog.e("lccchatkit error " + e);
                    UtilManger.handleError(LoginActivity.this, e.getCode());
                }
            }
        });
    }


    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    @Override
    protected void tintStatusBarApi21() {


    }

    @Override
    protected void tintStatusBarApi19() {

    }
}
