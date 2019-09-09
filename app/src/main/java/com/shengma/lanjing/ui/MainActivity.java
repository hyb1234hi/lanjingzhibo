package com.shengma.lanjing.ui;


import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.UserInfoBean;
import com.shengma.lanjing.cookies.CookiesManager;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import com.shengma.lanjing.views.ControlScrollViewPager;
import com.shengma.lanjing.views.MyFragmentPagerAdapter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener , EasyPermissions.PermissionCallbacks {
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.ll3)
    LinearLayout ll3;
    @BindView(R.id.ll4)
    LinearLayout ll4;
    @BindView(R.id.im1)
    ImageView im1;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.im2)
    ImageView im2;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.im3)
    ImageView im3;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.im4)
    ImageView im4;
    @BindView(R.id.tv4)
    TextView tv4;

    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .writeTimeout(18000, TimeUnit.MILLISECONDS)
            .connectTimeout(18000, TimeUnit.MILLISECONDS)
            .readTimeout(18000, TimeUnit.MILLISECONDS)
            .cookieJar(new CookiesManager())
            //        .retryOnConnectionFailure(true)
            .build();

    private ControlScrollViewPager viewpage;
    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        link_userinfo();
        viewpage = findViewById(R.id.viewpage);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewpage.setAdapter(myFragmentPagerAdapter);
        //设置当前页的ID
        viewpage.setCurrentItem(0);
        //添加翻页监听事件
        viewpage.addOnPageChangeListener(this);
        viewpage.setOffscreenPageLimit(4);

        methodRequiresTwoPermission();

    }


    private final int RC_CAMERA_AND_LOCATION=10000;

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA,
                Manifest.permission.RECEIVE_BOOT_COMPLETED, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.INTERNET};

        if (EasyPermissions.hasPermissions(this, perms)) {
            // 已经得到许可，就去做吧 //第一次授权成功也会走这个方法
            Log.d("BaseActivity", "成功获得权限");


        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "需要授予app权限,请点击确定",
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        Log.d("BaseActivity", "list.size():" + list.size());

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
        for (String s:list){
            Log.d("BaseActivity", s);
        }
        Log.d("BaseActivity", "list.size():" + list.size());
        Toast.makeText(MainActivity.this,"权限被拒绝会导致无法正常使用app",Toast.LENGTH_LONG).show();
       // finish();

    }

    @OnClick({R.id.ll1, R.id.ll2, R.id.ll3, R.id.ll4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll1:
                resetSelected();
                tv1.setTextColor(Color.parseColor("#3EE1F7"));
                im1.setBackgroundResource(R.drawable.shouye1);
                viewpage.setCurrentItem(PAGE_ONE);
                break;
            case R.id.ll2:
                resetSelected();
                tv2.setTextColor(Color.parseColor("#F36D87"));
                im2.setBackgroundResource(R.drawable.fujin1);
                viewpage.setCurrentItem(PAGE_TWO);
                break;
            case R.id.ll3:
                resetSelected();
                tv3.setTextColor(Color.parseColor("#FB9210"));
                im3.setBackgroundResource(R.drawable.xiaoxi1);
                viewpage.setCurrentItem(PAGE_THREE);
                break;
            case R.id.ll4:
                resetSelected();
                tv4.setTextColor(Color.parseColor("#9327F5"));
                im4.setBackgroundResource(R.drawable.wode1);
                viewpage.setCurrentItem(PAGE_FOUR);
                break;
        }
    }

    private void link_userinfo() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .get()
                .url(Consts.URL+"/user/info");
        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(MainActivity.this,"获取数据失败,请检查网络");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LogingActivity", "用户信息"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
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

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(MainActivity.this,"获取数据失败");

                }
            }
        });
    }

    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (viewpage.getCurrentItem()) {
                case PAGE_ONE: {
                    resetSelected();
                    tv1.setTextColor(Color.parseColor("#3EE1F7"));
                    im1.setBackgroundResource(R.drawable.shouye1);
                    break;
                }
                case PAGE_TWO: {
                    resetSelected();
                    tv2.setTextColor(Color.parseColor("#F36D87"));
                    im2.setBackgroundResource(R.drawable.fujin1);
                    break;
                }
                case PAGE_THREE: {
                    resetSelected();
                    tv3.setTextColor(Color.parseColor("#FB9210"));
                    im3.setBackgroundResource(R.drawable.xiaoxi1);
                    break;
                }
                case PAGE_FOUR: {
                    resetSelected();
                    tv4.setTextColor(Color.parseColor("#9327F5"));
                    im4.setBackgroundResource(R.drawable.wode1);
                    break;
                }
            }
        }
    }

    private void resetSelected(){
        tv1.setTextColor(Color.parseColor("#999999"));
        tv2.setTextColor(Color.parseColor("#999999"));
        tv3.setTextColor(Color.parseColor("#999999"));
        tv4.setTextColor(Color.parseColor("#999999"));
        im1.setBackgroundResource(R.drawable.shouye2);
        im2.setBackgroundResource(R.drawable.fujin2);
        im3.setBackgroundResource(R.drawable.xiaoxi2);
        im4.setBackgroundResource(R.drawable.wode2);
    }

}
