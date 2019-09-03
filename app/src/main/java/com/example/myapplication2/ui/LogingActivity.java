package com.example.myapplication2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

public class LogingActivity extends AppCompatActivity {


    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.yingcang)
    ImageView yingcang;
    @BindView(R.id.yanzhengmadenglu)
    TextView yanzhengmadenglu;
    @BindView(R.id.weixin)
    ImageView weixin;
    @BindView(R.id.denglu)
    Button denglu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging);
        ButterKnife.bind(this);



    }




    private boolean isC=false;

    @OnClick({R.id.yingcang, R.id.yanzhengmadenglu, R.id.weixin,R.id.denglu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.yingcang:
                if (!isC)  {
                    isC=true;
                    pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else {
                    isC=false;
                    pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                break;
            case R.id.yanzhengmadenglu:
                startActivity(new Intent(LogingActivity.this,LogingActivity_zhuce.class));

                break;
            case R.id.weixin:


                break;
            case R.id.denglu:

                startActivity(new Intent(LogingActivity.this, MainActivity.class));
                break;
        }
    }
}
