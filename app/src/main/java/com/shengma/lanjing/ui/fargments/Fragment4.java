package com.shengma.lanjing.ui.fargments;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.UserInfoBean;
import com.shengma.lanjing.dialogs.FenXiangDialog;
import com.shengma.lanjing.liveroom.IMLVBLiveRoomListener;
import com.shengma.lanjing.liveroom.MLVBLiveRoom;
import com.shengma.lanjing.liveroom.roomutil.commondef.LoginInfo;
import com.shengma.lanjing.mediarecorder.RecordActivity;
import com.shengma.lanjing.ui.GeXinSheZhiActivity;
import com.shengma.lanjing.ui.KaiBoActivity;
import com.shengma.lanjing.ui.QianBaoActivity;
import com.shengma.lanjing.ui.WoDeFenSiActivity;
import com.shengma.lanjing.ui.WoDeGuanZhuActivity;
import com.shengma.lanjing.ui.WoDeZhiBoActivity;
import com.shengma.lanjing.ui.WoDeZiLiaoActivity;
import com.shengma.lanjing.ui.wuguanjingyao.GuanYuWoMenActivity;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import com.shengma.lanjing.views.MyTopView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.objectbox.Box;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment {
    @BindView(R.id.mytopview)
    MyTopView mytopview;
    @BindView(R.id.touxiang)
    ImageView touxiang;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.dengji)
    TextView dengji;
    @BindView(R.id.dengji2)
    TextView dengji2;
    @BindView(R.id.myid)
    TextView myid;
    @BindView(R.id.textView1)
    LinearLayout textView1;
    @BindView(R.id.guanzhu)
    TextView guanzhu;
    @BindView(R.id.textView2)
    LinearLayout textView2;
    @BindView(R.id.fensi)
    TextView fensi;
    @BindView(R.id.textView3)
    LinearLayout textView3;
    @BindView(R.id.shichang)
    TextView shichang;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.rl3)
    RelativeLayout rl3;
    @BindView(R.id.rl4)
    RelativeLayout rl4;
    @BindView(R.id.rl5)
    RelativeLayout rl5;
    @BindView(R.id.bianji)
    ImageView bianji;
    @BindView(R.id.xingbie)
    ImageView xingbie;

    private Unbinder unbinder;
    private Box<BaoCunBean> baoCunBeanBox = MyApplication.myApplication.getBaoCunBeanBox();
    private BaoCunBean baoCunBean = null;

    public Fragment4() {
        baoCunBean = baoCunBeanBox.get(123456);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment4, container, false);
        unbinder = ButterKnife.bind(this, view);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        mytopview.setWH(widthPixels, heightPixels);
        Log.d("ZhiBoActivity", "heightPixels:" + heightPixels);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        baoCunBean = baoCunBeanBox.get(123456);
        if (baoCunBean != null) {
            if (getActivity() != null)
                Glide.with(getActivity())
                        .load(baoCunBean.getHeadImage())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(touxiang);
            //RequestOptions.bitmapTransform(new CircleCrop())//圆形
            //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
            name.setText(baoCunBean.getNickname() + "");
            dengji.setText("Lv." + baoCunBean.getUserLevel());
            dengji2.setText("Lv." + baoCunBean.getAnchorLevel());
            myid.setText("ID:" + baoCunBean.getUserid());
            shichang.setText(baoCunBean.getDuration() + "");
            fensi.setText(baoCunBean.getFans() + "");
            guanzhu.setText(baoCunBean.getIdols() + "");
            if (baoCunBean.getSex()==1){
                xingbie.setBackgroundResource(R.drawable.nan);
            }else if (baoCunBean.getSex()==2){
                xingbie.setBackgroundResource(R.drawable.nv);
            }else {
                xingbie.setVisibility(View.INVISIBLE);
            }
        }


        link_userinfo();
    }

    private void link_userinfo() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL + "/user/info");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity() != null)
                    ToastUtils.showError(getActivity(), "获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JsonObject jsonObject = null;
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LogingActivity", "用户信息" + ss);
                    jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    UserInfoBean userInfoBean = gson.fromJson(jsonObject, UserInfoBean.class);
                    Box<BaoCunBean> baoCunBeanBox = MyApplication.myApplication.getBaoCunBeanBox();
                    BaoCunBean bean = baoCunBeanBox.get(123456);
                    bean.setAnchorLevel(userInfoBean.getResult().getAnchorLevel());
                    bean.setAuth(userInfoBean.getResult().getAuth());
                    bean.setDuration(userInfoBean.getResult().getDuration());
                    bean.setFans(userInfoBean.getResult().getFans());
                    bean.setHeadImage(userInfoBean.getResult().getHeadImage());
                    bean.setIdNumber(userInfoBean.getResult().getIdNumber());
                    bean.setIdols(userInfoBean.getResult().getIdols());
                    bean.setNickname(userInfoBean.getResult().getNickname());
                    bean.setRealName(userInfoBean.getResult().getRealName());
                    bean.setSex(userInfoBean.getResult().getSex());
                    bean.setUserCode(userInfoBean.getResult().getUserCode());
                    bean.setUserid(userInfoBean.getResult().getId());
                    bean.setUserLevel(userInfoBean.getResult().getUserLevel());
                    baoCunBeanBox.put(bean);

                    LoginInfo loginInfo = new LoginInfo(Integer.parseInt(bean.getSdkAppId()), bean.getUserid() + "", bean.getNickname(), bean.getHeadImage(), bean.getImUserSig());
                    MLVBLiveRoom.sharedInstance(MyApplication.myApplication).setCameraMuteImage(BitmapFactory.decodeResource(getResources(), R.drawable.pause_publish));
                    //登录IM
                    MLVBLiveRoom.sharedInstance(MyApplication.myApplication).login(loginInfo, new IMLVBLiveRoomListener.LoginCallback() {
                        @Override
                        public void onError(int errCode, String errInfo) {
                            Log.d("ZhiBoActivity", "errCode:" + errCode);
                            Log.d("ZhiBoActivity", errInfo);
                        }

                        @Override
                        public void onSuccess() {
                            Log.d("ZhiBoActivity", "IM登录成功");


                        }
                    });

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    // ToastUtils.showError(MainActivity.this,"获取数据失败");
                    try {
                        if (jsonObject != null)
                            if (jsonObject.get("code").getAsInt() == 4401) {
                                if (getActivity() != null)
                                    ToastUtils.showInfo(getActivity(), "登录已过期,请重新登录");
                            }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4, R.id.rl5, R.id.textView1, R.id.textView2,
            R.id.textView3, R.id.bianji,R.id.rl6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textView1:
                startActivity(new Intent(getActivity(), WoDeZhiBoActivity.class));
                break;
            case R.id.textView2:
                startActivity(new Intent(getActivity(), WoDeGuanZhuActivity.class));
                break;
            case R.id.textView3:
                startActivity(new Intent(getActivity(), WoDeFenSiActivity.class));
                break;
            case R.id.rl1:
                startActivity(new Intent(getActivity(), QianBaoActivity.class));
                break;
            case R.id.rl2:
                startActivity(new Intent(getActivity(), KaiBoActivity.class));
                break;
            case R.id.rl3:
                startActivity(new Intent(getActivity(), RecordActivity.class));
                break;
            case R.id.rl4:
                startActivity(new Intent(getActivity(), GeXinSheZhiActivity.class));
                break;
            case R.id.rl5:
                FenXiangDialog dialog = new FenXiangDialog();
                dialog.show(getFragmentManager(), "fenxiang");
                break;
            case R.id.rl6:
                startActivity(new Intent(getActivity(), GuanYuWoMenActivity.class));
                break;
            case R.id.bianji:
                startActivity(new Intent(getActivity(), WoDeZiLiaoActivity.class));
                break;
        }
    }


}
