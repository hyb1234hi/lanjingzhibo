package com.shengma.lanjing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.shengma.lanjing.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhang_hao_an_quan);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.fanhui, R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.rl1:

                break;
            case R.id.rl2:

                break;
            case R.id.rl3:

                break;
            case R.id.rl4:

                break;
        }
    }
}
