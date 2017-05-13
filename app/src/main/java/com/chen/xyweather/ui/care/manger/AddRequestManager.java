package com.chen.xyweather.ui.care.manger;

/**
 * Created by chen on 17-5-13.
 * 添加好友
 */

public class AddRequestManager {

    private static AddRequestManager addRequestManager;

    /**
     * 用户端未读的邀请消息的数量
     */
    private int unreadAddRequestsCount = 0;

    public static synchronized AddRequestManager getInstance() {
        if (addRequestManager == null) {
            addRequestManager = new AddRequestManager();
        }
        return addRequestManager;
    }

    public AddRequestManager() {
    }

    /**
     * 是否有未读的消息
     */
    public boolean hasUnreadRequests() {
        return unreadAddRequestsCount > 0;
    }

    /**
     * 推送过来时自增
     */
    public void unreadRequestsIncrement() {
        ++ unreadAddRequestsCount;
    }


}
