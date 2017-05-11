package com.chen.xyweather.ui.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.xyweather.R;
import com.chen.xyweather.model.UserModel;
import com.chen.xyweather.ui.event.ConversationMemberClickEvent;
import com.squareup.picasso.Picasso;

import cn.leancloud.chatkit.adapter.LCIMCommonListAdapter;
import cn.leancloud.chatkit.viewholder.LCIMCommonViewHolder;
import de.greenrobot.event.EventBus;

/**
 * Created by chen on 17-5-11.
 * holder
 */

public class ConversationDetailItemHolder  extends LCIMCommonViewHolder<UserModel> {

    ImageView avatarView;
    TextView nameView;
    UserModel user;

    public ConversationDetailItemHolder(Context context, ViewGroup root) {
        super(context, root, R.layout.conversation_member_item);
        avatarView = (ImageView)itemView.findViewById(R.id.avatar);
        nameView = (TextView)itemView.findViewById(R.id.username);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != user) {
                    EventBus.getDefault().post(new ConversationMemberClickEvent(user.getObjectId(), false));
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != user) {
                    EventBus.getDefault().post(new ConversationMemberClickEvent(user.getObjectId(), true));
                }
                return true;
            }
        });
    }


    @Override
    public void bindData(UserModel userModel) {
        user = userModel;
        if (null != userModel) {
            Picasso.with(getContext()).load(user.getAvatar().getUrl()).into(avatarView);
            nameView.setText(userModel.getUsername());
        } else {
            avatarView.setImageResource(0);
            nameView.setText("");
        }
    }

    public static ViewHolderCreator HOLDER_CREATOR = new ViewHolderCreator<ConversationDetailItemHolder>() {
        @Override
        public ConversationDetailItemHolder createByViewGroupAndType(ViewGroup parent, int viewType) {
            return new ConversationDetailItemHolder(parent.getContext(), parent);
        }
    };

}
