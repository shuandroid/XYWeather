package com.chen.xyweather.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.model.UserModel;
import com.chen.xyweather.ui.care.manger.FriendsManger;
import com.chen.xyweather.utils.DebugLog;
import com.chen.xyweather.utils.UserCacheUtils;
import com.chen.xyweather.view.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.chatkit.utils.LCIMConstants;

public class ContactInfoActivity extends BaseActivity {

    private String mUserId = "";
    protected UserModel mUser;

    @Bind(R.id.username)
    protected TextView mUsername;

    @Bind(R.id.number)
    protected TextView mNumber;

    @Bind(R.id.toolbar_title)
    protected TextView mTitle;

    @Bind(R.id.add_friend)
    protected Button mAdd;

    @Bind(R.id.chat_friend)
    protected Button mChat;

    @Bind(R.id.user_head)
    protected CircleImageView mUserHead;

    @OnClick(R.id.add_friend)
    protected void addFriend() {
        Intent intent = new Intent(this, ChatRoomActivity.class);
        intent.putExtra(LCIMConstants.PEER_ID, mUserId);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.chat_friend)
    protected void chatFriend() {
        Intent intent = new Intent(this, ChatRoomActivity.class);
        intent.putExtra(LCIMConstants.PEER_ID, mUserId);
        startActivity(intent);
        finish();
    }

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_contact_info);
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionbar() {
        mTitle.setText("详细资料");
    }

    @Override
    protected void setupViews() {

        initData();
        initView();
    }

    private void initData() {
        mUserId = getIntent().getStringExtra("user_id");
        DebugLog.e("mUserId" + mUserId);
        mUser = UserCacheUtils.getCachedUser(mUserId);
        DebugLog.e("mUser" + mUser);
    }

    private void initView() {
        List<String> cacheFriends = FriendsManger.getFriendIds();
        boolean isFriend = cacheFriends.contains(mUserId);
//        if (isFriend) {
//            mChat.setVisibility(View.VISIBLE);
//
//        } else {
//            mChat.setVisibility(View.GONE);
//            mAdd.setVisibility(View.VISIBLE);
//        }

//        Picasso.with(this).load(mUser.getAvatarUrl()).error(R.mipmap.head).into(mUserHead);
        mUsername.setText(mUser.getNickName() == null ? mUser.getUsername() : mUser.getNickName());
        mNumber.setText(mUser.getMobilePhoneNumber());
    }

    @Override
    protected void tintStatusBarApi21() {

    }

    @Override
    protected void tintStatusBarApi19() {

    }
}
