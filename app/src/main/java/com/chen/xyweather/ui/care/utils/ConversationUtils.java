package com.chen.xyweather.ui.care.utils;

import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.utils.LCIMConversationUtils;

/**
 * Created by chen on 17-5-13.
 * 对话工具类
 */

public class ConversationUtils extends LCIMConversationUtils {

    public static void createSingleConversation(String memberId, AVIMConversationCreatedCallback callback) {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put(ConversationType.TYPE_KEY, ConversationType.Single.getValue());
        LCChatKit.getInstance().getClient().createConversation(Arrays.asList(memberId), "", attrs, false, true, callback);
    }
}
