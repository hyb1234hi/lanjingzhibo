package com.shengma.lanjing.liveroom.roomutil.commondef;

import org.json.JSONException;
import org.json.JSONObject;

public class AudienceInfo {
    public String userID;     //观众ID
    public String userInfo;   //观众信息
    public String userName;
    public String userAvatar;

    public void transferUserInfo() {
        JSONObject jsonRoomInfo = null;
        try {
            jsonRoomInfo = new JSONObject(userInfo);
            userName    = jsonRoomInfo.optString("userName");
            userAvatar  = jsonRoomInfo.optString("userAvatar");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "AudienceInfo{" +
                "userID='" + userID + '\'' +
                ", userInfo='" + userInfo + '\'' +
                ", userName='" + userName + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                '}';
    }
}
