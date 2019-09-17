package com.shengma.lanjing.ui.fargments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.adapters.XiaoXiAdapter;
import com.shengma.lanjing.beans.MyXiaoXiBean;
import com.shengma.lanjing.cookies.CookiesManager;
import com.shengma.lanjing.ui.XiTongXiaoXiActivity;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment {
    @BindView(R.id.tupian)
    ImageView tupian;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    private SwipeRefreshLayout swipeRefreshLayout;
    //private RecyclerView recyclerView;
    // private int pag=1;
    // private float jd=0,wd=0;
    private List<MyXiaoXiBean.ResultBean> beanList = new ArrayList<>();
  //  private XiaoXiAdapter adapter;
    private EditText sousuo;
    private TextView rrr;
    private Unbinder unbinder;

    public Fragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        unbinder = ButterKnife.bind(this, view);
        rrr = view.findViewById(R.id.rtrr);
        sousuo = view.findViewById(R.id.sousuo);
        swipeRefreshLayout = view.findViewById(R.id.refreshLayout);
        //recyclerView=view.findViewById(R.id.recyclerview);
        //设置进度View的组合颜色，在手指上下滑时使用第一个颜色，在刷新中，会一个个颜色进行切换
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#3EE1F7"), Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // pag+=1;
                link_list();

            }
        });
        if (getActivity()!=null)
        Glide.with(getActivity())
                .load(R.drawable.xitongxiaoxibg)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(tupian);

        //  GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        //设置布局管理器
        //   recyclerView.setLayoutManager(layoutManager);
        //设置Adapter
        //   adapter=new XiaoXiAdapter(beanList);
        //  recyclerView.setAdapter(adapter);
        //设置增加或删除条目的动画
        //  recyclerView.setItemAnimator( new DefaultItemAnimator());
//        int firstAndLastColumnW = DisplayUtils.dp2px(getActivity(), 16);
//        int firstRowTopMargin = DisplayUtils.dp2px(getActivity(), 16);
//        GridDividerItemDecoration gridDividerItemDecoration =
//                new GridDividerItemDecoration(getActivity(), firstAndLastColumnW, firstRowTopMargin, firstRowTopMargin);
//        gridDividerItemDecoration.setFirstRowTopMargin(firstRowTopMargin);
//        gridDividerItemDecoration.setLastRowBottomMargin(firstRowTopMargin);
//        recyclerView.addItemDecoration(gridDividerItemDecoration);
//        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Log.d(TAG, "position:" + position);
//            }
//        });

        link_list();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        rrr.setFocusableInTouchMode(true);//解决clearFocus无效
        sousuo.clearFocus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void link_list() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .get()///live/nearby?latitude=1&longitude=1&page=1&pageSize=10
                .url(Consts.URL + "/message");

        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity() != null)
                    ToastUtils.showError(getActivity(), "获取数据失败,请检查网络");
                if (getActivity() != null)
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
                if (getActivity() != null)
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
                    Log.d("LogingActivity", "消息列表" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    MyXiaoXiBean bean = gson.fromJson(jsonObject, MyXiaoXiBean.class);
                    if (bean.getCode() == 2000) {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    beanList.clear();
                                    beanList.addAll(bean.getResult());
                                    if (beanList.size()>0){
                                       title.setText(beanList.get(0).getContent());
                                       time.setText(beanList.get(beanList.size()-1).getCreateTime());
                                    }

                                }
                            });
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    if (getActivity() != null)
                        ToastUtils.showError(getActivity(), "获取数据失败");
                    if (getActivity() != null)
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

    @OnClick(R.id.rl1)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), XiTongXiaoXiActivity.class));
    }
}
