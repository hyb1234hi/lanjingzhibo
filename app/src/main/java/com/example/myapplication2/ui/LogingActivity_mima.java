package com.example.myapplication2.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LogingActivity_mima extends AppCompatActivity {


    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.mima1)
    EditText mima1;
    @BindView(R.id.mima2)
    EditText mima2;
    @BindView(R.id.denglu)
    Button denglu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging_mima);
        ButterKnife.bind(this);


    }


    @OnClick({R.id.fanhui, R.id.denglu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.denglu:


                break;
        }
    }
}
