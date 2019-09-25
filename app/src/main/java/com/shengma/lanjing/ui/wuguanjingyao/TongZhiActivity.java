package com.shengma.lanjing.ui.wuguanjingyao;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.shengma.lanjing.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TongZhiActivity extends AppCompatActivity {


    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.swich1)
    SwitchCompat swich1;
    @BindView(R.id.swich2)
    SwitchCompat swich2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tong_zhi);
        ButterKnife.bind(this);

        swich1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
        swich2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

    }

    @OnClick(R.id.fanhui)
    public void onViewClicked() {
        finish();
    }
}
