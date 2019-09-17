package com.shengma.lanjing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WoDeZhiBoActivity extends AppCompatActivity {

    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.touxiang)
    ImageView touxiang;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.dengji)
    TextView dengji;
    @BindView(R.id.shouru)
    TextView shouru;
    @BindView(R.id.rl1)
    RelativeLayout rl1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_zhi_bo);
        ButterKnife.bind(this);


    }

    @Override
    public void onResume() {
        super.onResume();
       BaoCunBean baoCunBean = MyApplication.myApplication.getBaoCunBeanBox().get(123456);
        if (baoCunBean != null) {

            Glide.with(WoDeZhiBoActivity.this)
                        .load(baoCunBean.getHeadImage())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(touxiang);
            //RequestOptions.bitmapTransform(new CircleCrop())//圆形
            //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
            name.setText(baoCunBean.getNickname() + "");
            dengji.setText("Lv." + baoCunBean.getAnchorLevel());

        }

    }


    @OnClick({R.id.fanhui, R.id.rl1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.rl1:
                startActivity(new Intent(WoDeZhiBoActivity.this,ZHiBoShiChangActivity.class));
                break;
        }
    }
}
