/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.txd.hzj.wjlp;

import com.hyphenate.easeui.EaseConstant;
import com.txd.hzj.wjlp.catchDoll.socketcmd.SockAPP;

public class Constant extends EaseConstant {
    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final String GROUP_USERNAME = "item_groups";
    public static final String CHAT_ROOM = "item_chatroom";
    public static final String ACCOUNT_REMOVED = "account_removed";
    public static final String ACCOUNT_CONFLICT = "conflict";
    public static final String ACCOUNT_FORBIDDEN = "user_forbidden";
    public static final String ACCOUNT_KICKED_BY_CHANGE_PASSWORD = "kicked_by_change_password";
    public static final String ACCOUNT_KICKED_BY_OTHER_DEVICE = "kicked_by_another_device";
    public static final String CHAT_ROBOT = "item_robots";
    public static final String MESSAGE_ATTR_ROBOT_MSGTYPE = "msgtype";
    public static final String ACTION_GROUP_CHANAGED = "action_group_changed";
    public static final String ACTION_CONTACT_CHANAGED = "action_contact_changed";

    public static final String H5NAME = "android";

    // ↓↓↓↓↓↓↓↓↓↓↓ 抓娃娃常量字段
    public static SockAPP SOCK_APP;
    public static final int SOCKET_LINE_SUCCESS = 1;
    public static final int SOCKET_LINE_FAIL = 0;

    public static final String VIDEO_VOD_URL_KEY = "videoPlayerUrl"; // 点播Url
    public static final String VIDEO_LIVE_1_URL_KEY = "VideoPlayerUrl1"; // 直播1摄像头流
    public static final String VIDEO_LIVE_2_URL_KEY = "VideoPlayerUrl2"; // 直播2摄像头流

    // 网络类型常量
    public static final int NETWORK_CLASS_UNKNOW = 0; // 未知网络类型
    public static final int NETWORK_CLASS_WIFI = 1; // WIFI
    public static final int NETWORK_CLASS_2_G = 2; // 2G网络
    public static final int NETWORK_CLASS_3_G = 3; // 3G网络
    public static final int NETWORK_CLASS_4_G = 4; // 4G网络
}
