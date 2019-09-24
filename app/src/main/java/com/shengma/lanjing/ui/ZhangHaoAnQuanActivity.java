package com.shengma.lanjing.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.MsgWarp;



import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZhangHaoAnQuanActivity extends AppCompatActivity {

    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.sfzbangding)
    TextView sfzbangding;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.zfbbangding)
    TextView zfbbangding;
    @BindView(R.id.rl3)
    RelativeLayout rl3;
    @BindView(R.id.wxbangding)
    TextView wxbangding;
    @BindView(R.id.rl4)
    RelativeLayout rl4;
    private BaoCunBean baoCunBean=MyApplication.myApplication.getBaoCunBean();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhang_hao_an_quan);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        if (baoCunBean!=null){
            if (baoCunBean.getAuth()==1){
                rl2.setEnabled(false);
                rl2.setFocusable(false);
                sfzbangding.setText("待审核");
            }
            if (baoCunBean.getAuth()==3){
                sfzbangding.setText("未通过");
            }
            if (baoCunBean.getAuth()==2){
                sfzbangding.setText("通过认证");
            }

        }


    }

    @OnClick({R.id.fanhui, R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.rl1:
                startActivity(new Intent(ZhangHaoAnQuanActivity.this,XiuGaiMiMaActivity.class));
                break;
            case R.id.rl2:
                if (baoCunBean!=null){
                    if (baoCunBean.getAuth()==2){
                        startActivity(new Intent(ZhangHaoAnQuanActivity.this,ZhengJian2Activity.class));

                    }else {
                        startActivity(new Intent(ZhangHaoAnQuanActivity.this,ZhengJianXinXiActivity.class));
                    }
                 }

                break;
            case R.id.rl3:

                break;
            case R.id.rl4:

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void photo(MsgWarp msgWarp) {
        if (msgWarp.getType() == 3333) {//上传身份证之后发送审核状态广播
           rl2.setEnabled(false);
           rl2.setFocusable(false);
           sfzbangding.setText("待审核");
            Log.d("KaiBoActivity", msgWarp.getMsg());
        }


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
