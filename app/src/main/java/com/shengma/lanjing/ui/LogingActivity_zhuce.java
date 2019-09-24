package com.shengma.lanjing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.MsgWarp;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogingActivity_zhuce extends AppCompatActivity {

    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.zhanghaomimadenglu)
    TextView zhanghaomimadenglu;
    //    @BindView(R.id.weixin)
//    ImageView weixin;
    @BindView(R.id.yanzhengma)
    Button yanzhengma;
    @BindView(R.id.rrr)
    TextView rrr;
//    @BindView(R.id.fanhui)
//    ImageView fanhui;
    private String wx = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging_zc);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        wx = getIntent().getStringExtra("wx");
        if (wx != null && wx.equals("wx")) {
            rrr.setText("微信绑定手机");
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(MsgWarp msgWarp) {

        if (msgWarp.getType() == 1002) {
            finish();
        }
    }

    @OnClick({R.id.zhanghaomimadenglu, R.id.yanzhengma})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zhanghaomimadenglu:
                finish();
                break;
//            case R.id.fanhui:
//
//                if (wx!=null&&wx.equals("wx")) {
//                    Intent intent = new Intent(LogingActivity_zhuce.this, MainActivity.class);
//                    startActivity(intent);
//                    EventBus.getDefault().post(new MsgWarp(1002, "关闭登录界面"));
//                } else {
//                    Log.d("LogingActivity_zhuce", "点点滴滴");
//                    finish();
//                }
//
//                break;
            case R.id.yanzhengma:
                Intent intent = new Intent(LogingActivity_zhuce.this, LogingActivity_yzm.class);
                intent.putExtra("phone", phone.getText().toString().trim());
                intent.putExtra("wx",wx);
                startActivity(intent);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
