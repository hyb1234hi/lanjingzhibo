package com.shengma.lanjing.ui.zhibo;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.ImageAssetDelegate;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieImageAsset;
import com.badoo.mobile.util.WeakHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.adapters.GuanZhongAdapter;
import com.shengma.lanjing.adapters.LiWuBoFangAdapter;
import com.shengma.lanjing.adapters.LiaoTianAdapter;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.ChaXunGeRenXinXi;
import com.shengma.lanjing.beans.JieShuPkBean;
import com.shengma.lanjing.beans.LiWuBoFangBean;
import com.shengma.lanjing.beans.LiaoTianBean;
import com.shengma.lanjing.beans.LogingBe;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.beans.XiaZaiLiWuBean;
import com.shengma.lanjing.beans.YongHuListBean;
import com.shengma.lanjing.beans.YongHuListBean_;
import com.shengma.lanjing.dialogs.FenXiangDialog;
import com.shengma.lanjing.dialogs.InputPopupwindow;
import com.shengma.lanjing.dialogs.MeiYanDialog;
import com.shengma.lanjing.dialogs.PKDialog;
import com.shengma.lanjing.dialogs.PKListDialog;
import com.shengma.lanjing.dialogs.PaiHangListDialog;
import com.shengma.lanjing.dialogs.TuiChuDialog;
import com.shengma.lanjing.dialogs.YongHuListDialog;
import com.shengma.lanjing.dialogs.YongHuXinxiDialog;
import com.shengma.lanjing.dialogs.ZhuBoXinxiDialog;
import com.shengma.lanjing.liveroom.IMLVBLiveRoomListener;
import com.shengma.lanjing.liveroom.MLVBLiveRoom;
import com.shengma.lanjing.liveroom.MLVBLiveRoomImpl;
import com.shengma.lanjing.liveroom.roomutil.commondef.AnchorInfo;
import com.shengma.lanjing.liveroom.roomutil.commondef.AudienceInfo;
import com.shengma.lanjing.liveroom.roomutil.commondef.RoomInfo;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.InputMethodUtils;
import com.shengma.lanjing.utils.KeyboardStatusDetector;
import com.shengma.lanjing.utils.ReadAssetsJsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import com.shengma.lanjing.utils.Utils;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import static android.content.ContentValues.TAG;


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
    @BindView(R.id.group)
    Group group;
    @BindView(R.id.video_player2)
    TXCloudVideoView videoPlayer2;
    @BindView(R.id.pktopview)
    View pktopview;
    @BindView(R.id.pkim2)
    ImageView pkim2;
    @BindView(R.id.daojishi)
    TextView daojishi;
    @BindView(R.id.pkbottomview)
    View pkbottomview;
    @BindView(R.id.pkview1)
    View pkview1;
    @BindView(R.id.pkview2)
    View pkview2;
    @BindView(R.id.pkxing1)
    ImageView pkxing1;
    @BindView(R.id.pkxing2)
    ImageView pkxing2;
    @BindView(R.id.pktv1)
    TextView pktv1;
    @BindView(R.id.pktv2)
    TextView pktv2;
    @BindView(R.id.chengfa)
    TextView chengfa;
    @BindView(R.id.donghua)
    ImageView donghua;
    @BindView(R.id.paiming)
    TextView paiming;
    private MLVBLiveRoom mlvbLiveRoom = MLVBLiveRoomImpl.sharedInstance(MyApplication.myApplication);
    private BaoCunBean baoCunBean = MyApplication.myApplication.getBaoCunBean();
    private TXCloudVideoView txCloudVideoView;      // 主播本地预览的 View
    private RecyclerView gz_recyclerView;
    private GuanZhongAdapter guanZhongAdapter;
    private List<YongHuListBean> guanZhuBeanList = new ArrayList<>();
    private Timer timer = new Timer();
    private TimerTask task;
    private WeakHandler mHandler;
    private KeyboardStatusDetector keyboardStatusDetector;
    private InputPopupwindow popupwindow = null;
    private int width, hight;
    private List<LiaoTianBean> liaoTianBeanList = new ArrayList<>();
    private List<LiaoTianBean> lingshiList = new ArrayList<>();
    private LiaoTianAdapter liaoTianAdapter;
    private List<RoomInfo> pkList = new ArrayList<>();
    private PKListDialog pkListDialog;
    private ZLoadingDialog dialog;
    private static CountDownTimer  timer1;//pk
    private static CountDownTimer timer2;//惩罚
    private AnchorInfo anchorInfo;
    private int jianpangHight;
    private List<LiWuBoFangBean> boFangBeanList = new ArrayList<>();
    private LiWuBoFangAdapter liWuBoFangAdapter;
    private boolean isPK = false;
    private LinkedBlockingQueue<Integer> linkedBlockingQueue;
    private TanChuangThread tanChuangThread;
    private Box<YongHuListBean> yongHuListBeanBox = MyApplication.myApplication.getYongHuListBeanBox();
    private LottieAnimationView animationView;
    private long numberGZ=0, pkMoney;
    private String dangqianPKId = "";
    private float pktWidth;
    private boolean isON=true;
    private long pKtime1,pKtime2;
    private Box<XiaZaiLiWuBean> xiaZaiLiWuBeanBox=MyApplication.myApplication.getXiaZaiLiWuBeanBox();
    private int upadteCount=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_zhi_bo);
        ButterKnife.bind(this);
        animationView = findViewById(R.id.animation_view);
        MyApplication.myApplication.getYongHuListBeanBox().removeAll();
        EventBus.getDefault().register(this);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        width = outMetrics.widthPixels;
        hight = outMetrics.heightPixels;
        linkedBlockingQueue = new LinkedBlockingQueue<>();
        liWuBoFangAdapter = new LiWuBoFangAdapter(boFangBeanList);
        mHandler = new WeakHandler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                switch (message.what) {
                    case 111:

                        break;
                    case 222://聊天的
                        upadteCount++;
                        if (upadteCount>2){
                            upadteCount=0;
                            if (liaoTianBeanList.size() > 40) {
                                Iterator<LiaoTianBean> iter = liaoTianBeanList.iterator();
                                int i = 0;
                                while (iter.hasNext()) {
                                    i++;
                                    LiaoTianBean item = iter.next();
                                    if (i < 10) {
                                        iter.remove();
                                    } else {
                                        break;
                                    }
                                }
                            }
                            liaoTianBeanList.addAll(0,lingshiList);
                            lingshiList.clear();
                            liaoTianAdapter.notifyDataSetChanged();
                        }
                        if (isPK){
                            link_jieshupk(dangqianPKId);
                        }
                      //  Log.d("ZhiBoActivity", "更新聊天界面");
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
                jianpangHight = heightDiff;
//                if (keyboardVisible && isAA) {
//                    if (popupwindow != null)
//                        popupwindow.dismiss();
//                    popupwindow = new InputPopupwindow(ZhiBoActivity.this);
//                    popupwindow.setOutsideTouchable(true);
//                    if (!popupwindow.isShowing()) {
//                        popupwindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, heightDiff);
//                    }
//                } else {
//                    if (popupwindow != null)
//                        popupwindow.dismiss();
//                }
            }
        });
        txCloudVideoView = findViewById(R.id.video_player);
        gz_recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ZhiBoActivity.this, LinearLayoutManager.HORIZONTAL, false);
        //设置布局管理器
        gz_recyclerView.setLayoutManager(layoutManager);
        //设置Adapter
        guanZhongAdapter = new GuanZhongAdapter(guanZhuBeanList);
        gz_recyclerView.setAdapter(guanZhongAdapter);

        mlvbLiveRoom.setListener(this);
        mlvbLiveRoom.setCameraMuteImage(R.drawable.pause_publish);
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

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(ZhiBoActivity.this, LinearLayoutManager.VERTICAL, true);
        //设置布局管理器
        liaotianReView.setLayoutManager(layoutManager1);

        //设置Adapter
        liaoTianAdapter = new LiaoTianAdapter(liaoTianBeanList);
        liaoTianAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("ZhiBoActivity", "长按");
                if (!(baoCunBean.getUserid()+"").equals(liaoTianBeanList.get(position).getUserid()+"")){
                    YongHuXinxiDialog yongHuXinxiDialog=new YongHuXinxiDialog(baoCunBean.getUserid()+"",liaoTianBeanList.get(position).getUserid()+"");
                    yongHuXinxiDialog.show(getSupportFragmentManager(),"yonghuxnxi");
                }
                return false;
            }
        });
        liaotianReView.setAdapter(liaoTianAdapter);
//        liaoTianAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
////            @Override public void onLoadMoreRequested() {
////                //mQuickAdapter.loadMoreEnd();mQuickAdapter.loadMoreComplete();mQuickAdapter.loadMoreFail();
////                liaoTianAdapter.loadMoreEnd();
////
////            }
////        }, liaotianReView);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(ZhiBoActivity.this, LinearLayoutManager.VERTICAL, true);
        liwuReView.setLayoutManager(layoutManager2);
        liwuReView.setAdapter(liWuBoFangAdapter);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) liaotianReView.getLayoutParams();
        params.height = (int) (hight * 0.33);
        liaotianReView.setLayoutParams(params);
        liaotianReView.invalidate();
        ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) liwuReView.getLayoutParams();
        params2.height = (int) (hight * 0.33);
        liwuReView.setLayoutParams(params2);
        liwuReView.invalidate();

        mlvbLiveRoom.createRoom(baoCunBean.getUserid() + "", roomInfo, baoCunBean.getPushUrl(), new CreateRoomCallback() {
            @Override
            public void onError(int errCode, String errInfo) {
                Log.d("ZhiBoActivity", "errCode:" + errCode);
                Log.d("ZhiBoActivity", "errInfo:" + errInfo);
                ToastUtils.showInfo(ZhiBoActivity.this, "创建房间失败,请退出后重试");
            }
            @Override
            public void onSuccess(String RoomID) {
                Log.d("ZhiBoActivity", RoomID + "创建成功");
                LiaoTianBean bean =new LiaoTianBean();
                bean.setType(2);
                bean.setNickname("");
                bean.setNeirong(" 欢迎来到直播间！直播内容严禁包含政治\n、低俗色情、吸烟酗酒等内容，若有违反，\n账号会被禁封。");
                liaoTianBeanList.add(bean);
                liaoTianAdapter.notifyDataSetChanged();
            }
        });
        tanChuangThread = new TanChuangThread();
        tanChuangThread.start();
        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 222;
                mHandler.sendMessage(message);

            }
        };
        timer.schedule(task, 3000, 1000);
    }


    @Override
    public void onError(int errCode, String errMsg, Bundle extraInfo) {
            Log.d("ZhiBoActivity", "errCode:" + errCode);
            Log.d("ZhiBoActivity","errCode:"+ errMsg);
            Log.d("ZhiBoActivity", "errCode:"+extraInfo.toString());

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
//        Log.d("ZhiBoActivity", audienceInfo.toString());
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                LiaoTianBean bean = new LiaoTianBean();
//                bean.setNickname(audienceInfo.userName);
//                bean.setType(2);
//                bean.setUserInfo(audienceInfo.userInfo);
//                bean.setHeadImage(audienceInfo.userAvatar);
//                bean.setUserid(Long.parseLong(audienceInfo.userID));
//                lingshiList.add(0, bean);
//
//            }
//        });

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
    public void onRequestRoomPK(AnchorInfo anchorInfo2) {
        Log.d("ggggg", "被动收到PK请求");
        if (!isPK) {
            isPK = true;
            //启动倒计时关闭pk弹窗
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(8);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (pkListDialog!=null)
                                pkListDialog.dismiss();
                        }
                    });
                }
            }).start();

            anchorInfo = anchorInfo2;
            dangqianPKId = anchorInfo.userID;
            PKDialog pkDialog = new PKDialog(ZhiBoActivity.this, anchorInfo.userID);
            pkDialog.setCanceledOnTouchOutside(false);
            pkDialog.setOnQueRenListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //拒绝
                    pkDialog.dismiss();
                    mlvbLiveRoom.responseRoomPK(anchorInfo.userID, false, "");
                    isPK = false;
                }
            });
            pkDialog.setQuXiaoListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //同意
                    pkDialog.dismiss();
                    mlvbLiveRoom.responseRoomPK(anchorInfo.userID, true, "");
                    mlvbLiveRoom.startRemoteView(anchorInfo, videoPlayer2, new PlayCallback() {
                        @Override
                        public void onBegin() {
                            link_kaishihunliu(anchorInfo.userID, baoCunBean.getUserid() + "");
                            Log.d("ZhiBoActivity", "onBegin");
                            pktv1.setText("0");
                            pktv2.setText("0");
                            txCloudVideoView.setVisibility(View.GONE);
                            group.setVisibility(View.VISIBLE);
                            startPKMessageToAll();
                            timer1 = new CountDownTimer(600000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    pKtime1=millisUntilFinished;//传送给进直播间的观众
                                    String st = Utils.timeParse((millisUntilFinished));
                                    daojishi.setText(st);
                                }
                                @Override
                                public void onFinish() {
                                    //第一次倒计时结束
                                    chengfa.setVisibility(View.VISIBLE);
                                    timer2 = new CountDownTimer(100000, 1000) {

                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            pKtime2=millisUntilFinished;//传送给进直播间的观众
                                            String st = Utils.timeParse((millisUntilFinished));
                                            daojishi.setText(st);
                                            dangqianPKId = "";
                                        }

                                        @Override
                                        public void onFinish() {
                                            //惩罚倒计时结束
                                            chengfa.setVisibility(View.GONE);
                                            group.setVisibility(View.GONE);
                                            link_endmlx(anchorInfo.userID, baoCunBean.getUserid() + "");
                                            mlvbLiveRoom.stopRemoteView(anchorInfo);
                                            txCloudVideoView.setVisibility(View.VISIBLE);
                                            mlvbLiveRoom.quitRoomPK(new QuitRoomPKCallback() {
                                                @Override
                                                public void onError(int errCode, String errInfo) {
                                                }
                                                @Override
                                                public void onSuccess() {
                                                }
                                            });
                                            isPK = false;
                                        }
                                    };
                                    timer2.start();

                                }
                            };
                            timer1.start();
                        }
                        @Override
                        public void onError(int errCode, String errInfo) {
                        }
                        @Override
                        public void onEvent(int event, Bundle param) {

                        }
                    });
                }
            });
            pkDialog.show();
        }
    }
    @Override
    public void onQuitRoomPK(AnchorInfo anchorInfo) {
        //结束Pk
        Log.d("ZhiBoActivity", "收到结束PK");
        chengfa.setVisibility(View.GONE);
        group.setVisibility(View.GONE);
        link_endmlx(anchorInfo.userID, baoCunBean.getUserid() + "");
        mlvbLiveRoom.stopRemoteView(anchorInfo);
        txCloudVideoView.setVisibility(View.VISIBLE);
        pkMoney = 0;
        isPK = false;
    }

    @Override
    public void onRecvRoomTextMsg(String roomID, String userID, String userName, String userAvatar, String message) {
    }
    @Override
    public void onRecvRoomCustomMsg(String roomID, String userID, String userName, String userAvatar, String cmd, String message) {
        switch (cmd) {
            case "1": //收到普通消息
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LiaoTianBean bean = com.alibaba.fastjson.JSONObject.parseObject(message, LiaoTianBean.class);
                        lingshiList.add(0, bean);
                    }
                });
                break;
            case "liwudonghua1": //收到普通礼物消息
                synchronized (ZhiBoActivity.this) {
                    LiWuBoFangBean parseUser = com.alibaba.fastjson.JSONObject.parseObject(message, LiWuBoFangBean.class);
                    boFangBeanList.add(0, parseUser);
                    liWuBoFangAdapter.notifyDataSetChanged();
                    linkedBlockingQueue.offer(1);
                }
                link_userinfo(baoCunBean.getUserid() + "");
                break;
            case "liwudonghua2": //收到大型礼物消息
                LiWuBoFangBean parseUser = com.alibaba.fastjson.JSONObject.parseObject(message, LiWuBoFangBean.class);
                XiaZaiLiWuBean liWuBean=xiaZaiLiWuBeanBox.get(Long.parseLong(parseUser.getLiwuID()));
                if (liWuBean.isJY()){//解压过了
                    playDongHua(parseUser.getLiwuID());
                }
                synchronized (ZhiBoActivity.this){
                    boFangBeanList.add(0, parseUser);
                    liWuBoFangAdapter.notifyDataSetChanged();
                    linkedBlockingQueue.offer(1);
                }
                link_userinfo(baoCunBean.getUserid() + "");
                break;
            case "rufang": //收到观众入房消息
                numberGZ += 1;
                if (numberGZ<0){
                    numberGZ=0;
                }
                guanzhongxiangqiang.setText(numberGZ + "");
                YongHuListBean yongHuListBean = com.alibaba.fastjson.JSONObject.parseObject(message, YongHuListBean.class);
                MyApplication.myApplication.getYongHuListBeanBox().put(yongHuListBean);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // Log.d("ZhiBoActivity", "收到观众入房" + yongHuListBean.getName());
                        LiaoTianBean bean = new LiaoTianBean();
                        bean.setNickname(yongHuListBean.getName());
                        bean.setType(2);
                        bean.setNeirong("加入直播间");
                        bean.setHeadImage(yongHuListBean.getHeadImage());
                        bean.setUserid((yongHuListBean.getId()));
                        lingshiList.add( bean);
                    }
                });
                List<YongHuListBean> listBeans = yongHuListBeanBox.query().orderDesc(YongHuListBean_.jingbi).build().find(0, 8);
                guanZhuBeanList.clear();
                guanZhuBeanList.addAll(listBeans);
                guanZhongAdapter.notifyDataSetChanged();
                //发送当前pk时间
                if (isPK){
                    if (pKtime1!=600000){
                        zhongtuPKMessageToAll("1",pKtime1+"");
                    }else {
                        zhongtuPKMessageToAll("2",pKtime2+"");
                    }
                }
                //发送当前人数
                mlvbLiveRoom.sendRoomCustomMsg("roomNum", numberGZ+"", new SendRoomCustomMsgCallback() {
                    @Override
                    public void onError(int errCode, String errInfo) {
                    }
                    @Override
                    public void onSuccess() {
                    }
                });
                break;
            case "tuifang": //收到观众tui房消息
                numberGZ -= 1;
                if (numberGZ<0){
                    numberGZ=0;
                }
                guanzhongxiangqiang.setText(numberGZ+"");
                YongHuListBean huListBean = com.alibaba.fastjson.JSONObject.parseObject(message, YongHuListBean.class);
                MyApplication.myApplication.getYongHuListBeanBox().remove(huListBean.getId());
                guanZhongAdapter.notifyDataSetChanged();
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(MsgWarp msgWarp) {
        if (msgWarp.getType() == 1005) {//收到输入的消息广播
            if (!msgWarp.getMsg().equals("")) {
                //  ToastUtils.showInfo(ZhiBoActivity.this,"收到关闭键盘通知");
                try {
//                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
//                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
//                                  InputMethodManager.HIDE_NOT_ALWAYS);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null && txCloudVideoView != null) {
                        // imm.hideSoftInputFromWindow(txCloudVideoView.getWindowToken(), 0);
                        imm.showSoftInput(txCloudVideoView, InputMethodManager.SHOW_IMPLICIT);
                    }
                    if (popupwindow != null)
                        popupwindow.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showInfo(ZhiBoActivity.this, "收到关闭键盘通知" + e.getMessage());
                }
                //发送消息
                Log.d("ZhiBoActivity", msgWarp.getMsg());
                LiaoTianBean bean = new LiaoTianBean();
                bean.setNickname(baoCunBean.getNickname());
                bean.setType(1);
                bean.setDengji(baoCunBean.getAnchorLevel());
                bean.setHeadImage(baoCunBean.getHeadImage());
                bean.setUserid(baoCunBean.getUserid());
                bean.setNeirong(msgWarp.getMsg());
                bean.setSex(baoCunBean.getSex());
                liaoTianBeanList.add(0, bean);
                liaoTianAdapter.notifyDataSetChanged();
                String js = com.alibaba.fastjson.JSONObject.toJSONString(bean);
                mlvbLiveRoom.sendRoomCustomMsg("1", js, new SendRoomCustomMsgCallback() {
                    @Override
                    public void onError(int errCode, String errInfo) {
                        Log.d("ZhiBoActivity", "errCode:" + errCode);
                        Log.d("ZhiBoActivity", "errInfo:" + errInfo);
                    }
                    @Override
                    public void onSuccess() {
                        Log.d("ZhiBoActivity", "发送自定义消息成功1");
                    }
                });
            }
        }
        if (msgWarp.getType() == 1006) {//主动发起PK
            isPK = true;
            if (pkListDialog != null)
                pkListDialog.dismiss();
            dialog = new ZLoadingDialog(ZhiBoActivity.this);
            dialog.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)//设置类型
                    .setLoadingColor(Color.parseColor("#FF3EE1F7"))//颜色
                    .setHintText("等待对方接受...")
                    .setHintTextSize(16) // 设置字体大小 dp
                    .setHintTextColor(Color.WHITE)  // 设置字体颜色
                    .setDurationTime(0.6) // 设置动画时间百分比 - 0.5倍
                    .setDialogBackgroundColor(Color.parseColor("#bb111111")) // 设置背景色，默认白色
                    .show();
            mlvbLiveRoom.requestRoomPK(msgWarp.getMsg(), new RequestRoomPKCallback() {
                @Override
                public void onAccept(AnchorInfo anchorInfo2) {
                    anchorInfo = anchorInfo2;
                    //接受
                    if (dialog != null)
                        dialog.dismiss();
                    Log.d("ZhiBoActivity", "接受" + anchorInfo.accelerateURL);
                    dangqianPKId = anchorInfo.userID;
                    mlvbLiveRoom.startRemoteView(anchorInfo, videoPlayer2, new PlayCallback() {
                        @Override
                        public void onBegin() {
                            //link_kaishihunliu(anchorInfo.userID, baoCunBean.getUserid() + "");
                            Log.d("ZhiBoActivity", "onBegin");
                            txCloudVideoView.setVisibility(View.GONE);
                            pktv1.setText("0");
                            pktv2.setText("0");
                            group.setVisibility(View.VISIBLE);
                            startPKMessageToAll();
                            timer1 = new CountDownTimer(600000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    pKtime1=millisUntilFinished;//传送给进直播间的观众
                                    String st = Utils.timeParse((millisUntilFinished));
                                    daojishi.setText(st);
                                }
                                @Override
                                public void onFinish() {
                                    //第一次倒计时结束
                                    chengfa.setVisibility(View.VISIBLE);
                                    timer2 = new CountDownTimer(100000, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            pKtime2=millisUntilFinished;//传送给进直播间的观众
                                            String st = Utils.timeParse((millisUntilFinished));
                                            daojishi.setText(st);
                                            dangqianPKId = "";
                                        }
                                        @Override
                                        public void onFinish() {
                                            //惩罚倒计时结束
                                            chengfa.setVisibility(View.GONE);
                                            group.setVisibility(View.GONE);
                                          //  link_endmlx(anchorInfo.userID, baoCunBean.getId() + "");
                                            mlvbLiveRoom.stopRemoteView(anchorInfo);
                                            txCloudVideoView.setVisibility(View.VISIBLE);
                                            mlvbLiveRoom.quitRoomPK(new QuitRoomPKCallback() {
                                                @Override
                                                public void onError(int errCode, String errInfo) {
                                                }
                                                @Override
                                                public void onSuccess() {
                                                }
                                            });
                                            isPK = false;
                                        }
                                    };
                                    timer2.start();
                                }
                            };
                            timer1.start();
                        }
                        @Override
                        public void onError(int errCode, String errInfo) {
                            Log.d("ZhiBoActivity", "onError" + errInfo + errCode);
                        }
                        @Override
                        public void onEvent(int event, Bundle param) {
                            Log.d("ZhiBoActivity", "onEvent" + event + param.toString());
                        }
                    });
                }

                @Override
                public void onReject(String reason) {
                    //拒绝
                    if (dialog != null)
                        dialog.dismiss();
                    Log.d("ZhiBoActivity", "拒绝");
                    ToastUtils.showInfo(ZhiBoActivity.this, "对方拒绝PK");
                    isPK = false;
                }
                @Override
                public void onTimeOut() {
                    if (dialog != null)
                        dialog.dismiss();
                    ToastUtils.showInfo(ZhiBoActivity.this, "超时未接受");
                }
                @Override
                public void onError(int errCode, String errInfo) {
                    if (dialog != null)
                        dialog.dismiss();
                    ToastUtils.showInfo(ZhiBoActivity.this, "网络异常");
                }
            });
        }
        if (msgWarp.getType() == 3368) {//发送设置管理员自定义消息
            if (msgWarp.getMsg().equals("0")){//取消
                mlvbLiveRoom.sendRoomCustomMsg("setGLY", "0", new SendRoomCustomMsgCallback() {
                    @Override
                    public void onError(int errCode, String errInfo) {
                    }
                    @Override
                    public void onSuccess() {
                    }
                });
            }else {//设置
                mlvbLiveRoom.sendRoomCustomMsg("setGLY", "1", new SendRoomCustomMsgCallback() {
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
        //查询主播信息
        link_userinfo(baoCunBean.getUserid() + "");
    }


    @Override
    protected void onDestroy() {
        mlvbLiveRoom.quitRoomPK(new QuitRoomPKCallback() {
            @Override
            public void onError(int errCode, String errInfo) {
            }
            @Override
            public void onSuccess() {
            }
        });
        timer.cancel();
        link_tuichuzhibo();
        if (task != null)
            task.cancel();
        EventBus.getDefault().unregister(this);
        if (anchorInfo != null)
            link_endmlx(anchorInfo.userID, baoCunBean.getUserid() + "");

        if (timer1 != null)
            timer1.cancel();
        if (timer2 != null)
            timer2.cancel();
        linkedBlockingQueue.clear();
        if (tanChuangThread != null)
            tanChuangThread.interrupt();

        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        TuiChuDialog tuiChuDialog = new TuiChuDialog(ZhiBoActivity.this);
        tuiChuDialog.setOnQueRenListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (anchorInfo != null)
                    link_endmlx(anchorInfo.userID, baoCunBean.getUserid() + "");
                mlvbLiveRoom.exitRoom(new ExitRoomCallback() {
                    @Override
                    public void onError(int errCode, String errInfo) {
                        mlvbLiveRoom.setListener(null);
                        tuiChuDialog.dismiss();
                        ZhiBoActivity.this.finish();
                    }
                    @Override
                    public void onSuccess() {
                        mlvbLiveRoom.setListener(null);
                        tuiChuDialog.dismiss();
                        ZhiBoActivity.this.finish();
                    }
                });
                link_tuichuzhibo();
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
            R.id.fanzhuang, R.id.meiyan, R.id.pk, R.id.shuodian, R.id.video_player, R.id.touxiang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.guanzhongxiangqiang:
                YongHuListDialog yongHuListDialog = new YongHuListDialog(baoCunBean.getUserid() + "");
                yongHuListDialog.setzhu(true);
                yongHuListDialog.show(getSupportFragmentManager(), "yuanogn");

                break;
            case R.id.paihangView:
                PaiHangListDialog paiHangListDialog = new PaiHangListDialog();
                paiHangListDialog.show(getSupportFragmentManager(), "paihang");

                break;
            case R.id.tuichu:
                TuiChuDialog tuiChuDialog = new TuiChuDialog(ZhiBoActivity.this);
                tuiChuDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (anchorInfo != null)
                            link_endmlx(anchorInfo.userID, baoCunBean.getUserid() + "");
                        mlvbLiveRoom.exitRoom(new ExitRoomCallback() {
                            @Override
                            public void onError(int errCode, String errInfo) {
                                mlvbLiveRoom.setListener(null);
                                tuiChuDialog.dismiss();
                                ZhiBoActivity.this.finish();
                            }

                            @Override
                            public void onSuccess() {
                                mlvbLiveRoom.setListener(null);
                                tuiChuDialog.dismiss();
                                ZhiBoActivity.this.finish();
                            }
                        });
                        link_tuichuzhibo();
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
                FenXiangDialog dialog = new FenXiangDialog();
                dialog.show(getSupportFragmentManager(), "fenxiang");
                break;
            case R.id.fanzhuang:
                mlvbLiveRoom.switchCamera();

                break;
            case R.id.meiyan:
                MeiYanDialog meiYanDialog = new MeiYanDialog();
                meiYanDialog.show(getSupportFragmentManager(), "meiyan");
                // mlvbLiveRoom.setBeautyStyle()
                break;
            case R.id.pk:
                mlvbLiveRoom.getRoomList(0, 400, new GetRoomListCallback() {
                    @Override
                    public void onError(int errCode, String errInfo) {
                        pkList.clear();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showInfo(ZhiBoActivity.this, "暂无合适的PK主播");
                            }
                        });
                    }
                    @Override
                    public void onSuccess(ArrayList<RoomInfo> roomInfoList) {
                        pkList.clear();
                        for (RoomInfo info : roomInfoList) {
                            if (Long.parseLong(info.roomCreator) != baoCunBean.getUserid()) {
                                pkList.add(info);
                            }
                        }
                        pkListDialog = new PKListDialog(pkList);
                        pkListDialog.show(getSupportFragmentManager(), "pklist");
                    }
                });
                break;
            case R.id.video_player:
                try {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (popupwindow != null)
                        popupwindow.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.shuodian:
                InputMethodUtils.showOrHide(this);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(200);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (popupwindow != null)
                                    popupwindow.dismiss();
                                popupwindow = new InputPopupwindow(ZhiBoActivity.this);
                                popupwindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, jianpangHight);
                            }
                        });
                    }
                }).start();
                break;
            case R.id.touxiang:
                ZhuBoXinxiDialog zhuBoXinxiDialog = new ZhuBoXinxiDialog(baoCunBean.getUserid() + "");
                zhuBoXinxiDialog.show(getSupportFragmentManager(), "zhuboxinxi");
                break;
        }
    }

    private void link_kaishihunliu(String id1, String id2) {
        Log.d("ZhiBoActivity", "混流fromId:" + id1);
        Log.d("ZhiBoActivity", "混流toId:" + id2);
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("fromId", id2)
                .add("toId", id1)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/live/mix");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(ZhiBoActivity.this, "获取数据失败,请检查网络");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "混流" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    LogingBe logingBe = gson.fromJson(jsonObject, LogingBe.class);
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(ZhiBoActivity.this, "获取数据失败");
                }
            }
        });
    }

    private void link_tuichuzhibo() {
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("", "")
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/live/end");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                //  ToastUtils.showError(WoDeZiLiaoActivity.this, "获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "退出直播:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    // ToastUtils.showError(BoFangActivity.this, "获取数据失败");
                }
            }
        });
    }

    private void link_endmlx(String id1, String id2) {
        if (timer1 != null)
            timer1.cancel();
        timer1=null;
        if (timer2 != null)
            timer2.cancel();
            timer2=null;
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("fromId", id1)
                .add("toId", id2)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/live/mix/cancel");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(ZhiBoActivity.this, "获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", id1+"  "+id2+"  混流" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    LogingBe logingBe = gson.fromJson(jsonObject, LogingBe.class);
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(ZhiBoActivity.this, "获取数据失败");
                }
            }
        });
    }


    private class TanChuangThread extends Thread {
        boolean isRing;

        @Override
        public void run() {
            while (!isRing) {
                try {
                    //有动画 ，延迟到一秒一次
                    Integer subject = linkedBlockingQueue.take();
                    SystemClock.sleep(4000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (ZhiBoActivity.this) {
                                boFangBeanList.remove(boFangBeanList.size() - 1);
                                liWuBoFangAdapter.notifyDataSetChanged();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.d("RecognizeThread", "中断了弹窗线程");
        }

        @Override
        public void interrupt() {
            isRing = true;
             Log.d("RecognizeThread", "中断了弹窗线程");
            super.interrupt();
        }
    }

    private void playDongHua(String idid) {
        final String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        //提供一个代理接口从 SD 卡读取 images 下的图片
        animationView.setVisibility(View.VISIBLE);
        animationView.cancelAnimation();
        animationView.removeAllAnimatorListeners();
        animationView.setImageAssetDelegate(new ImageAssetDelegate() {
            @Override
            public Bitmap fetchBitmap(LottieImageAsset asset) {
                // Log.d("BoFangActivity", asset.getFileName());
                Bitmap bitmap = null;
                FileInputStream fileInputStream = null;
                try {
                    fileInputStream = new FileInputStream(absolutePath + File.separator + "lanjing/" + idid + "/images/" + asset.getFileName());
                    bitmap = BitmapFactory.decodeStream(fileInputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                    animationView.cancelAnimation();
                    animationView.setVisibility(View.GONE);
                  XiaZaiLiWuBean xiaZaiLiWuBean= xiaZaiLiWuBeanBox.get(Long.parseLong(idid));
                    if (xiaZaiLiWuBean!=null){
                        xiaZaiLiWuBean.setD(false);
                        xiaZaiLiWuBean.setJY(false);
                        xiaZaiLiWuBeanBox.put(xiaZaiLiWuBean);
                    }
                } finally {
                    try {
                        if (bitmap == null) {
                            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }

                    } catch (IOException e) {
                        Log.d(TAG, "e:" + e);
                        animationView.cancelAnimation();
                        animationView.setVisibility(View.GONE);
                    }
                }
                return bitmap;
            }
        });

        animationView.setAnimationFromJson(ReadAssetsJsonUtil.readJSON(idid));
        animationView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("BoFangActivity", "结束了");
                animationView.cancelAnimation();
                animationView.setVisibility(View.GONE);
                super.onAnimationEnd(animation);
            }
        });
        animationView.playAnimation();
    }


    private void link_userinfo(String id) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL + "/anchor/info/" + id);
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                //  ToastUtils.showError(WoDeZiLiaoActivity.this, "获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "查询个人信息:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    ChaXunGeRenXinXi logingBe = gson.fromJson(jsonObject, ChaXunGeRenXinXi.class);
                    if (logingBe.getCode() == 2000) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                paiming.setText("总排名:" + logingBe.getResult().getRank());
                                name.setText(logingBe.getResult().getNickname());
                                xingguang.setText(logingBe.getResult().getStarLight() + "");
                                fangjianhao.setText("LANJING " + logingBe.getResult().getId());
                                Glide.with(ZhiBoActivity.this)
                                        .load(logingBe.getResult().getHeadImage())
                                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                        .into(touxiang);
                            }
                        });
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    // ToastUtils.showError(BoFangActivity.this, "获取数据失败");

                }
            }
        });
    }


    private void link_jieshupk(String toId) {//fromId自己的id
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object = new JSONObject();
        try {
            object.put("fromId", baoCunBean.getUserid() + "");
            object.put("toId", toId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Log.d("ZhiBoActivity", "礼物参数"+object.toString());
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/live/pk/result");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                // ToastUtils.showError(ZhiBoActivity.this,"获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("ZhiBoActivity", "结束PK" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    JieShuPkBean userInfoBean = gson.fromJson(jsonObject, JieShuPkBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           // group.setVisibility(View.VISIBLE);
                          //  Log.d("ZhiBoActivity", "pkview1.getWidth():" + pkview1.getWidth());
                         //   Log.d("ZhiBoActivity", "pkview1.getWidth():" + pkview2.getWidth());
                            int pkv1=pkview1.getWidth();
                            int pkv2=pkview2.getWidth();
                            //(A-B)÷Bx100%
                            if (userInfoBean != null && userInfoBean.getResult() != null
                                    && userInfoBean.getResult().getOwnGift() != null && userInfoBean.getResult().getOtherGift() != null) {

                                float me = Float.parseFloat(userInfoBean.getResult().getOwnGift());
                                float to = Float.parseFloat(userInfoBean.getResult().getOtherGift());
                                if (me==0 && to ==0){
                                    return;
                                }
                                pktv1.setText(((int)me)+"");
                                pktv2.setText(((int)to)+"");
                                if (pkv1==0 || pkv2==0){
                                    return;
                                }else {
                                    if (isON){
                                        isON=false;
                                        pktWidth=pkv1;
                                    }
                                    if (me>to){
                                      float f1= (float)Math.abs(((to-me)/me)*(pktWidth/2.0));
                                        pkxuetiao((int) (pktWidth+f1), pkview1);
                                        pkxuetiao((int) (pktWidth-f1), pkview2);
                                        updataPKMessageToAll(me+"",to+"");
                                    }else if (me<to){
                                        float f2=(float) Math.abs((((me-to)/to)*(pktWidth/2.0)));
                                        pkxuetiao((int) (pktWidth-f2), pkview1);
                                        pkxuetiao((int) (pktWidth+f2), pkview2);
                                        updataPKMessageToAll(me+"",to+"");
                                    }
                                }
                                Log.d("ZhiBoActivity", "dddd");
                            }

                        }
                    });
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    //ToastUtils.showError(QianBaoActivity.this,"获取数据失败");

                }
            }
        });
    }



    private void updataPKMessageToAll(String me,String to){
        mlvbLiveRoom.sendRoomCustomMsg("updatapkall", me + "," + to, new SendRoomCustomMsgCallback() {
            @Override
            public void onError(int errCode, String errInfo) {

            }
            @Override
            public void onSuccess() {

            }
        });
    }

    private void startPKMessageToAll(){
        mlvbLiveRoom.sendRoomCustomMsg("startpkall", "", new SendRoomCustomMsgCallback() {
            @Override
            public void onError(int errCode, String errInfo) {

            }
            @Override
            public void onSuccess() {

            }
        });
    }


    private void zhongtuPKMessageToAll(String type,String time){
        mlvbLiveRoom.sendRoomCustomMsg("zhongtupkall", type+","+time, new SendRoomCustomMsgCallback() {
            @Override
            public void onError(int errCode, String errInfo) {

            }
            @Override
            public void onSuccess() {

            }
        });
    }

    private void pkxuetiao(int width1, View view) {

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        params.width=width1;
        view.setLayoutParams(params);
        view.invalidate();
    }
}