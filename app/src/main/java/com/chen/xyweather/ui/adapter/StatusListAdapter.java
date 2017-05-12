package com.chen.xyweather.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.*;
import com.avos.avoscloud.SaveCallback;
import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseListAdapter;
import com.chen.xyweather.base.ViewHolder;
import com.chen.xyweather.bean.Status;
import com.chen.xyweather.ui.ImageBrowserActivity;
import com.chen.xyweather.ui.MainActivity;
import com.chen.xyweather.ui.StatusListActivity;
import com.chen.xyweather.utils.App;
import com.chen.xyweather.utils.StatusService;
import com.chen.xyweather.utils.StatusUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhoutao on 17/5/11.
 *
 */
public class StatusListAdapter extends BaseListAdapter<Status> {

    public StatusListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public View getView(int position, View conView, ViewGroup parent) {
        if (conView == null) {
            conView = inflater.inflate(com.chen.xyweather.R.layout.status_item, null, false);
        }
        TextView nameView = ViewHolder.findViewById(conView, R.id.nameView);
        TextView textView = ViewHolder.findViewById(conView, R.id.statusText);
        ImageView avatarView = ViewHolder.findViewById(conView, R.id.avatarView);
        ImageView imageView = ViewHolder.findViewById(conView, R.id.statusImage);
        ImageView likeView = ViewHolder.findViewById(conView, R.id.likeView);
        TextView likeCountView = ViewHolder.findViewById(conView, R.id.likeCount);
        View likeLayout = ViewHolder.findViewById(conView, R.id.likeLayout);

        final Status status = datas.get(position);
        final AVStatus innerStatus = status.getInnerStatus();
        AVUser source = innerStatus.getSource();
        StatusUtils.displayAvatar(source, avatarView);
        nameView.setText(source.getUsername());


        if (TextUtils.isEmpty(innerStatus.getMessage())) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(innerStatus.getMessage());
            textView.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(innerStatus.getImageUrl()) == false) {
            imageView.setVisibility(View.VISIBLE);

            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(ctx));

            ImageLoader.getInstance().displayImage(innerStatus.getImageUrl(),
                    imageView, StatusUtils.normalImageOptions);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx, ImageBrowserActivity.class);
                    intent.putExtra("url", innerStatus.getImageUrl());
                    ctx.startActivity(intent);
                }
            });
        } else {
            imageView.setVisibility(View.GONE);
        }
        final AVObject detail = status.getDetail();

        final List<String> likes;
        if (detail.get(App.LIKES) != null) {
            likes = (List<String>) detail.get(App.LIKES);
        } else {
            likes = new ArrayList<String>();
        }

        int n = likes.size();
        if (n > 0) {
            likeCountView.setText(n + "");
        } else {
            likeCountView.setText("");
        }

        final AVUser user = AVUser.getCurrentUser();
        final String userId = user.getObjectId();
        final boolean contains = likes.contains(userId);
        if (contains) {
            likeView.setImageResource(R.mipmap.status_ic_player_liked);
        } else {
            likeView.setImageResource(R.mipmap.ic_player_like);
        }
        likeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveCallback saveCallback = new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (StatusUtils.filterException(ctx, e)) {
                            notifyDataSetChanged();
                        }
                    }
                };
                if (contains) {
                    likes.remove(userId);
                    StatusService.saveStatusLikes(detail, likes, saveCallback);
                } else {
                    likes.add(userId);
                    StatusService.saveStatusLikes(detail, likes, saveCallback);
                }
            }
        });

        return conView;
    }



}
