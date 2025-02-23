package com.netease.yunxin.app.videocall;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.netease.yunxin.kit.alog.ALog;
import com.netease.yunxin.nertc.nertcvideocall.bean.InvitedInfo;
import com.netease.yunxin.nertc.ui.CallKitNotificationConfig;

import org.json.JSONObject;

import kotlin.jvm.functions.Function1;

class SelfNotificationConfigFetcher implements Function1<InvitedInfo, CallKitNotificationConfig> {
    @Override
    public CallKitNotificationConfig invoke(InvitedInfo invitedInfo) {
        String name;
        NimUserInfo userInfo = NIMClient.getService(UserService.class).getUserInfo(invitedInfo.invitor);
        if (userInfo != null) {
            name = userInfo.getName();
        } else {
            String extraInfo = invitedInfo.attachment;
            try {
                JSONObject object = new JSONObject(extraInfo);
                name = object.optString("userName");
            } catch (Exception exception) {
                ALog.e("SelfNotificationConfigFetcher", "parse inviteInfo extra error.", exception);
                name = "";
            }
        }
        return new CallKitNotificationConfig(R.mipmap.ic_launcher, null, "您有新的来电", name + "邀请您进行【网络通话】");
    }
}
