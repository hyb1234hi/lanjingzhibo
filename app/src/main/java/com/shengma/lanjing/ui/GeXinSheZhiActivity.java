package com.shengma.lanjing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.shengma.lanjing.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GeXinSheZhiActivity extends AppCompatActivity {

    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.rl3)
    RelativeLayout rl3;
    @BindView(R.id.rl4)
    RelativeLayout rl4;
    @BindView(R.id.rl5)
    RelativeLayout rl5;
    @BindView(R.id.tuichu)
    LinearLayout tuichu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ge_xin_she_zhi);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.fanhui, R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4, R.id.rl5, R.id.tuichu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.rl1:
                startActivity(new Intent(GeXinSheZhiActivity.this,ZhangHaoAnQuanActivity.class));
                break;
            case R.id.rl2:

                break;
            case R.id.rl3:

                break;
            case R.id.rl4:

                break;
            case R.id.rl5:

                break;
            case R.id.tuichu:

                break;
        }
    }
}
