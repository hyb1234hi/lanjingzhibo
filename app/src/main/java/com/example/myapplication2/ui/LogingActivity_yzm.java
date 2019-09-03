package com.example.myapplication2.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;
import com.example.myapplication2.views.PhoneCode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogingActivity_yzm extends AppCompatActivity {


    @BindView(R.id.yanzhengma)
    TextView yanzhengma;
    @BindView(R.id.yifasong)
    TextView yifasong;
    @BindView(R.id.pc_1)
    PhoneCode pc1;
    @BindView(R.id.fanhui)
    ImageView fanhui;
    private CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging_yzm);
        ButterKnife.bind(this);
        yifasong.setText("已发送到:" + getIntent().getStringExtra("phone"));
        pc1 = (PhoneCode) findViewById(R.id.pc_1);

        //注册事件回调（根据实际需要，可写，可不写）
        pc1.setOnInputListener(new PhoneCode.OnInputListener() {
            @Override
            public void onSucess(String code) {
                Log.d("LogingActivity_yzm", code);
                //输完后才会回调

            }

            @Override
            public void onInput() {
                Log.d("LogingActivity_yzm", "ddddd");
                //每次输入都会回调

            }
        });

        timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                yanzhengma.setEnabled(false);
                yanzhengma.setText((millisUntilFinished / 1000) + "秒后可重发");
            }

            @Override
            public void onFinish() {
                yanzhengma.setEnabled(true);
                yanzhengma.setText("重新获取验证码");
            }
        };
        timer.start();

    }


    @OnClick({R.id.yanzhengma,R.id.fanhui})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.yanzhengma) {

        }
        if (view.getId() == R.id.fanhui) {
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }



}
