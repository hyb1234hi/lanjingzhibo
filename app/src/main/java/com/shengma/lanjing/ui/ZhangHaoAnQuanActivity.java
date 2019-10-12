package com.shengma.lanjing.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.beans.UserInfoBean;
import com.shengma.lanjing.liveroom.IMLVBLiveRoomListener;
import com.shengma.lanjing.liveroom.MLVBLiveRoom;
import com.shengma.lanjing.liveroom.roomutil.commondef.LoginInfo;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ZhangHaoAnQuanActivity extends AppCompatActivity {

    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.sfzbangding)
    TextView sfzbangding;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.zfbbangding)
    TextView zfbbangding;
    @BindView(R.id.rl3)
    RelativeLayout rl3;
    @BindView(R.id.wxbangding)
    TextView wxbangding;
    @BindView(R.id.rl4)
    RelativeLayout rl4;
    private BaoCunBean baoCunBean=MyApplication.myApplication.getBaoCunBean();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhang_hao_an_quan);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);


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

                  //  ToastUtils.showError(getActivity(), "获取数据失败,请检查网络");
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            baoCunBean=MyApplication.myApplication.getBaoCunBean();
                            if (baoCunBean!=null){
                                if (baoCunBean.getAuth()==1){
                                    rl2.setEnabled(false);
                                    rl2.setFocusable(false);
                                    sfzbangding.setText("待审核");
                                }
                                if (baoCunBean.getAuth()==3){
                                    sfzbangding.setText("未通过");
                                }
                                if (baoCunBean.getAuth()==2){
                                    sfzbangding.setText("通过认证");
                                }
                            }
                        }
                    });

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");

                }
            }
        });
    }

    @OnClick({R.id.fanhui, R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.rl1:
                startActivity(new Intent(ZhangHaoAnQuanActivity.this,XiuGaiMiMaActivity.class));
                break;
            case R.id.rl2:
                if (baoCunBean!=null){
                    if (baoCunBean.getAuth()==2){
                        startActivity(new Intent(ZhangHaoAnQuanActivity.this,ZhengJian2Activity.class));

                    }else {
                        startActivity(new Intent(ZhangHaoAnQuanActivity.this,ZhengJianXinXiActivity.class));
                    }
                 }
                break;
            case R.id.rl3:

                break;
            case R.id.rl4:

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void photo(MsgWarp msgWarp) {
        if (msgWarp.getType() == 3333) {//上传身份证之后发送审核状态广播
           rl2.setEnabled(false);
           rl2.setFocusable(false);
           sfzbangding.setText("待审核");
            Log.d("KaiBoActivity", msgWarp.getMsg());
        }


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
