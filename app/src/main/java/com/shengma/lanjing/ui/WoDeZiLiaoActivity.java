package com.shengma.lanjing.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityConfig;
import com.lljjcoder.style.cityjd.JDCityPicker;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.beans.SaveBean;
import com.shengma.lanjing.dialogs.PhotoDialog;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WoDeZiLiaoActivity extends AppCompatActivity implements View.OnClickListener, OnDateSetListener {
    private PhotoDialog photoDialog;
    ZLoadingDialog dialog;
    private ImageView touxiang,fanhui;
    private RelativeLayout rl1,rl2,rl3,rl4;
    private String fengmianPath;
    private TextView name,xingbie,nianling,qianming,jiaxiang,bianji;
    private TimePickerDialog mDialogAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_zi_liao);
        EventBus.getDefault().register(this);
        BaoCunBean baoCunBean=MyApplication.myApplication.getBaoCunBean();
        fanhui=findViewById(R.id.fanhui);
        fanhui.setOnClickListener(this);
        touxiang=findViewById(R.id.touxiang);
        touxiang.setOnClickListener(this);
        rl1=findViewById(R.id.rl1);
        rl1.setOnClickListener(this);
        rl2=findViewById(R.id.rl2);
        rl2.setOnClickListener(this);
        rl3=findViewById(R.id.rl3);
        rl3.setOnClickListener(this);
        rl4=findViewById(R.id.rl4);
        rl4.setOnClickListener(this);
        xingbie=findViewById(R.id.xuanze1);
        nianling=findViewById(R.id.xuanze2);
        qianming=findViewById(R.id.xuanze3);
        jiaxiang=findViewById(R.id.xuanze4);
        bianji=findViewById(R.id.bianjiningcheng);bianji.setOnClickListener(this);
        name=findViewById(R.id.name);
        if (baoCunBean!=null){
            name.setText(baoCunBean.getNickname()+"");
            if (baoCunBean.getSex()!=0){
                if (baoCunBean.getSex()==1){
                    xingbie.setText("男");
                }else {
                    xingbie.setText("女");
                }
            }
            if (baoCunBean.getAge()!=0)
            nianling.setText(baoCunBean.getAge()+"");
            if (baoCunBean.getQianming()!=null)
            qianming.setText(baoCunBean.getQianming());
            if (baoCunBean.getJiaxiang()!=null)
            jiaxiang.setText(baoCunBean.getJiaxiang()+"");
            Glide.with(WoDeZiLiaoActivity.this)
                    .load(baoCunBean.getHeadImage())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(touxiang);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void photo(MsgWarp msgWarp) {
        if (msgWarp.getType() == 1003) {
            dialog = new ZLoadingDialog(WoDeZiLiaoActivity.this);
            dialog.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)//设置类型
                    .setLoadingColor(Color.parseColor("#FF3EE1F7"))//颜色
                    .setHintText("加载中...")
                    .setHintTextSize(16) // 设置字体大小 dp
                    .setHintTextColor(Color.WHITE)  // 设置字体颜色
                    .setDurationTime(0.6) // 设置动画时间百分比 - 0.5倍
                    .setDialogBackgroundColor(Color.parseColor("#bb111111")) // 设置背景色，默认白色
                    .show();

            link_loging(msgWarp.getMsg());
            Log.d("KaiBoActivity", msgWarp.getMsg());
        }
    }


    @Override
    protected void onRestart() {
//        if (photoDialog != null)
//            photoDialog.dismiss();

        Log.d("KaiBoActivity", "onRestart");
        super.onRestart();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.touxiang:
                photoDialog = new PhotoDialog();
                photoDialog.show(getSupportFragmentManager(), "photodialog");
                break;
            case R.id.rl1:

                break;
            case R.id.rl2:
                //1442246400000
                long tenYears = 100L * 365 * 1000 * 60 * 60 * 24L;
                mDialogAll = new TimePickerDialog.Builder()
                        .setCallBack(WoDeZiLiaoActivity.this)
                        .setCancelStringId("取消")
                        .setSureStringId("确定")
                        .setTitleStringId("出生日期")
                        .setYearText("年")
                        .setMonthText("月")
                        .setDayText("日")
                        .setHourText("")
                        .setMinuteText("")
                        .setCyclic(true)
                        .setMinMillseconds(Long.parseLong(dateToStamp("1950-01-01")))
                        .setMaxMillseconds(System.currentTimeMillis())
                        .setCurrentMillseconds(System.currentTimeMillis())
                        .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                        .setType(Type.YEAR_MONTH_DAY)
                        .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                        .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                        .setWheelItemTextSize(14)
                        .build();
                mDialogAll.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.rl3:

                break;
            case R.id.rl4:
                JDCityPicker cityPicker = new JDCityPicker();
                JDCityConfig jdCityConfig = new JDCityConfig.Builder().build();

                jdCityConfig.setShowType(JDCityConfig.ShowType.PRO_CITY_DIS);
                cityPicker.init(this);
                cityPicker.setConfig(jdCityConfig);
                cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        jiaxiang.setText(city.getName());
                        link_save(city.getName(),"city");
                    }

                    @Override
                    public void onCancel() {
                    }
                });
                cityPicker.showCityPicker();
                break;
            case R.id.bianjiningcheng:

                break;
            case R.id.fanhui:
                finish();
                break;
        }
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s)  {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = Long.valueOf(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }



    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .post(requestBody)
                .url(Consts.URL + "/user/upload/img");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(WoDeZiLiaoActivity.this, "获取数据失败,请检查网络");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null)
                            dialog.dismiss();
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
                        if (photoDialog != null)
                            photoDialog.dismiss();
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
                    fengmianPath=jsonObject.get("result").getAsString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(WoDeZiLiaoActivity.this)
                                    .load(path)
                                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                    .into(touxiang);
                        }
                    });
                    link_save(fengmianPath,"headImage");

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(WoDeZiLiaoActivity.this, "获取数据失败");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (dialog != null)
                                dialog.dismiss();
                        }
                    });

                }
            }
        });
    }

    private void link_save(String string,String type) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        BaoCunBean bean=MyApplication.myApplication.getBaoCunBean();
        JSONObject object=new JSONObject();
            if (type.equals("headImage")){
                try {
                    if (bean.getJiaxiang()!=null)
                    object.put("city",bean.getJiaxiang());
                    object.put("headImage",string);
                    object.put("nickname",bean.getNickname()+"");
                    object.put("sex",bean.getSex());
                    if (bean.getQianming()!=null)
                    object.put("signature",bean.getQianming());
                    object.put("year",bean.getAge());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        if (type.equals("city")){
            try {

                object.put("city",string);
                if (bean.getHeadImage()!=null)
                object.put("headImage",bean.getHeadImage());
                object.put("nickname",bean.getNickname()+"");
                object.put("sex",bean.getSex());
                if (bean.getQianming()!=null)
                    object.put("signature",bean.getQianming());
                object.put("year",bean.getAge());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (type.equals("nickname")){
            try {
                if (bean.getJiaxiang()!=null)
                object.put("city",bean.getJiaxiang());
                if (bean.getHeadImage()!=null)
                    object.put("headImage",bean.getHeadImage());
                object.put("nickname",string);
                object.put("sex",bean.getSex());
                if (bean.getQianming()!=null)
                    object.put("signature",bean.getQianming());
                object.put("year",bean.getAge());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (type.equals("sex")){
            try {
                if (bean.getJiaxiang()!=null)
                    object.put("city",bean.getJiaxiang());
                if (bean.getHeadImage()!=null)
                    object.put("headImage",bean.getHeadImage());
                object.put("nickname",bean.getNickname()+"");
                object.put("sex",Integer.parseInt(string));
                if (bean.getQianming()!=null)
                    object.put("signature",bean.getQianming());
                object.put("year",bean.getAge());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (type.equals("signature")){
            try {
                if (bean.getJiaxiang()!=null)
                    object.put("city",bean.getJiaxiang());
                if (bean.getHeadImage()!=null)
                    object.put("headImage",bean.getHeadImage());
                object.put("nickname",bean.getNickname()+"");
                object.put("sex",bean.getSex());
                object.put("signature",string);
                object.put("year",bean.getAge());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (type.equals("year")){
            try {
                if (bean.getJiaxiang()!=null)
                    object.put("city",bean.getJiaxiang());
                if (bean.getHeadImage()!=null)
                    object.put("headImage",bean.getHeadImage());
                object.put("nickname",bean.getNickname()+"");
                object.put("sex",bean.getSex());
                if (bean.getQianming()!=null)
                object.put("signature",bean.getQianming());
                object.put("year",Integer.parseInt(string));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        RequestBody body = RequestBody.create(object.toString(),JSON);
    //    RequestBody fileBody = RequestBody.create(new File(path), MediaType.parse("application/octet-stream"));
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("img", System.currentTimeMillis() + ".png", fileBody)
//                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/user/save");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(WoDeZiLiaoActivity.this, "获取数据失败,请检查网络");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "更新信息:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    SaveBean saveBean = gson.fromJson(jsonObject, SaveBean.class);
                    BaoCunBean baoCunBean=MyApplication.myApplication.getBaoCunBean();
                    if (baoCunBean!=null){
                        baoCunBean.setAge(saveBean.getResult().getYear());
                        baoCunBean.setNickname(saveBean.getResult().getNickname());
                        baoCunBean.setSex(saveBean.getResult().getSex());
                        baoCunBean.setQianming(saveBean.getResult().getSignature());
                        baoCunBean.setJiaxiang(saveBean.getResult().getCity());
                        baoCunBean.setHeadImage(saveBean.getResult().getHeadImage());
                        MyApplication.myApplication.getBaoCunBeanBox().put(baoCunBean);
                      //  Log.d("WoDeZiLiaoActivity", MyApplication.myApplication.getBaoCunBean().getHeadImage());
                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(WoDeZiLiaoActivity.this, "获取数据失败");

                }
            }
        });
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
       Log.d( "dddd",timePickerView.getCurrentMillSeconds()+"");
        Log.d( "dddd",millseconds+"");
        Log.d("fffff",stampToDate(millseconds+""));
    }
}
