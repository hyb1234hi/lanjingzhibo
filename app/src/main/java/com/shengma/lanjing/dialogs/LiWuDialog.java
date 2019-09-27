package com.shengma.lanjing.dialogs;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.adapters.GridViewAdapter;
import com.shengma.lanjing.adapters.GridViewAdapter2;
import com.shengma.lanjing.beans.BeiBaoBean;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.beans.QianBaoBean;
import com.shengma.lanjing.beans.XiaZaiLiWuBean;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import com.shengma.lanjing.views.LiWuViewPagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class LiWuDialog extends DialogFragment {

    @BindView(R.id.jingdian)
    TextView jingdian;
    @BindView(R.id.jingdianview)
    View jingdianview;
    @BindView(R.id.beibao)
    TextView beibao;
    @BindView(R.id.beibaoview)
    View beibaoview;
    @BindView(R.id.viewpage)
    ViewPager viewpage;
    @BindView(R.id.viewpage2)
    ViewPager viewpage2;
    @BindView(R.id.yue)
    TextView yue;
    @BindView(R.id.chongzhi)
    TextView chongzhi;
    @BindView(R.id.fasong)
    TextView fasong;
    private Window window;
    private Unbinder unbinder;
    private List<View> mViewPagerGridList;
    private List<View> mViewPagerGridList2;
    private List<XiaZaiLiWuBean> mDatas = new ArrayList<>();
    private List<XiaZaiLiWuBean> mDatas2 = new ArrayList<>();
    private GridViewAdapter adapter;
    private GridViewAdapter2 adapter2;
    private LayoutInflater inflate;
    private List<GridViewAdapter> gridViewAdapters = new ArrayList<>();
    private List<GridViewAdapter2> gridViewAdapters2 = new ArrayList<>();
    private String id;

    public LiWuDialog(String id) {
        this.id=id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.liwu_dialog, null);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, view);
        this.inflate = inflater;
//        mViewPagerGridList = new ArrayList<View>();
//           //塞入GridView：
//          //计算每页最大显示个数
//        int pageSize = 4 * 2;
//        //一共的页数等于 总数/每页数量，并取整。
//        int pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
//        Log.i("TAG", "mDatas.size()*1.0/pageSize:" + (mDatas.size() * 1.0 / pageSize));
//
//        for (int index = 0; index < pageCount; index++) {
//            //每个页面都是inflate出一个新实例
//            GridView grid = (GridView) inflater.inflate(R.layout.item_viewpager, null, false);
//            //给GridView设置Adapter，传入index
//            adapter=new GridViewAdapter(getActivity(), mDatas, index);
//            grid.setAdapter(adapter);
//            //加入到ViewPager的View数据集中
//            mViewPagerGridList.add(grid);
//        }
//        viewpage.setAdapter(new LiWuViewPagerAdapter(mViewPagerGridList));
        viewpage.setOffscreenPageLimit(3);
        List<XiaZaiLiWuBean> xiaZaiLiWuBeanList=MyApplication.myApplication.getXiaZaiLiWuBeanBox().getAll();
        mDatas.addAll(xiaZaiLiWuBeanList);
        mViewPagerGridList = new ArrayList<View>();
        //塞入GridView：
        //计算每页最大显示个数
        int pageSize = 4 * 2;
        //一共的页数等于 总数/每页数量，并取整。
        int pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        Log.i("TAG", "mDatas.size()*1.0/pageSize:" + (mDatas.size() * 1.0 / pageSize));

        for (int index = 0; index < pageCount; index++) {
            //每个页面都是inflate出一个新实例
            GridView grid = (GridView) inflate.inflate(R.layout.item_viewpager, null, false);
            grid.setNumColumns(4);
            //给GridView设置Adapter，传入index
            adapter = new GridViewAdapter(getActivity(), mDatas, index);
            gridViewAdapters.add(adapter);
            grid.setAdapter(adapter);
            //加入到ViewPager的View数据集中
            mViewPagerGridList.add(grid);
        }
        viewpage.setAdapter(new LiWuViewPagerAdapter(mViewPagerGridList));
        /////////////////
        link_beibao();
        link_qianbao();
      //  link_userinfo();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(MsgWarp msgWarp) {

        if (msgWarp.getType() == 1009) {
            int size = mDatas.size();
            for (int i = 0; i < size; i++) {
                if (Integer.parseInt(msgWarp.getMsg()) == i) {
                    mDatas.get(i).setA(true);
                } else {
                    mDatas.get(i).setA(false);
                }
            }
            for (GridViewAdapter a : gridViewAdapters) {
                a.notifyDataSetChanged();
            }

        }
        if (msgWarp.getType() == 2009) {
            int size = mDatas2.size();
            for (int i = 0; i < size; i++) {
                if (Integer.parseInt(msgWarp.getMsg()) == i) {
                    mDatas2.get(i).setA(true);
                } else {
                    mDatas2.get(i).setA(false);
                }
            }
            for (GridViewAdapter2 a : gridViewAdapters2) {
                a.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // 下面这些设置必须在此方法(onStart())中才有效
        window = getDialog().getWindow();
        if (window != null) {
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


    @OnClick({R.id.jingdian, R.id.beibao, R.id.chongzhi,R.id.fasong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jingdian:
                jingdianview.setVisibility(View.VISIBLE);
                jingdian.setTextColor(Color.parseColor("#FFFFFF"));
                beibaoview.setVisibility(View.GONE);
                beibao.setTextColor(Color.parseColor("#9E9F9F"));
                fasong.setVisibility(View.VISIBLE);
                chongzhi.setVisibility(View.GONE);
                viewpage.setVisibility(View.VISIBLE);
                viewpage2.setVisibility(View.GONE);
                break;
            case R.id.beibao:
                beibaoview.setVisibility(View.VISIBLE);
                beibao.setTextColor(Color.parseColor("#FFFFFF"));
                jingdianview.setVisibility(View.GONE);
                jingdian.setTextColor(Color.parseColor("#9E9F9F"));
                chongzhi.setVisibility(View.VISIBLE);
                fasong.setVisibility(View.GONE);
                viewpage.setVisibility(View.GONE);
                viewpage2.setVisibility(View.VISIBLE);

                break;
            case R.id.chongzhi:
                for (XiaZaiLiWuBean bean:mDatas2){
                    if (bean.isA()){
                        //发送
                        link_fasong2(bean.getId()+"",bean);
                        break;
                    }
                }
                dismiss();

                break;
            case R.id.fasong:
                for (XiaZaiLiWuBean bean:mDatas){
                    if (bean.isA()){
                        //发送
                    link_fasong(bean.getId()+"",bean);
                        break;
                    }
                }
                dismiss();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void link_fasong(String id2,XiaZaiLiWuBean bean) {
       // MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("anchorId", id)
                .add("giftId", id2)
                .build();
//        JSONObject object=new JSONObject();
//        try {
//            object.put("uname",uname);
//            object.put("pwd",pwd);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        //RequestBody body = RequestBody.create(object.toString(),JSON);
//        RequestBody fileBody = RequestBody.create(new File(path), MediaType.parse("application/octet-stream"));
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("img", System.currentTimeMillis() + ".png", fileBody)
//                .build();
       // Log.d("LiWuDialog", "anchorId"+id);
       // Log.d("LiWuDialog", "giftId"+id2);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/gift/send");
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
                    Log.d("AllConnects", "发送礼物:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    if (jsonObject.get("code").getAsInt()==1){
                        EventBus.getDefault().post(new MsgWarp(1100,bean.getId()+"",bean.getType()+""));
                    }else {
                        if (getActivity()!=null)
                        ToastUtils.showError(getActivity(), "赠送礼物失败");
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    // ToastUtils.showError(BoFangActivity.this, "获取数据失败");

                }
            }
        });
    }

    private void link_fasong2(String id2,XiaZaiLiWuBean bean) {
        // MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("anchorId", id)
                .add("giftId", id2)
                .build();
//        JSONObject object=new JSONObject();
//        try {
//            object.put("uname",uname);
//            object.put("pwd",pwd);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        //RequestBody body = RequestBody.create(object.toString(),JSON);
//        RequestBody fileBody = RequestBody.create(new File(path), MediaType.parse("application/octet-stream"));
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("img", System.currentTimeMillis() + ".png", fileBody)
//                .build();
        // Log.d("LiWuDialog", "anchorId"+id);
        // Log.d("LiWuDialog", "giftId"+id2);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/backpack/send");
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
                    Log.d("AllConnects", "发送礼物2:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    if (jsonObject.get("code").getAsInt()==1){
                        EventBus.getDefault().post(new MsgWarp(1100,bean.getId()+"",bean.getType()+""));
                    }else {
                        if (getActivity()!=null)
                            ToastUtils.showError(getActivity(), "赠送礼物失败");
                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    // ToastUtils.showError(BoFangActivity.this, "获取数据失败");

                }
            }
        });
    }


    private void link_beibao() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL + "/backpack?page=1&pageSize=100");
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
                    Log.d("AllConnects", "背包礼物:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    BeiBaoBean logingBe = gson.fromJson(jsonObject, BeiBaoBean.class);
                    if (logingBe.getCode()==2000) {
                        for (BeiBaoBean.ResultBean bean:logingBe.getResult()){
                            XiaZaiLiWuBean xiaZaiLiWuBean = new XiaZaiLiWuBean();
                            xiaZaiLiWuBean.setId(bean.getId());
                            xiaZaiLiWuBean.setType(bean.getType());
                            xiaZaiLiWuBean.setGiftUrl(bean.getGiftUrl());
                            xiaZaiLiWuBean.setNum(bean.getNum());
                            xiaZaiLiWuBean.setGiftName(bean.getName());
                            mDatas2.add(xiaZaiLiWuBean);
                        }
                        if (getActivity()!=null)
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                viewpage2.setOffscreenPageLimit(3);
                                mViewPagerGridList2 = new ArrayList<View>();
                                //塞入GridView：
                                //计算每页最大显示个数
                                int pageSize = 4 * 2;
                                //一共的页数等于 总数/每页数量，并取整。
                                int pageCount = (int) Math.ceil(mDatas2.size() * 1.0 / pageSize);
                                Log.i("TAG", "mDatas.size()*1.0/pageSize:" + (mDatas2.size() * 1.0 / pageSize));

                                for (int index = 0; index < pageCount; index++) {
                                    //每个页面都是inflate出一个新实例
                                    GridView grid = (GridView) inflate.inflate(R.layout.item_viewpager, null, false);
                                    grid.setNumColumns(4);
                                    //给GridView设置Adapter，传入index
                                    adapter2 = new GridViewAdapter2(getActivity(), mDatas2, index);
                                    gridViewAdapters2.add(adapter2);
                                    grid.setAdapter(adapter2);
                                    //加入到ViewPager的View数据集中
                                    mViewPagerGridList2.add(grid);
                                }
                                viewpage2.setAdapter(new LiWuViewPagerAdapter(mViewPagerGridList2));
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

    private void link_qianbao() {
        //  MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("", "")
                .build();
//        JSONObject object=new JSONObject();
//        try {
//            object.put("uname",uname);
//            object.put("pwd",pwd);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        //RequestBody body = RequestBody.create(object.toString(),JSON);
//        RequestBody fileBody = RequestBody.create(new File(path), MediaType.parse("application/octet-stream"));
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("img", System.currentTimeMillis() + ".png", fileBody)
//                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/user/wallet");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                // ToastUtils.showError(getActivity().this, "进房失败,请退出后重试");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "钱包:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    QianBaoBean logingBe = gson.fromJson(jsonObject, QianBaoBean.class);
                    if (logingBe.getCode() == 2000) {
                        if (getActivity()!=null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    yue.setText("余额:"+logingBe.getResult().getBalance());
                                }
                            });
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    //  ToastUtils.showError(BoFangActivity.this, "进房失败,请退出后重试");

                }
            }
        });
    }

}
