package com.shengma.lanjing.ui.fargments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.adapters.ZHiBoAdapter;
import com.shengma.lanjing.beans.LiveType;
import com.shengma.lanjing.beans.ZhiBoBean;
import com.shengma.lanjing.cookies.CookiesManager;
import com.shengma.lanjing.ui.zhibo.BoFangActivity;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.DisplayUtils;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import com.shengma.lanjing.views.GridDividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class SYFragment2 extends Fragment implements View.OnClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .writeTimeout(18000, TimeUnit.MILLISECONDS)
            .connectTimeout(18000, TimeUnit.MILLISECONDS)
            .readTimeout(18000, TimeUnit.MILLISECONDS)
            .cookieJar(new CookiesManager())
            //        .retryOnConnectionFailure(true)
            .build();
    private int pag=1,type=0;
    private List<ZhiBoBean.ResultBean> beanList=new ArrayList<>();
    private ZHiBoAdapter adapter;
    private LinearLayout linearLayout;
    private List<LiveType.ResultBean> typeList=new ArrayList<>();


    public SYFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_syfragment2, container, false);
        swipeRefreshLayout=view.findViewById(R.id.refreshLayout);
        recyclerView=view.findViewById(R.id.recyclerview);
        linearLayout=view.findViewById(R.id.topll);
        //设置进度View的组合颜色，在手指上下滑时使用第一个颜色，在刷新中，会一个个颜色进行切换
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#3EE1F7"), Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pag+=1;
                link_list();
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置Adapter
        adapter=new ZHiBoAdapter(beanList);
        recyclerView.setAdapter(adapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        int firstAndLastColumnW = DisplayUtils.dp2px(getActivity(), 16);
        int firstRowTopMargin = DisplayUtils.dp2px(getActivity(), 16);
        GridDividerItemDecoration gridDividerItemDecoration =
                new GridDividerItemDecoration(getActivity(), firstAndLastColumnW, firstRowTopMargin, firstRowTopMargin);
        gridDividerItemDecoration.setFirstRowTopMargin(firstRowTopMargin);
        gridDividerItemDecoration.setLastRowBottomMargin(firstRowTopMargin);
        recyclerView.addItemDecoration(gridDividerItemDecoration);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "position:" + position);
                Intent intent=new Intent(getActivity(), BoFangActivity.class);
                intent.putExtra("idid",beanList.get(position).getId());
                intent.putExtra("playPath",beanList.get(position).getPlayUrl());
                startActivity(intent);
            }
        });


        link_live_type();

        return view;
    }


    private void link_list() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .get()//live/list?page=1&pageSize=10&type=0
                .url(Consts.URL+"/live/list?page="+pag+"&pageSize=10&type="+type);

        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity()!=null)
                    ToastUtils.showError(getActivity(),"获取数据失败,请检查网络");
                if (getActivity()!=null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                if (getActivity()!=null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LogingActivity", "关注列表"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    ZhiBoBean bean = gson.fromJson(jsonObject, ZhiBoBean.class);
                    if (bean.getCode()==2000) {
                        if (getActivity()!=null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    beanList.addAll(bean.getResult());
                                    adapter.notifyDataSetChanged();
                                }
                            });
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    if (getActivity()!=null)
                        ToastUtils.showError(getActivity(),"获取数据失败");
                    if (getActivity()!=null)
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                }
            }
        });
    }


    private void link_live_type() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL+"/live/type");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity()!=null)
                    ToastUtils.showError(getActivity(),"获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());

                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LogingActivity", "关注列表"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    LiveType bean = gson.fromJson(jsonObject, LiveType.class);
                    if (bean.getCode()==2000) {
                        typeList.clear();
                        typeList.addAll(bean.getResult());
                        if (getActivity()!=null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   int ss = bean.getResult().size();
                                   if (ss>0) {
                                       for (int i = 0; i < ss; i++) {
                                           View view_dk = View.inflate(getActivity(), R.layout.zbtitle_item, null);
                                           LinearLayout layout = view_dk.findViewById(R.id.root_layout);
                                           TextView textView = view_dk.findViewById(R.id.toptitle);
                                           textView.setText(bean.getResult().get(i).getName());
                                           view_dk.setOnClickListener(SYFragment2.this);
                                           if (i == 0) {
                                               textView.setTextColor(Color.parseColor("#FFF36D87"));
                                               textView.setBackgroundResource(R.drawable.yuanjiao_bg);
                                           } else {
                                               textView.setTextColor(Color.parseColor("#FF999999"));
                                               textView.setBackgroundColor(Color.TRANSPARENT);
                                           }
                                           view_dk.setTag(i);
                                           linearLayout.addView(view_dk);
                                       }
                                       link_list();
                                   }
                                }
                            });
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    if (getActivity()!=null)
                        ToastUtils.showError(getActivity(),"获取数据失败");

                }
            }
        });
    }


    @Override
    public void onClick(View view) {
       int size =  typeList.size();
       for (int i=0;i<size;i++){
          View view1= linearLayout.getChildAt(i);
          TextView textView= view1.findViewById(R.id.toptitle);
          if (view1.getTag().equals(view.getTag())){
              textView.setTextColor(Color.parseColor("#FFF36D87"));
              textView.setBackgroundResource(R.drawable.yuanjiao_bg);
          }else {
              textView.setTextColor(Color.parseColor("#FF999999"));
              textView.setBackgroundColor(Color.TRANSPARENT);
          }
       }
       type=Integer.parseInt(view.getTag().toString());
       beanList.clear();
       pag=1;
       link_list();

    }



}
