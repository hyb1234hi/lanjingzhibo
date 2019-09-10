package com.shengma.lanjing.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.tencent.rtmp.TXLog;

import org.json.JSONObject;

/**
 * Module:   TCUserMgr
 * <p>
 * Function: 用户信息管理
 * <p>
 * 1. 管理用户的信息属性：userId、密码、token、昵称、头像地址等等数据
 * <p>
 * 2. 登录之后会拉取重要的信息内容如：COS相关的信息、IM的UserSign等等
 * <p>
 * 3. 提供登录、注册、更新头像等
 * <p>
 * 4. 登录成功之后，会自动初始化 MLVB 组件 { TCUserMgr#loginMLVB()}
 */
public class TCUserMgr {
    public static final String TAG = TCUserMgr.class.getSimpleName();

    private Context mContext;              // context 上下文
    private String mUserId = "";           // 用户id
    private String mUserPwd = "";          // 用户密码
    private String mToken = "";            // token
    private long mSdkAppID = 0;            // sdkAppId
    private String mUserSig = "";          // 用于登录到 MLVB 的userSign
    private String mAccountType;
    private String mNickName = "";         // 昵称
    private String mUserAvatar = "";       // 头像连接地址
    private int mSex = -1;//0:male,1:female,-1:unknown  // 性别
    private String mCoverPic;             //  直播用的封面图
    private String mLocation;              // 地址信息
    private CosInfo mCosInfo = new CosInfo();   // COS 存储的 sdkappid

    private static class TCUserMgrHolder {
        private static TCUserMgr instance = new TCUserMgr();
    }

    public static TCUserMgr getInstance() {
        return TCUserMgrHolder.instance;
    }

    private TCUserMgr() {
    }

    //cos 配置
    public static class CosInfo {
        public String bucket = "";
        public String appID = "";
        public String secretID = "";
        public String region = "";
    }


    public CosInfo getCosInfo() {
        return mCosInfo;
    }

    public void initContext(Context context) {
        mContext = context.getApplicationContext();
        loadUserInfo();
    }

    public boolean hasUser() {
        return !TextUtils.isEmpty(mUserId) && !TextUtils.isEmpty(mUserPwd);
    }

    public String getUserSign() {
        return mUserSig;
    }
    public String getUserToken() {
        return mToken;
    }

    public String getUserId() {
        return mUserId;
    }

    public String getNickname() {
        return mNickName;
    }

    public String getUserAvatar() {
        return mUserAvatar;
    }

    public void setNickName(String nickName, TCHTTPMgr.Callback callback) {
        mNickName = nickName;

    }

    public String getAvatar() {
        return mUserAvatar;
    }

    public void setAvatar(String pic, TCHTTPMgr.Callback callback) {
        mUserAvatar = pic;

    }

    public String getCoverPic() {
        return mCoverPic;
    }

    public void setCoverPic(String pic, TCHTTPMgr.Callback callback) {
        mCoverPic = pic;

    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location, TCHTTPMgr.Callback callback) {
        mLocation = location;
    }

    public int getUserSex() {
        return mSex;
    }

    public void setUserSex(int sex, TCHTTPMgr.Callback callback) {
        mSex = sex;

    }

    public long getSDKAppID() {
        return mSdkAppID;
    }

    public String getAccountType() {
        return mAccountType;
    }


    public void logout() {
        mUserId = "";
        mUserPwd = "";
        mCoverPic = "";
        mUserAvatar = "";
        mLocation = "";
        clearUserInfo();
    }


    private void loadUserInfo() {

        if (mContext == null) return;
        TXLog.d(TAG, "xzb_process: load local user info");
        SharedPreferences settings = mContext.getSharedPreferences("TCUserInfo", Context.MODE_PRIVATE);
        mUserId = settings.getString("userid", "");
        mUserPwd = settings.getString("userpwd", "");
    }

    private void saveUserInfo() {

        if (mContext == null) return;
        TXLog.d(TAG, "xzb_process: save local user info");
        SharedPreferences settings = mContext.getSharedPreferences("TCUserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("userid", mUserId);
        editor.putString("userpwd", mUserPwd);
        editor.commit();
    }

    private void clearUserInfo() {
        if (mContext == null) return;
        SharedPreferences settings = mContext.getSharedPreferences("TCUserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("userid", "");
        editor.putString("userpwd", "");
        editor.commit();
    }

    /**
     *     /////////////////////////////////////////////////////////////////////////////////
     *     //
     *     //                      网络请求相关
     *     //
     *     /////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * 发起网络请求注册账号
     *
     * @param userId
     * @param password
     * @param callback
     */
    public void register(final String userId, final String password, final TCHTTPMgr.Callback callback) {
        try {
            String pwd = TCUtils.md5(TCUtils.md5(password) + userId);
            JSONObject body = new JSONObject()
                    .put("userid", userId)
                    .put("password", pwd);
          //  TCHTTPMgr.getInstance().request(TCGlobalConfig.APP_SVR_URL + "/register", body, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
