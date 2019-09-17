package com.shengma.lanjing.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.adapters.WoDefensiAdapter;
import com.shengma.lanjing.adapters.ZHiBoShiChangAdapter;
import com.shengma.lanjing.beans.FenSiBean;
import com.shengma.lanjing.beans.ZhiBoSHiChangBean;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WoDeFenSiActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private int pag=1;
    private List<FenSiBean.ResultBean> beanList=new ArrayList<>();
    private WoDefensiAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_fen_si);

        ImageView imageView=findViewById(R.id.fanhui);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        swipeRefreshLayout=findViewById(R.id.refreshLayout);
        recyclerView=findViewById(R.id.recyclerview);
        //设置进度View的组合颜色，在手指上下滑时使用第一个颜色，在刷新中，会一个个颜色进行切换
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#3EE1F7"), Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pag+=1;
                link_list();
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(WoDeFenSiActivity.this, LinearLayoutManager.VERTICAL,false);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置Adapter
        adapter=new WoDefensiAdapter(beanList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "position:" + position);

            }
        });

        link_list();

    }

    private void link_list() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL+"/user/fans?page="+pag+"&pageSize=10");

        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(WoDeFenSiActivity.this,"获取数据失败,请检查网络");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LogingActivity", "直播时长"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    FenSiBean bean = gson.fromJson(jsonObject, FenSiBean.class);
                    if (bean.getCode()==2000) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                beanList.addAll(bean.getResult());
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(WoDeFenSiActivity.this,"获取数据失败");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });
    }

}
