package com.chen.xyweather.model;

import com.chen.xyweather.utils.UserCacheUtils;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;

/**
 * Created by chen on 17-5-11.
 * user
 */

public class CustomUserProvider implements LCChatProfileProvider {

    private static CustomUserProvider customUserProvider;

    public synchronized static CustomUserProvider getInstance() {
        if (null == customUserProvider) {
            customUserProvider = new CustomUserProvider();
        }
        return customUserProvider;
    }

    private CustomUserProvider() {
    }


    private static LCChatKitUser getThirdPartUser(UserModel userModel) {
        return new LCChatKitUser(userModel.getObjectId(), userModel.getUsername(),
                userModel.getAvatar().getUrl());
    }

    private static List<LCChatKitUser> getThirdPartUsers(List<UserModel> userModels) {
        List<LCChatKitUser> thirdPartUsers = new ArrayList<>();
        for (UserModel userModel : userModels) {
            thirdPartUsers.add(getThirdPartUser(userModel));
        }

        return thirdPartUsers;
    }

    @Override
    public void fetchProfiles(List<String> list, final LCChatProfilesCallBack lcChatProfilesCallBack) {
        UserCacheUtils.fetchUsers(list, new UserCacheUtils.CacheUserCallback() {
            @Override
            public void done(List<UserModel> userList, Exception e) {
                lcChatProfilesCallBack.done(getThirdPartUsers(userList), e);
            }
        });
    }
}
