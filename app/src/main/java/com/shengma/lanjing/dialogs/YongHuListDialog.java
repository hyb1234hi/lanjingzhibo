package com.shengma.lanjing.dialogs;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.adapters.YongHuListAdapter;


import com.shengma.lanjing.beans.YongHuPaiHang;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class YongHuListDialog extends DialogFragment {

    private Window window;
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private YongHuListAdapter yongHuListAdapter;
    private List<YongHuPaiHang.ResultBean> yongHuListBeanList=new ArrayList<>();
   // private Box<YongHuListBean> yongHuListBeanBox= MyApplication.myApplication.getYongHuListBeanBox();
    private int size=20,page=0;
    private boolean isZB=false;
    private String zhuboid;

    public YongHuListDialog(String zhuboid) {
        this.zhuboid=zhuboid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View  view = inflater.inflate(R.layout.yonghu_dialog, null);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        ImageView fanhui=view.findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        yongHuListAdapter=new YongHuListAdapter(yongHuListBeanList);
        recyclerView.setAdapter(yongHuListAdapter);
//        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Log.d(TAG, "position:" + position);
//                if (isZB){//是主播才能点击
//                    YongHuXinxiDialog yongHuXinxiDialog=new YongHuXinxiDialog(zhuboid,yongHuListBeanList.get(position).getId()+"");
//                    yongHuXinxiDialog.show(getFragmentManager(),"yonghuxnxi");
//                    dismiss();
//                }
//            }
//        });
       // refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
               // page+=1;
               //link_chaxunGLY();
            }
        });
        link_chaxunGLY();
        return view;
    }

    public void setzhu(boolean isZB){
        this.isZB=isZB;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 下面这些设置必须在此方法(onStart())中才有效
        window = getDialog().getWindow();
        if (window!=null){
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


    private void link_chaxunGLY() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL + "/detail/user/rank?id="+zhuboid);
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                //  ToastUtils.showError(WoDeZiLiaoActivity.this, "获取数据失败,请检查网络");
                if (getActivity()!=null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.finishLoadMore(false/*,false*/);//传入false表示加载失败
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
                            refreshLayout.finishLoadMore(500/*,false*/);//传入false表示加载失败
                        }
                    });
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "查询是否管理员:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                      Gson gson = new Gson();//{"code":0,"desc":"不是管理员","total":0}
                    YongHuPaiHang yongHuPaiHang = gson.fromJson(jsonObject, YongHuPaiHang.class);
                    if (jsonObject.get("code").getAsInt()== 2000) {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    yongHuListBeanList.addAll(yongHuPaiHang.getResult());
                                    yongHuListAdapter.notifyDataSetChanged();
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


}
