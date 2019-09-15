package com.shengma.lanjing.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;

import com.shengma.lanjing.beans.ChaXunBean;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;

import okhttp3.Request;

import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class PKDialog extends Dialog {

    private Button jujue,tongyi;
    private TextView name,title,dengji;
    private ImageView xingbie,touxiang;
    private String id;
    private Activity context;


    public PKDialog(Activity context,String id) {
        super(context, R.style.dialog_style2);
        setCustomDialog();
        this.id=id;
        this.context=context;
        link_get(id);
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.pk_dialog, null);
        jujue=mView.findViewById(R.id.jujue);
        tongyi=mView.findViewById(R.id.tongyi);
        name=mView.findViewById(R.id.name);
        title=mView.findViewById(R.id.title);
        dengji=mView.findViewById(R.id.dengji);
        xingbie=mView.findViewById(R.id.xingbie);
        touxiang=mView.findViewById(R.id.touxiang);
        super.setContentView(mView);
    }


    @Override
    public void setContentView(int layoutResID) {
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {
    }

    /**
     * 确定键监听器
     * @param listener
     */
    public void setOnQueRenListener(View.OnClickListener listener){
        jujue.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setQuXiaoListener(View.OnClickListener listener){
        tongyi.setOnClickListener(listener);
    }

    private void link_get(String id) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .get()
                .url(Consts.URL+"/anchor/info/"+id);
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(context,"获取数据失败,请检查网络");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    ChaXunBean logingBe = gson.fromJson(jsonObject, ChaXunBean.class);
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            name.setText(logingBe.getResult().getNickname()+"");
                            if (logingBe.getResult().getSex()==1){
                                xingbie.setBackgroundResource(R.drawable.nan);
                            }else {
                                xingbie.setBackgroundResource(R.drawable.nv);
                            }
                            dengji.setText("Lv."+logingBe.getResult().getAnchorLevel());
                            title.setText(logingBe.getResult().getNickname()+"请求与你PK");
                            Glide.with(context)
                                    .load(logingBe.getResult().getHeadImage())
                                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                    .into(touxiang);
                        }
                    });

                    Log.d("AllConnects", "查询信息:" + ss);
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(context,"获取数据失败");

                }
            }
        });
    }


}
