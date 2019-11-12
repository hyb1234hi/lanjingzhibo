package com.shengma.lanjing.ui.zhibo;


import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.ImageAssetDelegate;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieImageAsset;
import com.badoo.mobile.util.WeakHandler;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.meihu.beautylibrary.manager.MHBeautyManager;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.adapters.GuanZhongAdapter;
import com.shengma.lanjing.adapters.LiWuBoFangAdapter;
import com.shengma.lanjing.adapters.LiaoTianAdapter;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.ChaXunGeRenXinXi;
import com.shengma.lanjing.beans.JieShuPkBean;
import com.shengma.lanjing.beans.KaiBoBean;
import com.shengma.lanjing.beans.LiWuBoFangBean;
import com.shengma.lanjing.beans.LiaoTianBean;
import com.shengma.lanjing.beans.LogingBe;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.beans.XiaZaiLiWuBean;
import com.shengma.lanjing.beans.YongHuListBean;
import com.shengma.lanjing.beans.YongHuListBean_;
import com.shengma.lanjing.dialogs.FenXiangDialog;
import com.shengma.lanjing.dialogs.InputPopupwindow;
import com.shengma.lanjing.dialogs.JuLiDialog;
import com.shengma.lanjing.dialogs.PKDialog;
import com.shengma.lanjing.dialogs.PKListDialog;
import com.shengma.lanjing.dialogs.PaiHangListDialog;
import com.shengma.lanjing.dialogs.PhotoDialog;
import com.shengma.lanjing.dialogs.PingDaoDialog;
import com.shengma.lanjing.dialogs.TuiChuDialog;
import com.shengma.lanjing.dialogs.YongHuListDialog;
import com.shengma.lanjing.dialogs.YongHuXinxiDialog;
import com.shengma.lanjing.dialogs.ZhuBoXinxiDialog;
import com.shengma.lanjing.liveroom.IMLVBLiveRoomListener;
import com.shengma.lanjing.liveroom.MLVBLiveRoom;
import com.shengma.lanjing.liveroom.MLVBLiveRoomImpl;
import com.shengma.lanjing.liveroom.roomutil.commondef.AnchorInfo;
import com.shengma.lanjing.liveroom.roomutil.commondef.AudienceInfo;
import com.shengma.lanjing.liveroom.roomutil.commondef.LoginInfo;
import com.shengma.lanjing.meihu.enums.FilterEnum;
import com.shengma.lanjing.meihu.interfaces.DefaultBeautyEffectListener;
import com.shengma.lanjing.meihu.views.BaseBeautyViewHolder;
import com.shengma.lanjing.meihu.views.BeautyViewHolderFactory;
import com.shengma.lanjing.ui.ZhengJianXinXiActivity;
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
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.ContentValues.TAG;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.shengma.lanjing.meihu.enums.FilterEnum.BEAUTIFUL_FILTER;
import static com.shengma.lanjing.meihu.enums.FilterEnum.BLUES_FILTER;
import static com.shengma.lanjing.meihu.enums.FilterEnum.COOL_FILTER;
import static com.shengma.lanjing.meihu.enums.FilterEnum.FRESH_FILTER;
import static com.shengma.lanjing.meihu.enums.FilterEnum.JAPANESE_FILTER;
import static com.shengma.lanjing.meihu.enums.FilterEnum.NOSTALGIC_FILTER;
import static com.shengma.lanjing.meihu.enums.FilterEnum.NO_FILTER;
import static com.shengma.lanjing.meihu.enums.FilterEnum.PINK_FILTER;
import static com.shengma.lanjing.meihu.enums.FilterEnum.ROMANTIC_FILTER;


public class ZhiBoActivity extends FragmentActivity implements IMLVBLiveRoomListener, DefaultBeautyEffectListener {
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
    @BindView(R.id.toptop)
    View toptop;
    @BindView(R.id.pkjgim1)
    ImageView pkjgim1;
    @BindView(R.id.pkjgim2)
    ImageView pkjgim2;
    @BindView(R.id.name_3)
    TextView name3;
    @BindView(R.id.name_zhibojian)
    TextView nameZhibojian;
    @BindView(R.id.name_liwu)
    TextView nameLiwu;
    @BindView(R.id.hengfu)
    ConstraintLayout hengfu;
    @BindView(R.id.rootv)
    LinearLayout rootv;
    @BindView(R.id.rootview)
    ConstraintLayout rootview;
    @BindView(R.id.group2)
    Group group2;
    @BindView(R.id.group1)
    Group group1;
    @BindView(R.id.meiyan1)
    ImageView meiyan1;
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
    // private List<RoomInfo> pkList = new ArrayList<>();
    private PKListDialog pkListDialog;
    private ZLoadingDialog dialog;
    private static CountDownTimer timer1;//pk
    private static CountDownTimer timer2;//惩罚
    private AnchorInfo anchorInfo;
    private int jianpangHight;
    private List<LiWuBoFangBean> boFangBeanList = new ArrayList<>();
    private LiWuBoFangAdapter liWuBoFangAdapter;
    private boolean isPK = false;
    private LinkedBlockingQueue<Integer> linkedBlockingQueue;
    private LinkedBlockingQueue<String> linkedBlockingQueueLW;
    private TanChuangThreadLW tanChuangThreadLW;
    private TanChuangThread tanChuangThread;
    private Box<YongHuListBean> yongHuListBeanBox = MyApplication.myApplication.getYongHuListBeanBox();
    private LottieAnimationView animationView;
    private long numberGZ = 0;
    private String dangqianPKId = "";
    private float pktWidth;
    private boolean isON = true;
    private long pKtime1, pKtime2;
    private Box<XiaZaiLiWuBean> xiaZaiLiWuBeanBox = MyApplication.myApplication.getXiaZaiLiWuBeanBox();
    private int upadteCount = 0, upadteCountPK = 0;
    // private int shenli = -1;
    private PKDialog pkDialog = null;
    protected InputMethodManager imm;
    private BaseBeautyViewHolder beautyViewHolder;
    private MHBeautyManager mMhSDKManager;
    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.fengmian)
    ImageView fengmian;
    @BindView(R.id.weizhi)
    View weizhi;
    @BindView(R.id.pingdao)
    View pingdao;
    @BindView(R.id.kaibo)
    TextView kaibo;
    @BindView(R.id.fenxiang1)
    ImageView fenxiang1;
    @BindView(R.id.zhuti)
    EditText zhuti;
    @BindView(R.id.frrrd)
    TextView frrrd;
    @BindView(R.id.xianshi)
    TextView xianshi;
    private PhotoDialog photoDialog;
    ZLoadingDialog dialog_kaibo;
    private int liveType = -1;
    private String fengmianPath = "";
    private LocationClient locationClient;
    private int count = 0;
    private String jd = "", wd = "";
    private int m1=0,m2=0,m3=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_zhi_bo);
        ButterKnife.bind(this);
        imm = (InputMethodManager) ZhiBoActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        animationView = findViewById(R.id.animation_view);
        MyApplication.myApplication.getYongHuListBeanBox().removeAll();
        EventBus.getDefault().register(this);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        width = outMetrics.widthPixels;
        hight = outMetrics.heightPixels;
        linkedBlockingQueue = new LinkedBlockingQueue<>();
        linkedBlockingQueueLW = new LinkedBlockingQueue<>();
        liWuBoFangAdapter = new LiWuBoFangAdapter(boFangBeanList);
        mHandler = new WeakHandler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                switch (message.what) {
                    case 111:

                        break;
                    case 222://聊天的
                        upadteCount++;
                        upadteCountPK++;
                        if (upadteCount > 1) {
                            upadteCount = 0;
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
                            liaoTianBeanList.addAll(0, lingshiList);
                            lingshiList.clear();
                            liaoTianAdapter.notifyDataSetChanged();
                        }
                        //  Log.d("ZhiBoActivity", "isPK:" + isPK);
                        if (isPK) {
                            link_jieshupk(dangqianPKId);
                            if (upadteCountPK >= 18) {
                                upadteCountPK = 0;
                                link_iszaixian(dangqianPKId);
                            }
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
        guanZhongAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ZhuBoXinxiDialog zhuBoXinxiDialog = new ZhuBoXinxiDialog(guanZhuBeanList.get(position).getId() + "");
                zhuBoXinxiDialog.show(getSupportFragmentManager(), "dfrttt");
            }
        });
        gz_recyclerView.setAdapter(guanZhongAdapter);

        mlvbLiveRoom.setListener(this);
        mlvbLiveRoom.setCameraMuteImage(R.drawable.pause_publish);
        mlvbLiveRoom.startLocalPreview(true, txCloudVideoView, ZhiBoActivity.this);
//        String roomInfo = "";
//        try {
//            roomInfo = new JSONObject()
//                    .put("title", "自定义")
//                    .put("frontcover", "自定义")
//                    .put("location", "自定义")
//                    .toString();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(ZhiBoActivity.this, LinearLayoutManager.VERTICAL, true);
        //设置布局管理器
        liaotianReView.setLayoutManager(layoutManager1);

        //设置Adapter
        liaoTianAdapter = new LiaoTianAdapter(liaoTianBeanList);
        liaoTianAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("ZhiBoActivity", "长按");
                if (baoCunBean.getUserid() != 0 && !(baoCunBean.getUserid() + "").equals(liaoTianBeanList.get(position).getUserid() + "")) {
                    YongHuXinxiDialog yongHuXinxiDialog = new YongHuXinxiDialog(baoCunBean.getUserid() + "", liaoTianBeanList.get(position).getUserid() + "", true);
                    yongHuXinxiDialog.show(getSupportFragmentManager(), "yonghuxnxi");
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
        params.height = (int) (hight * 0.30);
        liaotianReView.setLayoutParams(params);
        liaotianReView.invalidate();
        ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) liwuReView.getLayoutParams();
        params2.height = (int) (hight * 0.33);
        liwuReView.setLayoutParams(params2);
        liwuReView.invalidate();

        ConstraintLayout.LayoutParams params3 = (ConstraintLayout.LayoutParams) videoPlayer2.getLayoutParams();
        params3.width = (int) (width / 2.0);
        params3.height = (int) (hight * 0.38);
        videoPlayer2.setLayoutParams(params3);
        videoPlayer2.invalidate();


        tanChuangThread = new TanChuangThread();
        tanChuangThread.start();
        tanChuangThreadLW = new TanChuangThreadLW();
        tanChuangThreadLW.start();

        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 222;
                mHandler.sendMessage(message);

            }
        };
        timer.schedule(task, 3000, 1600);

        beautyViewHolder = BeautyViewHolderFactory.getBeautyViewHolder(this, rootview);
        beautyViewHolder.setEffectListener(this);

        showGPSContacts();

        mMhSDKManager = mlvbLiveRoom.getMHBeautyManager();
        beautyViewHolder.setMhBeautyManager(mMhSDKManager);

        beautyViewHolder.hide();
    }


    @Override
    public void onError(int errCode, String errMsg, Bundle extraInfo) {
        // Log.d("ZhiBoActivity", "errCode:" + errCode);
        // Log.d("ZhiBoActivity", "errCode:" + errMsg);
        //   Log.d("ZhiBoActivity", "errCode:" + extraInfo.toString());
        if (errCode == -2301) {
            if (isPK) {
                dangqianPKId = "";
                chengfa.setVisibility(View.GONE);
                pkjgim1.setVisibility(View.GONE);
                pkjgim2.setVisibility(View.GONE);
                group.setVisibility(View.GONE);
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) txCloudVideoView.getLayoutParams();
                params.height = hight;
                params.width = width;
                txCloudVideoView.setLayoutParams(params);
                txCloudVideoView.invalidate();
                link_endmlx(anchorInfo.userID, baoCunBean.getUserid() + "");
                mlvbLiveRoom.stopRemoteView(anchorInfo);
                mlvbLiveRoom.quitRoomPK(new QuitRoomPKCallback() {
                    @Override
                    public void onError(int errCode, String errInfo) {
                    }

                    @Override
                    public void onSuccess() {
                    }
                });
                isPK = false;
                ToastUtils.showInfo(ZhiBoActivity.this, "主播已经下线");
            }
        }
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
        Log.d("ggggg", "被动收到PK请求" + anchorInfo2.accelerateURL);
        if (!isPK) {
            isPK = true;
            anchorInfo = anchorInfo2;
            dangqianPKId = anchorInfo.userID;
            pkDialog = new PKDialog(ZhiBoActivity.this, anchorInfo.userID);
            pkDialog.setCanceledOnTouchOutside(false);
            pkDialog.setOnQueRenListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //拒绝

                    mlvbLiveRoom.responseRoomPK(anchorInfo.userID, false, "");
                    isPK = false;
                    pkDialog.dismiss();
                    pkDialog = null;
                }
            });
            pkDialog.setQuXiaoListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //同意
                    mlvbLiveRoom.responseRoomPK(anchorInfo.userID, true, "");
                    mlvbLiveRoom.startRemoteView(anchorInfo, videoPlayer2, new PlayCallback() {
                        @Override
                        public void onBegin() {
                            link_kaishihunliu(anchorInfo.userID, baoCunBean.getUserid() + "");
                            Log.d("ZhiBoActivity", "onBegin");
                            pktv1.setText("0");
                            pktv2.setText("0");
                            group.setVisibility(View.VISIBLE);
                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) txCloudVideoView.getLayoutParams();
                            params.height = (int) (hight * 0.38);
                            params.width = width / 2;
                            txCloudVideoView.setLayoutParams(params);
                            txCloudVideoView.invalidate();
                            startPKMessageToAll();
                            timer1 = new CountDownTimer(600000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    pKtime1 = millisUntilFinished;//传送给进直播间的观众
                                    String st = Utils.timeParse((millisUntilFinished));
                                    daojishi.setText(st);
                                }

                                @Override
                                public void onFinish() {
                                    //第一次倒计时结束
                                    chengfa.setVisibility(View.VISIBLE);
                                    pkjgim1.setVisibility(View.VISIBLE);
                                    pkjgim2.setVisibility(View.VISIBLE);
                                    int p1 = Integer.parseInt(pktv1.getText().toString());
                                    int p2 = Integer.parseInt(pktv2.getText().toString());
                                    if (p1 > p2) {
                                        pkjgim1.setBackgroundResource(R.drawable.shenlibg);
                                        pkjgim2.setBackgroundResource(R.drawable.shibaibg);
                                    } else if (p1 < p2) {
                                        pkjgim1.setBackgroundResource(R.drawable.shibaibg);
                                        pkjgim2.setBackgroundResource(R.drawable.shenlibg);
                                    } else {
                                        pkjgim1.setBackgroundResource(R.drawable.pingjubg);
                                        pkjgim2.setBackgroundResource(R.drawable.pingjubg);
                                    }
                                    timer2 = new CountDownTimer(100000, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            pKtime2 = millisUntilFinished;//传送给进直播间的观众
                                            String st = Utils.timeParse((millisUntilFinished));
                                            daojishi.setText(st);

                                        }

                                        @Override
                                        public void onFinish() {
                                            //惩罚倒计时结束
                                            dangqianPKId = "";
                                            chengfa.setVisibility(View.GONE);
                                            pkjgim1.setVisibility(View.GONE);
                                            pkjgim2.setVisibility(View.GONE);
                                            group.setVisibility(View.GONE);
                                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) txCloudVideoView.getLayoutParams();
                                            params.height = hight;
                                            params.width = width;
                                            txCloudVideoView.setLayoutParams(params);
                                            txCloudVideoView.invalidate();
                                            link_endmlx(anchorInfo.userID, baoCunBean.getUserid() + "");
                                            mlvbLiveRoom.stopRemoteView(anchorInfo);
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
                            // isPK = false;
                        }

                        @Override
                        public void onEvent(int event, Bundle param) {
                        }
                    });
                    pkDialog.dismiss();
                    pkDialog = null;
                }
            });
            pkDialog.show();
            //启动倒计时关闭pk弹窗
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(8000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (pkDialog != null) {
                                pkDialog.dismiss();
                                isPK = false;
                            }
                        }
                    });
                }
            }).start();
        }
    }

    @Override
    public void onQuitRoomPK(AnchorInfo anchorInfo) {
        //结束Pk
        Log.d("ZhiBoActivity", "收到结束PK");
        ToastUtils.showError(ZhiBoActivity.this, "主播已经结束PK");
        if (timer1 != null)
            timer1.cancel();
        timer1 = null;
        if (timer2 != null)
            timer2.cancel();
        timer2 = null;
        chengfa.setVisibility(View.GONE);
        pkjgim1.setVisibility(View.GONE);
        pkjgim2.setVisibility(View.GONE);
        group.setVisibility(View.GONE);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) txCloudVideoView.getLayoutParams();
        params.height = hight;
        params.width = width;
        txCloudVideoView.setLayoutParams(params);
        txCloudVideoView.invalidate();
        link_endmlx(anchorInfo.userID, baoCunBean.getUserid() + "");
        mlvbLiveRoom.stopRemoteView(anchorInfo);
        isPK = false;
        //因为有延迟，发消息给观众关掉PK
        mlvbLiveRoom.sendRoomCustomMsg("guanbiPK", "", new SendRoomCustomMsgCallback() {
            @Override
            public void onError(int errCode, String errInfo) {
            }

            @Override
            public void onSuccess() {
            }
        });

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
                XiaZaiLiWuBean liWuBean = xiaZaiLiWuBeanBox.get(Long.parseLong(parseUser.getLiwuID()));
                if (liWuBean.isJY()) {//解压过了
                    // playDongHua(parseUser.getLiwuID());
                    linkedBlockingQueueLW.offer(parseUser.getLiwuID());
                }
                synchronized (ZhiBoActivity.this) {
                    boFangBeanList.add(0, parseUser);
                    liWuBoFangAdapter.notifyDataSetChanged();
                    linkedBlockingQueue.offer(1);
                }
                link_userinfo(baoCunBean.getUserid() + "");
                break;
            case "rufang": //收到观众入房消息
                numberGZ += 1;
                if (numberGZ < 0) {
                    numberGZ = 0;
                }
                guanzhongxiangqiang.setText(numberGZ + "");
                YongHuListBean yongHuListBean = com.alibaba.fastjson.JSONObject.parseObject(message, YongHuListBean.class);
                MyApplication.myApplication.getYongHuListBeanBox().put(yongHuListBean);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("ZhiBoActivity", "收到观众入房" + yongHuListBean.getName());
                        LiaoTianBean bean = new LiaoTianBean();
                        bean.setNickname(yongHuListBean.getName());
                        bean.setType(2);
                        bean.setNeirong("加入直播间");
                        bean.setHeadImage(yongHuListBean.getHeadImage());
                        bean.setUserid((yongHuListBean.getId()));
                        lingshiList.add(bean);
                    }
                });
                List<YongHuListBean> listBeans = yongHuListBeanBox.query().orderDesc(YongHuListBean_.jingbi).build().find(0, 8);
                guanZhuBeanList.clear();
                guanZhuBeanList.addAll(listBeans);
                guanZhongAdapter.notifyDataSetChanged();

                //发送当前pk时间
                if (isPK) {
                    if (pKtime1 != 600000) {//pk第一阶段
                        zhongtuPKMessageToAll("1", pKtime1 + "");
                    } else {//惩罚阶段
                        zhongtuPKMessageToAll("2", pKtime2 + "");
                    }
                }
                //发送当前人数
                mlvbLiveRoom.sendRoomCustomMsg("roomNum", numberGZ + "", new SendRoomCustomMsgCallback() {
                    @Override
                    public void onError(int errCode, String errInfo) {
                        //发送给中途进入的观众
                        String sss = com.alibaba.fastjson.JSONObject.toJSONString(listBeans);
                        mlvbLiveRoom.sendRoomCustomMsg("dangqianGZ", sss, new SendRoomCustomMsgCallback() {
                            @Override
                            public void onError(int errCode, String errInfo) {
                            }

                            @Override
                            public void onSuccess() {
                            }
                        });
                    }

                    @Override
                    public void onSuccess() {
                        //发送给中途进入的观众
                        String sss = com.alibaba.fastjson.JSONObject.toJSONString(listBeans);
                        mlvbLiveRoom.sendRoomCustomMsg("dangqianGZ", sss, new SendRoomCustomMsgCallback() {
                            @Override
                            public void onError(int errCode, String errInfo) {
                            }

                            @Override
                            public void onSuccess() {
                            }
                        });
                    }
                });
                break;
            case "tuifang": //收到观众tui房消息
                numberGZ -= 1;
                if (numberGZ < 0) {
                    numberGZ = 0;
                }
                guanzhongxiangqiang.setText(numberGZ + "");
                YongHuListBean huListBean = com.alibaba.fastjson.JSONObject.parseObject(message, YongHuListBean.class);
                MyApplication.myApplication.getYongHuListBeanBox().remove(huListBean.getId());
                guanZhongAdapter.notifyDataSetChanged();
                break;
            case "guanzhu":
                LiaoTianBean bean22 = new LiaoTianBean();
                bean22.setNickname(message);
                bean22.setType(2);
                bean22.setNeirong("关注了主播");
                //bean.setHeadImage(yongHuListBean.getHeadImage());
                bean22.setUserid(0);
                lingshiList.add(bean22);
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
                    dangqianPKId = anchorInfo.userID;
                    Log.d("ZhiBoActivity", anchorInfo.toString() + "个人信息");
                    mlvbLiveRoom.startRemoteView(anchorInfo, videoPlayer2, new PlayCallback() {
                        @Override
                        public void onBegin() {
                            //link_kaishihunliu(anchorInfo.userID, baoCunBean.getUserid() + "");
                            Log.d("ZhiBoActivity", "onBegin");
                            pktv1.setText("0");
                            pktv2.setText("0");
                            group.setVisibility(View.VISIBLE);
                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) txCloudVideoView.getLayoutParams();
                            params.height = (int) (hight * 0.38);
                            params.width = width / 2;
                            txCloudVideoView.setLayoutParams(params);
                            txCloudVideoView.invalidate();
                            startPKMessageToAll();
                            timer1 = new CountDownTimer(600000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    pKtime1 = millisUntilFinished;//传送给进直播间的观众
                                    String st = Utils.timeParse((millisUntilFinished));
                                    daojishi.setText(st);
                                }

                                @Override
                                public void onFinish() {
                                    //第一次倒计时结束
                                    chengfa.setVisibility(View.VISIBLE);
                                    pkjgim1.setVisibility(View.VISIBLE);
                                    pkjgim2.setVisibility(View.VISIBLE);
                                    int p1 = Integer.parseInt(pktv1.getText().toString());
                                    int p2 = Integer.parseInt(pktv2.getText().toString());
                                    if (p1 > p2) {
                                        pkjgim1.setBackgroundResource(R.drawable.shenlibg);
                                        pkjgim2.setBackgroundResource(R.drawable.shibaibg);
                                    } else if (p1 < p2) {
                                        pkjgim1.setBackgroundResource(R.drawable.shibaibg);
                                        pkjgim2.setBackgroundResource(R.drawable.shenlibg);
                                    } else {
                                        pkjgim1.setBackgroundResource(R.drawable.pingjubg);
                                        pkjgim2.setBackgroundResource(R.drawable.pingjubg);
                                    }
                                    timer2 = new CountDownTimer(100000, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            pKtime2 = millisUntilFinished;//传送给进直播间的观众
                                            String st = Utils.timeParse((millisUntilFinished));
                                            daojishi.setText(st);

                                        }

                                        @Override
                                        public void onFinish() {
                                            //惩罚倒计时结束
                                            dangqianPKId = "";
                                            chengfa.setVisibility(View.GONE);
                                            pkjgim1.setVisibility(View.GONE);
                                            pkjgim2.setVisibility(View.GONE);
                                            group.setVisibility(View.GONE);
                                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) txCloudVideoView.getLayoutParams();
                                            params.height = hight;
                                            params.width = width;
                                            txCloudVideoView.setLayoutParams(params);
                                            txCloudVideoView.invalidate();
                                            mlvbLiveRoom.stopRemoteView(anchorInfo);
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
                            // isPK = false;
                        }

                        @Override
                        public void onEvent(int event, Bundle param) {
                            Log.d("ZhiBoActivity", "onEvent" + event + param.toString());
                            // isPK = false;
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
                    isPK = false;
                }

                @Override
                public void onError(int errCode, String errInfo) {
                    if (dialog != null)
                        dialog.dismiss();
                    ToastUtils.showInfo(ZhiBoActivity.this, "网络异常");
                    isPK = false;
                }
            });
        }
        if (msgWarp.getType() == 3368) {//发送设置管理员自定义消息
            if (msgWarp.getMsg().equals("0")) {//取消
                mlvbLiveRoom.sendRoomCustomMsg("setGLY", "0", new SendRoomCustomMsgCallback() {
                    @Override
                    public void onError(int errCode, String errInfo) {
                    }

                    @Override
                    public void onSuccess() {
                    }
                });
            } else {//设置
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

        if (msgWarp.getType() == 3369) {//发送设置管理员自定义消息1是禁言 0是未禁言
            mlvbLiveRoom.sendRoomCustomMsg("chaxunJY", "0", new SendRoomCustomMsgCallback() {
                @Override
                public void onError(int errCode, String errInfo) {
                }

                @Override
                public void onSuccess() {
                }
            });
        }
        Log.d("ZhiBoActivity", "msgWarp.getType():" + msgWarp.getType());
        if (msgWarp.getType() == 887700) {//@别人
            Log.d("ZhiBoActivity", "收到8877" + msgWarp.getMsg());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(200);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    });
                    SystemClock.sleep(200);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (popupwindow != null)
                                popupwindow.dismiss();
                            popupwindow = new InputPopupwindow(ZhiBoActivity.this, msgWarp.getMsg());
                            popupwindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, jianpangHight);
                            //
                        }
                    });
                }
            }).start();
            if (msgWarp.getType() == 9981) {
                ToastUtils.showInfo(ZhiBoActivity.this, "抱歉，您已被强制停播");
                finish();
            }
        }
        if (msgWarp.getType() == 100868) {
            //横幅
            String[] sls = msgWarp.getMsg().split(",");
            View view_dk = View.inflate(ZhiBoActivity.this, R.layout.hengfu_item, null);
            TextView tv1 = view_dk.findViewById(R.id.name_3);
            TextView tv2 = view_dk.findViewById(R.id.name_zhibojian);
            TextView tv3 = view_dk.findViewById(R.id.name_liwu);
            tv1.setText(sls[0]);
            tv2.setText(sls[1]);
            tv3.setText(sls[2]);
            rootv.addView(view_dk);
            view_dk.setX(width);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view_dk.getLayoutParams();
            //params.leftMargin = width;
            view_dk.setLayoutParams(params);
            view_dk.invalidate();
            ValueAnimator animator = ValueAnimator.ofInt(width, -width);
            Interpolator interpolator = new LinearInterpolator();
            animator.setInterpolator(interpolator);
            //如下传入多个参数，效果则为0->5,5->3,3->10
            //ValueAnimator animator = ValueAnimator.ofInt(0,5,3,10);
            //设置动画的基础属性
            animator.setDuration(8000);//播放时长
            // animator.setStartDelay(300);//延迟播放
            animator.setRepeatCount(0);//重放次数
            //  animator.setRepeatMode(ValueAnimator.RESTART);
            //重放模式
            //ValueAnimator.START：正序
            //ValueAnimator.REVERSE：倒序
            //设置更新监听
            //值 改变一次，该方法就执行一次
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //获取改变后的值
                    int currentValue = (int) animation.getAnimatedValue();
                    //输出改变后的值
                    //  Log.d("1111", "onAnimationUpdate: " + currentValue);
                    //改变后的值发赋值给对象的属性值
                    view_dk.setTranslationX(currentValue);
                    //刷新视图
                    view_dk.requestLayout();
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d("BoFangActivity", "结束");
                    super.onAnimationEnd(animation);
                    rootv.removeView(view_dk);
                }
            });
            //启动动画
            animator.start();

        }
        if (msgWarp.getType() == 1003) {
            dialog = new ZLoadingDialog(ZhiBoActivity.this);
            dialog.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)//设置类型
                    .setLoadingColor(Color.parseColor("#FF3EE1F7"))//颜色
                    .setHintText("加载中...")
                    .setHintTextSize(16) // 设置字体大小 dp
                    .setHintTextColor(Color.WHITE)  // 设置字体颜色
                    .setDurationTime(0.6) // 设置动画时间百分比 - 0.5倍
                    .setDialogBackgroundColor(Color.parseColor("#bb111111")) // 设置背景色，默认白色
                    .show();
            link_loging(msgWarp.getMsg());
            // Log.d("KaiBoActivity", msgWarp.getMsg());
        } else if (msgWarp.getType() == 100) {
            liveType = Integer.parseInt(msgWarp.getTemp());
            frrrd.setText(msgWarp.getMsg());
        } else if (msgWarp.getType() == 6668) {
            if (msgWarp.getMsg().equals("kai")) {
                initLocationOption();
            } else {
                jd = "";
                wd = "";
                xianshi.setText("不显示距离");
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
        if (beautyViewHolder != null)
            beautyViewHolder.release();
        timer.cancel();
        if (anchorInfo != null)
            link_endmlx(anchorInfo.userID, baoCunBean.getUserid() + "");
        link_tuichuzhibo();
        linkedBlockingQueue.clear();
        if (tanChuangThread != null)
            tanChuangThread.interrupt();
        if (task != null)
            task.cancel();
        EventBus.getDefault().unregister(this);
        if (timer1 != null)
            timer1.cancel();
        timer1 = null;
        if (timer2 != null)
            timer2.cancel();
        timer2 = null;
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        if (!isPK) {
            TuiChuDialog tuiChuDialog = new TuiChuDialog(ZhiBoActivity.this);
            tuiChuDialog.setOnQueRenListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
        } else {
            ToastUtils.showInfo(ZhiBoActivity.this, "抱歉,PK中无法退出");
        }

        //  super.onBackPressed();
    }

    @OnClick({R.id.guanzhongxiangqiang, R.id.paihangView, R.id.tuichu, R.id.fenxiang,
            R.id.fanzhuang, R.id.meiyan, R.id.pk, R.id.shuodian, R.id.video_player, R.id.touxiang, R.id.fanhui, R.id.fengmian, R.id.weizhi, R.id.pingdao, R.id.kaibo, R.id.fenxiang1,R.id.meiyan1})
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
                if (!isPK) {
                    TuiChuDialog tuiChuDialog = new TuiChuDialog(ZhiBoActivity.this);
                    tuiChuDialog.setOnQueRenListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
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
                } else {
                    ToastUtils.showInfo(ZhiBoActivity.this, "抱歉,PK中无法退出");
                }
                break;
            case R.id.fenxiang:
                FenXiangDialog dialog = new FenXiangDialog();
                dialog.show(getSupportFragmentManager(), "fenxiang");
                break;
            case R.id.fanzhuang:
                mlvbLiveRoom.switchCamera();

                break;
            case R.id.meiyan:
                // Log.d("ZhiBoActivity", "txCloudVideoView.getHWVideoView().getBitmap().getWidth():" + txCloudVideoView.getSurf
                //  Log.d("ZhiBoActivity", "txCloudVideoView.getHWVideoView().getBitmap().getWidth():" + txCloudVideoView.getHWVideoView().getBitmap().getHeight());
                //    MeiYanDialog meiYanDialog = new MeiYanDialog();
                //   meiYanDialog.show(getSupportFragmentManager(), "meiyan");
                beautyViewHolder.show(); //显示美颜菜单
                // mlvbLiveRoom.setBeautyStyle()

                break;
            case R.id.pk:
                if (!isPK) {
                    pkListDialog = new PKListDialog();
                    pkListDialog.show(getSupportFragmentManager(), "pklist");
//                    mlvbLiveRoom.getRoomList(0, 400, new GetRoomListCallback() {
//                        @Override
//                        public void onError(int errCode, String errInfo) {
//                            pkList.clear();
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ToastUtils.showInfo(ZhiBoActivity.this, "暂无合适的PK主播");
//                                }
//                            });
//                        }
//                        @Override
//                        public void onSuccess(ArrayList<RoomInfo> roomInfoList) {
//                            pkList.clear();
//                            for (RoomInfo info : roomInfoList) {
//                                if (Long.parseLong(info.roomCreator) != baoCunBean.getUserid()) {
//                                    pkList.add(info);
//                                }
//                            }
//
//                        }
//                    });
                }
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
                                popupwindow = new InputPopupwindow(ZhiBoActivity.this, "");
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
            case R.id.fanhui:
                finish();
                break;
            case R.id.fengmian: {
                photoDialog = new PhotoDialog();
                photoDialog.show(getSupportFragmentManager(), "photodialog");
                break;
            }
            case R.id.weizhi: {
                JuLiDialog juLiDialog = new JuLiDialog();
                juLiDialog.show(getSupportFragmentManager(), "juli");
                break;
            }
            case R.id.pingdao: {
                PingDaoDialog daoDialog = new PingDaoDialog();
                daoDialog.show(getSupportFragmentManager(), "pingdao");

                break;
            }
            case R.id.kaibo:

                if (fengmianPath == null) {
                    ToastUtils.showInfo(ZhiBoActivity.this, "请先上传封面");
                    return;
                }
                if (zhuti.getText().toString().trim().equals("")) {
                    ToastUtils.showInfo(ZhiBoActivity.this, "请先填写主题");
                    return;
                }
                if (liveType == -1) {
                    ToastUtils.showInfo(ZhiBoActivity.this, "请先选择直播类型");
                    return;
                }
                kaibo.setEnabled(false);
                dialog_kaibo = new ZLoadingDialog(ZhiBoActivity.this);
                dialog_kaibo.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)//设置类型
                        .setLoadingColor(Color.parseColor("#FF3EE1F7"))//颜色
                        .setHintText("加载中...")
                        .setHintTextSize(16) // 设置字体大小 dp
                        .setHintTextColor(Color.WHITE)  // 设置字体颜色
                        .setDurationTime(0.6) // 设置动画时间百分比 - 0.5倍
                        .setDialogBackgroundColor(Color.parseColor("#bb111111")) // 设置背景色，默认白色
                        .show();
                link_end();
                break;
            case R.id.fenxiang1: {
                FenXiangDialog fenXiangDialog = new FenXiangDialog();
                fenXiangDialog.show(getSupportFragmentManager(), "fenxiang1");

                break;
            }
            case R.id.meiyan1:{
                beautyViewHolder.show();
                break;
            }
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
                // Log.d("AllConnects", "请求失败" + e.getMessage());
                // //  ToastUtils.showError(WoDeZiLiaoActivity.this, "获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //  Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    //  Log.d("AllConnects", "退出直播:" + ss);
                    //  JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();

                } catch (Exception e) {
                    //  Log.d("AllConnects", e.getMessage() + "异常");
                    // ToastUtils.showError(BoFangActivity.this, "获取数据失败");
                }
            }
        });
    }

    private void link_endmlx(String id1, String id2) {
        if (timer1 != null)
            timer1.cancel();
        timer1 = null;
        if (timer2 != null)
            timer2.cancel();
        timer2 = null;
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
                // Log.d("AllConnects", "请求失败" + e.getMessage());
                // ToastUtils.showError(ZhiBoActivity.this, "获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    // Log.d("AllConnects", id1 + "  " + id2 + "  混流" + ss);
                    //  JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    // Gson gson = new Gson();
                    //  LogingBe logingBe = gson.fromJson(jsonObject, LogingBe.class);
                } catch (Exception e) {
                    // Log.d("AllConnects", e.getMessage() + "异常");
                    // ToastUtils.showError(ZhiBoActivity.this, "获取数据失败");
                }
            }
        });
    }

    //美狐回调
    @Override
    public void onFilterChanged(FilterEnum filterEnumEnum) {
        //滤镜
        mlvbLiveRoom.setFilter(BitmapFactory.decodeResource(getResources(),R.drawable.filter_qingxin));
        Bitmap lookupBitmap = null;
        switch (filterEnumEnum) {
            case NO_FILTER:
                break;
            case ROMANTIC_FILTER:
                lookupBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.filter_langman);
                break;
            case FRESH_FILTER:
                lookupBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.filter_qingxin);
                break;
            case BEAUTIFUL_FILTER:
                lookupBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.filter_weimei);
                break;
            case PINK_FILTER:
                lookupBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.filter_fennen);
                break;
            case NOSTALGIC_FILTER:
                lookupBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.filter_huaijiu);
                break;
            case COOL_FILTER:
                lookupBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.filter_qingliang);
                break;
            case BLUES_FILTER:
                lookupBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.filter_landiao);
                break;
            case JAPANESE_FILTER:
                lookupBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.filter_rixi);
                break;
        }
        mlvbLiveRoom.setFilter(lookupBitmap);

    }

    @Override
    public void onMeiBaiChanged(int progress) {
        //美白
        m1=progress;
        mlvbLiveRoom.setBeautyStyle(0,m1,m2,m3);
    }

    @Override
    public void onMoPiChanged(int progress) {
        //磨皮
        m2=progress;
        mlvbLiveRoom.setBeautyStyle(0,m1,m2,m3);
    }

    @Override
    public void onFengNenChanged(int progress) {
        //粉嫩
        m3=progress;
        mlvbLiveRoom.setBeautyStyle(0,m1,m2,m3);
    }

    @Override
    public void onBeautyOrigin() {
        mlvbLiveRoom.setBeautyStyle(0,0,0,0);

    }


    private class TanChuangThread extends Thread {
        boolean isRing;

        @Override
        public void run() {
            while (!isRing) {
                try {
                    //有动画 ，延迟到一秒一次
                    Integer subject = linkedBlockingQueue.take();
                    SystemClock.sleep(5000);
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

    private boolean isLWPlay = false;

    private class TanChuangThreadLW extends Thread {
        boolean isRing;

        @Override
        public void run() {
            while (!isRing) {
                try {
                    //有动画 ，延迟到一秒一次
                    String id = linkedBlockingQueueLW.take();
                    isLWPlay = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            playDongHua(id);
                        }
                    });
                    while (!isLWPlay) {
                        SystemClock.sleep(200);
                    }
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
                    String paths = absolutePath + File.separator + "lanjing/" + idid + "/images/" + asset.getFileName();
                    File file = new File(paths);
                    if (file.exists()) {
                        fileInputStream = new FileInputStream(paths);
                        bitmap = BitmapFactory.decodeStream(fileInputStream);
                    } else {
                        animationView.cancelAnimation();
                        animationView.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    animationView.cancelAnimation();
                    animationView.setVisibility(View.GONE);
                    XiaZaiLiWuBean xiaZaiLiWuBean = xiaZaiLiWuBeanBox.get(Long.parseLong(idid));
                    if (xiaZaiLiWuBean != null) {
                        xiaZaiLiWuBean.setD(false);
                        xiaZaiLiWuBean.setJY(false);
                        xiaZaiLiWuBeanBox.put(xiaZaiLiWuBean);
                    }
                    isLWPlay = true;
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
                        isLWPlay = true;
                    }
                }
                return bitmap;
            }
        });
        String sss = ReadAssetsJsonUtil.readJSON(idid);
        if (sss.equals("")) {
            animationView.setVisibility(View.GONE);
            return;
        }
        animationView.setAnimationFromJson(sss);
        animationView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("BoFangActivity", "结束了");
                animationView.cancelAnimation();
                animationView.setVisibility(View.GONE);

                super.onAnimationEnd(animation);
                isLWPlay = true;
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
                                double xg = logingBe.getResult().getStarLight();
                                if (xg >= 10000) {
                                    xingguang.setText(Utils.doubleToString(xg / 10000.0) + "万");
                                } else {
                                    xingguang.setText(xg + "");
                                }
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
        Log.d("ZhiBoActivity", "礼物参数" + object.toString());
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
                            int pkv1 = pkview1.getWidth();
                            int pkv2 = pkview2.getWidth();
                            //(A-B)÷Bx100%
                            if (userInfoBean != null && userInfoBean.getResult() != null
                                    && userInfoBean.getResult().getOwnGift() != null && userInfoBean.getResult().getOtherGift() != null) {

                                float me = Float.parseFloat(userInfoBean.getResult().getOwnGift());
                                float to = Float.parseFloat(userInfoBean.getResult().getOtherGift());
                                if (me == 0 && to == 0) {
                                    return;
                                }
                                pktv1.setText(((int) me) + "");
                                pktv2.setText(((int) to) + "");
                                if (pkv1 == 0 || pkv2 == 0) {
                                    return;
                                } else {
                                    if (isON) {
                                        isON = false;
                                        pktWidth = pkv1;
                                    }
                                    if (me > to) {

                                        float f1 = (float) Math.abs(((to - me) / me) * (pktWidth / 2.0));
                                        pkxuetiao((int) (pktWidth + f1), pkview1);
                                        pkxuetiao((int) (pktWidth - f1), pkview2);
                                        updataPKMessageToAll(me + "", to + "");
                                    } else if (me < to) {
                                        float f2 = (float) Math.abs((((me - to) / to) * (pktWidth / 2.0)));
                                        pkxuetiao((int) (pktWidth - f2), pkview1);
                                        pkxuetiao((int) (pktWidth + f2), pkview2);
                                        updataPKMessageToAll(me + "", to + "");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PRIVATE_CODE) {
            Log.d("MainActivity", "resultCode:" + resultCode);
            if (resultCode != 0) {
                ToastUtils.showInfo(ZhiBoActivity.this, "打开GPS失败");
            } else {
                showGPSContacts();
            }
        }
    }

    /**
     * 获取到当前位置的经纬度
     *
     * @param location
     */
    private void updateLocation(Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Log.e("MainActivity", "维度：" + latitude + "\n经度" + longitude);
            jd = longitude + "";
            wd = latitude + "";
        } else {
            ToastUtils.showInfo(ZhiBoActivity.this, "无法获取到位置信息");
            Log.e("MainActivity", "无法获取到位置信息");
        }
    }


    static final String[] LOCATIONGPS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE};
    private static final int BAIDU_READ_PHONE_STATE = 100;//定位权限请求
    private static final int PRIVATE_CODE = 1315;//开启GPS权限

    /**
     * 检测GPS、位置权限是否开启
     */
    public void showGPSContacts() {
        LocationManager lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (lm == null)
            return;
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (Build.VERSION.SDK_INT >= 23) { //判断是否为android6.0系统版本，如果是，需要动态添加权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PERMISSION_GRANTED) {// 没有权限，申请权限。
                    ActivityCompat.requestPermissions(this, LOCATIONGPS,
                            BAIDU_READ_PHONE_STATE);
                } else {
                    // getLocation();//getLocation为定位方法
                    initLocationOption();
                }
            } else {
                initLocationOption();
                //  getLocation();//getLocation为定位方法
            }
        } else {
            Toast.makeText(this, "系统检测到未开启GPS定位服务,请开启", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, PRIVATE_CODE);
        }
    }

    /**
     * Android6.0申请权限的回调方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // requestCode即所声明的权限获取码，在checkSelfPermission时传入
        if (requestCode == BAIDU_READ_PHONE_STATE) {//如果用户取消，permissions可能为null.
            if (grantResults[0] == PERMISSION_GRANTED) {  //有权限
                // 获取到权限，作相应处理
                //  getLocation();
                initLocationOption();
            } else {
                showGPSContacts();
            }
        }
    }

    /**
     * 获取具体位置的经纬度
     */
    private void getLocation() {
        // 获取位置管理服务
        LocationManager locationManager;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        /**这段代码不需要深究，是locationManager.getLastKnownLocation(provider)自动生成的，不加会出错**/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (provider != null) {
            Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
            updateLocation(location);
        }
    }


    private void link_loging(String path) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = null;
//        body = new FormBody.Builder()
//                .add("uname", uname)
//                .add("pwd", pwd)
//                .build();
//        JSONObject object=new JSONObject();
//        try {
//            object.put("uname",uname);
//            object.put("pwd",pwd);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        //RequestBody body = RequestBody.create(object.toString(),JSON);
        RequestBody fileBody = RequestBody.create(new File(path), MediaType.parse("application/octet-stream"));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("img", System.currentTimeMillis() + ".png", fileBody)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(requestBody)
                .url(Consts.URL + "/user/upload/img");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null)
                            dialog.dismiss();
                        ToastUtils.showError(ZhiBoActivity.this, "获取数据失败,请检查网络");
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null)
                            dialog.dismiss();
                    }
                });
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "上传图片:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
//                    Gson gson = new Gson();
//                    LogingBe logingBe = gson.fromJson(jsonObject, LogingBe.class);
                    fengmianPath = jsonObject.get("result").getAsString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fengmian.setImageBitmap(BitmapFactory.decodeFile(path));
                            if (photoDialog != null && !ZhiBoActivity.this.isFinishing())
                                photoDialog.dismiss();
                        }
                    });
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (dialog != null)
                                dialog.dismiss();
                            ToastUtils.showError(ZhiBoActivity.this, e.getMessage() + "");
                        }
                    });
                }
            }
        });
    }
//    {
//        "coverImg": "string",
//            "latitude": 0,
//            "longitude": 0,
//            "title": "string",
//            "type": 0
//    }

    private void link_kaibo() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object = new JSONObject();
        try {
            object.put("coverImg", fengmianPath);
            object.put("latitude", wd);
            object.put("longitude", jd);
            object.put("title", zhuti.getText().toString().trim());
            object.put("type", liveType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        try {
//            object.put("coverImg","h");
//            object.put("latitude",0);
//            object.put("longitude",0);
//            object.put("title","测试");
//            object.put("type","1");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        Log.d("KaiBoActivity", "liveType:" + object.toString());
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/live/start");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(ZhiBoActivity.this, "获取数据失败,请检查网络");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog_kaibo != null)
                            dialog_kaibo.dismiss();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "开播:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    KaiBoBean bean = gson.fromJson(jsonObject, KaiBoBean.class);
                    if (bean.getCode() == 2000) {
                        BaoCunBean baoCunBean = MyApplication.myApplication.getBaoCunBeanBox().get(123456);
                        if (baoCunBean != null) {
                            baoCunBean.setRoomId(bean.getResult().getRoomId());
                            baoCunBean.setPushUrl(bean.getResult().getPushUrl());
                            baoCunBean.setPlayUrl(bean.getResult().getPlayUrl());
                            baoCunBean.setPlaySafeUrl(bean.getResult().getPlaySafeUrl());
                            MyApplication.myApplication.getBaoCunBeanBox().put(baoCunBean);
                            LoginInfo loginInfo = new LoginInfo(Integer.parseInt(baoCunBean.getSdkAppId()), baoCunBean.getUserid() + "", baoCunBean.getNickname(), baoCunBean.getHeadImage(), baoCunBean.getImUserSig());
                            MLVBLiveRoom.sharedInstance(MyApplication.myApplication).login(loginInfo, new LoginCallback() {
                                @Override
                                public void onError(int errCode, String errInfo) {
                                    ToastUtils.showInfo(ZhiBoActivity.this, "IM登录失败");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (dialog_kaibo != null)
                                                dialog_kaibo.dismiss();
                                        }
                                    });
                                }
                                @Override
                                public void onSuccess() {
                                    mlvbLiveRoom.createRoom(baoCunBean.getUserid() + "", "", baoCunBean.getPushUrl(), new CreateRoomCallback() {
                                        @Override
                                        public void onError(int errCode, String errInfo) {
                                            Log.d("ZhiBoActivity", "errCode:" + errCode);
                                            Log.d("ZhiBoActivity", "errInfo:" + errInfo);
                                            ToastUtils.showInfo(ZhiBoActivity.this, "创建房间失败,请退出后重试");
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (dialog_kaibo != null)
                                                        dialog_kaibo.dismiss();
                                                }
                                            });
                                        }
                                        @Override
                                        public void onSuccess(String RoomID) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (dialog_kaibo != null)
                                                        dialog_kaibo.dismiss();
                                                }
                                            });
                                            Log.d("ZhiBoActivity", "创建房间成功" );
                                            runOnUiThread(new Runnable() {//这里是推流成功回调
                                                @Override
                                                public void run() {
                                                    group1.setVisibility(View.GONE);
                                                    group2.setVisibility(View.VISIBLE);
                                                }
                                            });
                                            Log.d("ZhiBoActivity", RoomID + "创建成功");
                                            LiaoTianBean bean = new LiaoTianBean();
                                            bean.setType(2);
                                            bean.setNickname("");
                                            bean.setNeirong(" 蓝鲸倡导绿色直播环境，对直播内容会24小时巡查，封面和直播内容有任何违法违规、色情暴力、抹黑诋毁、低俗不良行为将被禁封。传播正能量，从自我做起！");
                                            liaoTianBeanList.add(bean);
                                            liaoTianAdapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                            });
                        }
                    } else if (bean.getCode() == -2) {
                        ToastUtils.showError(ZhiBoActivity.this, "你还未认证成主播,请先认证");
                        startActivity(new Intent(ZhiBoActivity.this, ZhengJianXinXiActivity.class));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (dialog_kaibo != null)
                                    dialog_kaibo.dismiss();
                            }
                        });
                    } else {
                        ToastUtils.showError(ZhiBoActivity.this, bean.getDesc());
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(ZhiBoActivity.this, "获取数据失败");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (dialog_kaibo != null)
                                dialog_kaibo.dismiss();
                        }
                    });

                }
            }
        });
    }

    private void link_end() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object = new JSONObject();
        try {
            object.put("", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(object.toString(), JSON);
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
                // ToastUtils.showError(KaiBoActivity.this, "获取数据失败,请检查网络");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        kaibo.setEnabled(true);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        kaibo.setEnabled(true);
                    }
                });
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "结束直播:" + ss);
                    link_kaibo();
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    // ToastUtils.showError(KaiBoActivity.this, "获取数据失败");
                }
            }
        });
    }


    /**
     * 初始化定位参数配置
     */


    private void initLocationOption() {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        locationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
        MyLocationListener myLocationListener = new MyLocationListener();
        //注册监听函数
        locationClient.registerLocationListener(myLocationListener);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("gcj02");
        //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        // locationOption.setScanSpan(0);
        //可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
        //可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
        //可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(false);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
        //可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
        //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        // locationOption.setOpenAutoNotifyMode();
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        // locationOption.setOpenAutoNotifyMode(3000,1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.setLocOption(locationOption);
        //开始定位
        locationClient.start();

    }


    private class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            //获取纬度信息
            double latitude = location.getLatitude();
            //获取经度信息
            double longitude = location.getLongitude();
            int errorCode = location.getLocType();
            Log.d("MyLocationListener", "errorCode:" + errorCode);
            Log.d("MyLocationListener", "latitude纬度:" + latitude);
            Log.d("MyLocationListener", "longitude经度:" + longitude);

            if (errorCode == 161 || errorCode == 61) {
                jd = longitude + "";
                wd = latitude + "";
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        xianshi.setText(location.getAddress().city);
                    }
                });
                locationClient.stop();
            } else {
                count++;
                locationClient.restart();
                if (count >= 2) {
                    locationClient.stop();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            xianshi.setText("定位失败");
                        }
                    });
                    ToastUtils.showInfo(ZhiBoActivity.this, "定位失败");
                }
                return;
            }
            //获取定位精度，默认值为0.0f
            float radius = location.getRadius();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
        }
    }

    private void link_iszaixian(String toId) {//fromId自己的id
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL + "/anchor/islive/" + toId);
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
                    Log.d("ZhiBoActivity", "是否在线PK" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    //  Gson gson = new Gson();
                    // JieShuPkBean userInfoBean = gson.fromJson(jsonObject, JieShuPkBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("ZhiBoActivity", "dddd");//code=1是直播，0是未直播
                            try {
                                if (jsonObject != null && jsonObject.get("code") != null && jsonObject.get("code").getAsInt() == 0) {
                                    ToastUtils.showError(ZhiBoActivity.this, "主播已经下播");
                                    if (timer1 != null)
                                        timer1.cancel();
                                    timer1 = null;
                                    if (timer2 != null)
                                        timer2.cancel();
                                    timer2 = null;
                                    chengfa.setVisibility(View.GONE);
                                    pkjgim1.setVisibility(View.GONE);
                                    pkjgim2.setVisibility(View.GONE);
                                    group.setVisibility(View.GONE);
                                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) txCloudVideoView.getLayoutParams();
                                    params.height = hight;
                                    params.width = width;
                                    txCloudVideoView.setLayoutParams(params);
                                    txCloudVideoView.invalidate();
                                    link_endmlx(anchorInfo.userID, baoCunBean.getUserid() + "");
                                    mlvbLiveRoom.stopRemoteView(anchorInfo);
                                    isPK = false;

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
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


    private void updataPKMessageToAll(String me, String to) {
        mlvbLiveRoom.sendRoomCustomMsg("updatapkall", me + "," + to, new SendRoomCustomMsgCallback() {
            @Override
            public void onError(int errCode, String errInfo) {

            }

            @Override
            public void onSuccess() {

            }
        });
    }

    private void startPKMessageToAll() {
        mlvbLiveRoom.sendRoomCustomMsg("startpkall", "", new SendRoomCustomMsgCallback() {
            @Override
            public void onError(int errCode, String errInfo) {

            }

            @Override
            public void onSuccess() {

            }
        });
    }


    private void zhongtuPKMessageToAll(String type, String time) {
        mlvbLiveRoom.sendRoomCustomMsg("zhongtupkall", type + "," + time, new SendRoomCustomMsgCallback() {
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
        params.width = width1;
        view.setLayoutParams(params);
        view.invalidate();
    }
}