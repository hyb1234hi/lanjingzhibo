package com.shengma.lanjing.ui;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.adapters.SouSuoAdapter;
import com.shengma.lanjing.beans.SouSuoBean;
import com.shengma.lanjing.ui.zhibo.BoFangActivity;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;



public class SouSuoActivity extends AppCompatActivity {

    @BindView(R.id.sousuo)
    EditText sousuo;
    @BindView(R.id.quxiao)
    TextView quxiao;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private SouSuoAdapter adapter;
    private List<SouSuoBean.ResultBean> resultBeans=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sou_suo);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(SouSuoActivity.this, LinearLayoutManager.VERTICAL,false);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置Adapter
        adapter=new SouSuoAdapter(resultBeans);
        recyclerView.setAdapter(adapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        sousuo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")){
                    link_list(charSequence.toString());
                }else {
                    resultBeans.clear();
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "position:" + position);
                if (resultBeans.get(position).getStatus()==1){//进入直播间
                    Intent intent=new Intent(SouSuoActivity.this, BoFangActivity.class);
                    intent.putExtra("idid",resultBeans.get(position).getId());
                    intent.putExtra("playPath",resultBeans.get(position).getPlayUrl());
                    startActivity(intent);
                    finish();
                }else {
                    ToastUtils.showInfo(SouSuoActivity.this,"该主播未开播");
                }
            }
        });


    }

    @OnClick(R.id.quxiao)
    public void onViewClicked() {
        finish();
    }


    private void link_list(String oo) {
        Log.d("SouSuoActivity", oo);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL+"/anchor?condition="+oo+"&page=1&pageSize=20");

        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(SouSuoActivity.this,"获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LogingActivity", "搜索列表"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    SouSuoBean bean = gson.fromJson(jsonObject, SouSuoBean.class);
                    if (bean.getCode()==2000) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resultBeans.clear();
                                if (bean.getResult().size()>0){
                                    resultBeans.addAll(bean.getResult());
                                    adapter.notifyDataSetChanged();
                                }else {
                                    ToastUtils.showInfo(SouSuoActivity.this,"未搜索到该主播");
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");

                    ToastUtils.showError(SouSuoActivity.this,"获取数据失败");

                }
            }
        });
    }

}
