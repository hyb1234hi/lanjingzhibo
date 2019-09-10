package com.shengma.lanjing.ui.zhibo;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badoo.mobile.util.WeakHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.adapters.GuanZhongAdapter;

import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.GuanZhongBean;

import com.shengma.lanjing.liveroom.IMLVBLiveRoomListener;
import com.shengma.lanjing.liveroom.MLVBLiveRoom;
import com.shengma.lanjing.liveroom.MLVBLiveRoomImpl;
import com.shengma.lanjing.liveroom.roomutil.commondef.AnchorInfo;
import com.shengma.lanjing.liveroom.roomutil.commondef.AudienceInfo;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ZhiBoActivity extends AppCompatActivity implements IMLVBLiveRoomListener {
    @BindView(R.id.touxiang)
    ImageView touxiang;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.xingguang)
    TextView xingguang;
    private MLVBLiveRoom mlvbLiveRoom = MLVBLiveRoomImpl.sharedInstance(MyApplication.myApplication);
    private BaoCunBean baoCunBean = MyApplication.myApplication.getBaoCunBean();
    private TXCloudVideoView txCloudVideoView;      // 主播本地预览的 View
    private RecyclerView gz_recyclerView;
    private GuanZhongAdapter guanZhongAdapter;
    private List<GuanZhongBean> guanZhuBeanList=new ArrayList<>();
    private Timer timer = new Timer();
    private TimerTask task;
    private WeakHandler mHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_bo);
        ButterKnife.bind(this);
        mHandler = new WeakHandler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                switch (message.what){
                    case 111:

                        break;
                    case 222:

                        break;
                }
                return false;
            }
        });
        txCloudVideoView = findViewById(R.id.video_player);
        gz_recyclerView=findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ZhiBoActivity.this, LinearLayoutManager.HORIZONTAL,false);
        //设置布局管理器
        gz_recyclerView.setLayoutManager(layoutManager);
        //设置Adapter
        GuanZhongBean bean=new GuanZhongBean();
        bean.setXingguang(31321+"");
        bean.setHeadImage(baoCunBean.getHeadImage());
        guanZhuBeanList.add(bean);guanZhuBeanList.add(bean);guanZhuBeanList.add(bean);
        guanZhuBeanList.add(bean);guanZhuBeanList.add(bean);guanZhuBeanList.add(bean);
        guanZhuBeanList.add(bean);guanZhuBeanList.add(bean);guanZhuBeanList.add(bean);
        guanZhongAdapter=new GuanZhongAdapter(guanZhuBeanList);
        gz_recyclerView.setAdapter(guanZhongAdapter);

        mlvbLiveRoom.setListener(this);
        mlvbLiveRoom.setCameraMuteImage(BitmapFactory.decodeResource(getResources(), R.drawable.pause_publish));
        mlvbLiveRoom.startLocalPreview(true, txCloudVideoView);
        String roomInfo = "";
        try {
            roomInfo = new JSONObject()
                    .put("title", "自定义")
                    .put("frontcover", "自定义")
                    .put("location", "自定义")
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mlvbLiveRoom.createRoom(baoCunBean.getRoomId() + "", roomInfo, new CreateRoomCallback() {
            @Override
            public void onError(int errCode, String errInfo) {
                Log.d("ZhiBoActivity", "errCode:" + errCode);
                Log.d("ZhiBoActivity", "errInfo:" + errInfo);
            }

            @Override
            public void onSuccess(String RoomID) {
                Log.d("ZhiBoActivity", RoomID + "创建成功");
            }
        });

    }



    @Override
    public void onError(int errCode, String errMsg, Bundle extraInfo) {

    }

    @Override
    public void onWarning(int warningCode, String warningMsg, Bundle extraInfo) {

    }

    @Override
    public void onDebugLog(String log) {

    }

    @Override
    public void onRoomDestroy(String roomID) {

    }

    @Override
    public void onAnchorEnter(AnchorInfo anchorInfo) {

    }

    @Override
    public void onAnchorExit(AnchorInfo anchorInfo) {

    }

    @Override
    public void onAudienceEnter(AudienceInfo audienceInfo) {
        //观众进房
        Log.d("ZhiBoActivity", audienceInfo.toString());
    }

    @Override
    public void onAudienceExit(AudienceInfo audienceInfo) {
        //观众退房
        Log.d("ZhiBoActivity", audienceInfo.toString());
    }

    @Override
    public void onRequestJoinAnchor(AnchorInfo anchorInfo, String reason) {

    }

    @Override
    public void onKickoutJoinAnchor() {

    }

    @Override
    public void onRequestRoomPK(AnchorInfo anchorInfo) {

    }

    @Override
    public void onQuitRoomPK(AnchorInfo anchorInfo) {

    }

    @Override
    public void onRecvRoomTextMsg(String roomID, String userID, String userName, String userAvatar, String message) {

    }

    @Override
    public void onRecvRoomCustomMsg(String roomID, String userID, String userName, String userAvatar, String cmd, String message) {

    }


    @Override
    protected void onResume() {
        super.onResume();
       BaoCunBean baoCunBean= MyApplication.myApplication.getBaoCunBean();
        if (baoCunBean!=null){
            xingguang.setText("0");
            name.setText(baoCunBean.getNickname());
            Glide.with(ZhiBoActivity.this)
                    .load(baoCunBean.getHeadImage())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(touxiang);
        }


        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 222;
                mHandler.sendMessage(message);
            }
        };
        timer.schedule(task, 3000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        if (task != null)
            task.cancel();

    }


}
