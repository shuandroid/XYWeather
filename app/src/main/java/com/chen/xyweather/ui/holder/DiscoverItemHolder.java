package com.chen.xyweather.ui.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chen.xyweather.R;
import com.chen.xyweather.model.UserModel;
import com.chen.xyweather.ui.activity.ContactInfoActivity;
import com.chen.xyweather.utils.DebugLog;
import com.chen.xyweather.view.CircleImageView;


import cn.leancloud.chatkit.viewholder.LCIMCommonViewHolder;

/**
 * Created by chen on 17-5-11.
 * 好友界面内容
 */

public class DiscoverItemHolder extends LCIMCommonViewHolder<UserModel> {

    private TextView nameView;
    private TextView loginTimeView;
    private CircleImageView avatarView;
    private UserModel user;


    public DiscoverItemHolder(Context context, ViewGroup root) {
        super(context, root, R.layout.card_near_people_item);
        initView();
    }

    private void initView() {
        nameView = (TextView) itemView.findViewById(R.id.name_text);
        loginTimeView = (TextView) itemView.findViewById(R.id.login_hello);
        avatarView = (CircleImageView) itemView.findViewById(R.id.avatar_view);
        avatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != user) {
                    Intent intent = new Intent(getContext(), ContactInfoActivity.class);
                    intent.putExtra("user_id", user.getObjectId());
                    getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public void bindData(UserModel userModel) {
        user = userModel;
        if (userModel != null) {
            nameView.setText(user.getNickName() == null ? user.getUsername() : user.getNickName());
        } else {
            nameView.setText("");
            loginTimeView.setText("");
            avatarView.setImageResource(0);
        }
    }

    public static ViewHolderCreator HOLDER_CREATOR = new ViewHolderCreator<DiscoverItemHolder>() {
        @Override
        public DiscoverItemHolder createByViewGroupAndType(ViewGroup parent, int viewType) {
            return new DiscoverItemHolder(parent.getContext(), parent);
        }
    };

}
