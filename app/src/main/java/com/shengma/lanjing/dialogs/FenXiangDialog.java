package com.shengma.lanjing.dialogs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.Util;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class FenXiangDialog extends DialogFragment implements View.OnClickListener {

    private Window window;
    private LinearLayout l1,l2,l3,l4;
    private ImageView guanbi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View  view = inflater.inflate(R.layout.fenxiang_dialog, null);
        guanbi=view.findViewById(R.id.guanbi);
        guanbi.setOnClickListener(this);
        l1=view.findViewById(R.id.l1);
        l2=view.findViewById(R.id.l2);
        l3=view.findViewById(R.id.l3);
        l4=view.findViewById(R.id.l4);
        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 下面这些设置必须在此方法(onStart())中才有效
        window = getDialog().getWindow();
        if (window!=null){
            // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
            window.setBackgroundDrawableResource(android.R.color.transparent);
            // 设置动画
            window.setWindowAnimations(R.style.bottomDialog);
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM;
            // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
            params.width = getResources().getDisplayMetrics().widthPixels;
            window.setAttributes(params);
        }

    }

    private int mTargetScene = SendMessageToWX.Req.WXSceneSession;//个人
    private int mTargetTimeline = SendMessageToWX.Req.WXSceneTimeline;//朋友圈

    private IWXAPI api;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.l1: {
                api = WXAPIFactory.createWXAPI(getContext(), Consts.APP_ID, false);
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo1024);

                //初始化 WXImageObject 和 WXMediaMessage 对象
                WXImageObject imgObj = new WXImageObject(bmp);
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = imgObj;

                //设置缩略图
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 80, 80, true);
                bmp.recycle();
                msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("img");
                req.message = msg;
                req.scene = mTargetTimeline;
                // req.userOpenId = getOpenId();
                //调用api接口，发送数据到微信
                api.sendReq(req);
                break;
            }
            case R.id.l2: {
                api = WXAPIFactory.createWXAPI(getContext(), Consts.APP_ID, false);
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo1024);
                //初始化 WXImageObject 和 WXMediaMessage 对象
                WXImageObject imgObj = new WXImageObject(bmp);
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = imgObj;
                //设置缩略图
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 80, 80, true);
                bmp.recycle();
                msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("img");
                req.message = msg;
                req.scene = mTargetScene;
                // req.userOpenId = getOpenId();
                //调用api接口，发送数据到微信
                api.sendReq(req);
                break;
            }
            case R.id.l3:

                break;
            case R.id.l4:

                break;
            case R.id.guanbi:
                dismiss();
                break;
        }
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
