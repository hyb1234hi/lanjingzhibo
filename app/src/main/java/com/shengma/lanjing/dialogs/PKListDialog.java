package com.shengma.lanjing.dialogs;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.adapters.GuanZhuAdapter;
import com.shengma.lanjing.adapters.PKAdapter;
import com.shengma.lanjing.beans.PaiHangListBean;
import com.shengma.lanjing.beans.ZaiXianZhuBo;
import com.shengma.lanjing.liveroom.roomutil.commondef.RoomInfo;
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


public class PKListDialog extends DialogFragment implements View.OnClickListener {

    private Window window;
    private TextView quxiao;
    private ImageView fanhui;
    private RecyclerView recyclerView;
    private EditText editText;
    private List<ZaiXianZhuBo.ResultBean> list=new ArrayList<>();
    private PKAdapter adapter=null;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View  view = inflater.inflate(R.layout.pklist_dialog, null);
        fanhui=view.findViewById(R.id.fanhui);
        fanhui.setOnClickListener(this);
        quxiao=view.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(this);
        recyclerView=view.findViewById(R.id.recyclerview);
        editText=view.findViewById(R.id.sousuo);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置Adapter
        adapter=new PKAdapter(list);
        recyclerView.setAdapter(adapter);
//        editText.setFocusable(true);
//        editText.setFocusableInTouchMode(true);
//        editText.requestFocus();

      //  quxiao.setFocusableInTouchMode(true);//解决clearFocus无效
      //  editText.clearFocus();
        link_u();
        return view;
    }

    private void link_u() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL + "/anchor/online?page=1&pageSize=50");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity()!=null)
                    ToastUtils.showError(getActivity(), "获取数据失败,请检查网络");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "获取pk主播" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    ZaiXianZhuBo logingBe = gson.fromJson(jsonObject, ZaiXianZhuBo.class);
                    if (logingBe.getCode()==2000){
                        if (getActivity()!=null){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    list.clear();
                                    long mtid=MyApplication.myApplication.getBaoCunBean().getUserid();
                                    for (ZaiXianZhuBo.ResultBean resultBean:logingBe.getResult()){
                                        if (mtid!=resultBean.getId()){
                                            list.add(resultBean);
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
                                    if (list.size()==0){
                                        if (getActivity()!=null)
                                            ToastUtils.showError(getActivity(), "暂无合适的主播");
                                    }
                                }
                            });
                        }
                    }else {
                        if (getActivity()!=null)
                        ToastUtils.showError(getActivity(), "暂无合适的主播");
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    if (getActivity()!=null)
                        ToastUtils.showError(getActivity(), "获取数据失败");
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                InputMethodManager inManager = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        },100);


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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.quxiao:

                break;
            case R.id.fanhui:
                dismiss();
                break;
        }
    }



}
