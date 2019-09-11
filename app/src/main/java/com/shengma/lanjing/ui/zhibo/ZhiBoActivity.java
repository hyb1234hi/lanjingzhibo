package com.shengma.lanjing.ui.zhibo;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badoo.mobile.util.WeakHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.adapters.GuanZhongAdapter;
import com.shengma.lanjing.adapters.LiaoTianAdapter;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.GuanZhongBean;
import com.shengma.lanjing.beans.LiaoTianBean;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.dialogs.InputPopupwindow;
import com.shengma.lanjing.dialogs.TuiChuDialog;
import com.shengma.lanjing.liveroom.IMLVBLiveRoomListener;
import com.shengma.lanjing.liveroom.MLVBLiveRoom;
import com.shengma.lanjing.liveroom.MLVBLiveRoomImpl;
import com.shengma.lanjing.liveroom.roomutil.commondef.AnchorInfo;
import com.shengma.lanjing.liveroom.roomutil.commondef.AudienceInfo;
import com.shengma.lanjing.utils.InputMethodUtils;
import com.shengma.lanjing.utils.KeyboardStatusDetector;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ZhiBoActivity extends AppCompatActivity implements IMLVBLiveRoomListener {
    @BindView(R.id.touxiang)
    ImageView touxiang;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.xingguang)
    TextView xingguang;
    @BindView(R.id.guanzhongxiangqiang)
    TextView guanzhongxiangqiang;
    @BindView(R.id.paihangView)
    View paihangView;
    @BindView(R.id.fangjianhao)
    TextView fangjianhao;
    @BindView(R.id.tuichu)
    View tuichu;
    @BindView(R.id.fenxiang)
    View fenxiang;
    @BindView(R.id.fanzhuang)
    View fanzhuang;
    @BindView(R.id.meiyan)
    View meiyan;
    @BindView(R.id.pk)
    View pk;
    @BindView(R.id.shuodian)
    TextView shuodian;
    @BindView(R.id.liaotianReView)
    RecyclerView liaotianReView;
    @BindView(R.id.liwuReView)
    RecyclerView liwuReView;
    private MLVBLiveRoom mlvbLiveRoom = MLVBLiveRoomImpl.sharedInstance(MyApplication.myApplication);
    private BaoCunBean baoCunBean = MyApplication.myApplication.getBaoCunBean();
    private TXCloudVideoView txCloudVideoView;      // 主播本地预览的 View
    private RecyclerView gz_recyclerView;
    private GuanZhongAdapter guanZhongAdapter;
    private List<GuanZhongBean> guanZhuBeanList = new ArrayList<>();
    private Timer timer = new Timer();
    private TimerTask task;
    private WeakHandler mHandler;
    private KeyboardStatusDetector keyboardStatusDetector;
    private InputPopupwindow popupwindow = null;
    private int width,hight;
    private List<LiaoTianBean> liaoTianBeanList=new ArrayList<>();
    private LiaoTianAdapter liaoTianAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_bo);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        width = outMetrics.widthPixels;hight = outMetrics.heightPixels;
        mHandler = new WeakHandler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                switch (message.what) {
                    case 111:

                        break;
                    case 222://聊天的
                        if (liaoTianBeanList.size()>40){
                            Iterator<LiaoTianBean> iter = liaoTianBeanList.iterator();
                            int i=0;
                            while (iter.hasNext()) {
                                i++;
                                LiaoTianBean item = iter.next();
                                  if (i<10) {
                                      iter.remove();
                                  }else {
                                      break;
                                  }
                            }
                        }
                        liaoTianAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
        keyboardStatusDetector = new KeyboardStatusDetector(ZhiBoActivity.this);
        keyboardStatusDetector.registerView(shuodian);
        keyboardStatusDetector.setVisibilityListener(new KeyboardStatusDetector.KeyboardVisibilityListener() {
            @Override
            public void onVisibilityChanged(boolean keyboardVisible, int heightDiff) {
                //  Log.d("ZhiBoActivity", "keyboardVisible:" + keyboardVisible);
                //  Log.d("ZhiBoActivity", "heightDiff:" + heightDiff);
                if (keyboardVisible) {
                    if (popupwindow != null)
                        popupwindow.dismiss();
                    popupwindow = new InputPopupwindow(ZhiBoActivity.this);
                    popupwindow.setOutsideTouchable(true);
                    popupwindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, heightDiff);
                }
            }
        });
        txCloudVideoView = findViewById(R.id.video_player);
        gz_recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ZhiBoActivity.this, LinearLayoutManager.HORIZONTAL, false);
        //设置布局管理器
        gz_recyclerView.setLayoutManager(layoutManager);
        //设置Adapter
        GuanZhongBean bean = new GuanZhongBean();
        bean.setXingguang(31321 + "");
        bean.setHeadImage(baoCunBean.getHeadImage());
        guanZhuBeanList.add(bean);
        guanZhuBeanList.add(bean);
        guanZhuBeanList.add(bean);
        guanZhuBeanList.add(bean);
        guanZhuBeanList.add(bean);
        guanZhuBeanList.add(bean);
        guanZhuBeanList.add(bean);
        guanZhuBeanList.add(bean);
        guanZhuBeanList.add(bean);
        guanZhongAdapter = new GuanZhongAdapter(guanZhuBeanList);
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
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(ZhiBoActivity.this,LinearLayoutManager.VERTICAL,true);
        //设置布局管理器
        liaotianReView.setLayoutManager(layoutManager1);
        //设置Adapter
        liaoTianAdapter=new LiaoTianAdapter(liaoTianBeanList);
        liaotianReView.setAdapter(liaoTianAdapter);



        ConstraintLayout.LayoutParams params= (ConstraintLayout.LayoutParams) liaotianReView.getLayoutParams();
        params.height= (int) (hight*0.33);
        liaotianReView.setLayoutParams(params);
        liaotianReView.invalidate();
        ConstraintLayout.LayoutParams params2= (ConstraintLayout.LayoutParams) liwuReView.getLayoutParams();
        params2.height= (int) (hight*0.33);
        liwuReView.setLayoutParams(params2);
        liwuReView.invalidate();
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
        LiaoTianBean bean=new LiaoTianBean();
        bean.setNickname(audienceInfo.userName);
        bean.setType(2);
        bean.setUserInfo(audienceInfo.userInfo);
        bean.setHeadImage(audienceInfo.userAvatar);
        bean.setUserid(Long.parseLong(audienceInfo.userID));
        liaoTianBeanList.add(bean);

    }

    @Override
    public void onAudienceExit(AudienceInfo audienceInfo) {
        //观众退房
        Log.d("ZhiBoActivity", audienceInfo.toString());
//        LiaoTianBean bean=new LiaoTianBean();
//        bean.setNickname(audienceInfo.userName);
//        bean.setType(3);
//        bean.setUserInfo(audienceInfo.userInfo);
//        bean.setHeadImage(audienceInfo.userAvatar);
//        bean.setUserid(Long.parseLong(audienceInfo.userID));
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
        if (cmd.equals("1")){//发送普通消息
            LiaoTianBean bean=new LiaoTianBean();
            bean.setNickname(userName);
            bean.setType(2);
            //bean.setUserInfo(audienceInfo.userInfo);
            bean.setHeadImage(userAvatar);
            bean.setUserid(Long.parseLong(userID));
            bean.setNeirong(message);
            liaoTianBeanList.add(bean);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(MsgWarp msgWarp) {
        if (msgWarp.getType() == 1005) {
            if (!msgWarp.getMsg().equals("")) {
                if (popupwindow != null)
                    popupwindow.dismiss();
                try {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //发送消息
                Log.d("ZhiBoActivity", msgWarp.getMsg());
                LiaoTianBean bean=new LiaoTianBean();
                bean.setNickname(baoCunBean.getNickname());
                bean.setType(1);
                bean.setDengji(baoCunBean.getAnchorLevel());
                bean.setHeadImage(baoCunBean.getHeadImage());
                bean.setUserid(baoCunBean.getUserid());
                bean.setNeirong(msgWarp.getMsg());
                liaoTianBeanList.add(bean);

                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("sex",baoCunBean.getSex());
                    jsonObject.put("level",baoCunBean.getAnchorLevel());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mlvbLiveRoom.sendRoomCustomMsg("1", jsonObject.toString(), new SendRoomCustomMsgCallback() {
                    @Override
                    public void onError(int errCode, String errInfo) {

                    }

                    @Override
                    public void onSuccess() {

                    }
                });

            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        BaoCunBean baoCunBean = MyApplication.myApplication.getBaoCunBean();
        if (baoCunBean != null) {
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
        timer.cancel();
        if (task != null)
            task.cancel();
        EventBus.getDefault().unregister(this);

        super.onDestroy();


    }

    @Override
    public void onBackPressed() {

        TuiChuDialog tuiChuDialog = new TuiChuDialog(ZhiBoActivity.this);
        tuiChuDialog.setOnQueRenListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlvbLiveRoom.exitRoom(new ExitRoomCallback() {
                    @Override
                    public void onError(int errCode, String errInfo) {
                        tuiChuDialog.dismiss();
                        ZhiBoActivity.this.finish();
                    }

                    @Override
                    public void onSuccess() {
                        tuiChuDialog.dismiss();
                        ZhiBoActivity.this.finish();
                    }
                });
            }
        });
        tuiChuDialog.setQuXiaoListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tuiChuDialog.dismiss();
            }
        });
        tuiChuDialog.setCanceledOnTouchOutside(false);
        tuiChuDialog.show();
        //  super.onBackPressed();
    }

    @OnClick({R.id.guanzhongxiangqiang, R.id.paihangView, R.id.tuichu, R.id.fenxiang,
            R.id.fanzhuang, R.id.meiyan, R.id.pk, R.id.shuodian, R.id.video_player})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.guanzhongxiangqiang:

                break;
            case R.id.paihangView:

                break;
            case R.id.tuichu:
                TuiChuDialog tuiChuDialog = new TuiChuDialog(ZhiBoActivity.this);
                tuiChuDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mlvbLiveRoom.exitRoom(new ExitRoomCallback() {
                            @Override
                            public void onError(int errCode, String errInfo) {
                                tuiChuDialog.dismiss();
                                ZhiBoActivity.this.finish();
                            }

                            @Override
                            public void onSuccess() {
                                tuiChuDialog.dismiss();
                                ZhiBoActivity.this.finish();
                            }
                        });
                    }
                });
                tuiChuDialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tuiChuDialog.dismiss();
                    }
                });
                tuiChuDialog.setCanceledOnTouchOutside(false);
                tuiChuDialog.show();
                break;
            case R.id.fenxiang:

                break;
            case R.id.fanzhuang:
                mlvbLiveRoom.switchCamera();
                break;
            case R.id.meiyan:

                break;
            case R.id.pk:

                break;
            case R.id.video_player:
                if (popupwindow != null)
                    popupwindow.dismiss();
                try {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.shuodian:
                InputMethodUtils.showOrHide(this);
                break;
        }
    }

}