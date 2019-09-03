package com.example.myapplication2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogingActivity_zhuce extends AppCompatActivity {


    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.zhanghaomimadenglu)
    TextView zhanghaomimadenglu;
    @BindView(R.id.weixin)
    ImageView weixin;
    @BindView(R.id.yanzhengma)
    Button yanzhengma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging_zc);
        ButterKnife.bind(this);


    }


    @OnClick({R.id.zhanghaomimadenglu, R.id.weixin,R.id.yanzhengma})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zhanghaomimadenglu:

                finish();

                break;
            case R.id.weixin:


                break;
            case R.id.yanzhengma:
                Intent intent = new Intent(LogingActivity_zhuce.this,LogingActivity_yzm.class);
                intent.putExtra("phone","12222222");
                startActivity(intent);

                break;
        }
    }


}
