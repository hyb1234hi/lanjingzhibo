package com.shengma.lanjing.ui.wuguanjingyao;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import com.shengma.lanjing.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZhiBoJianSheZhiActivity extends AppCompatActivity {

    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.swich1)
    SwitchCompat swich1;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.swich2)
    SwitchCompat swich2;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.swich3)
    SwitchCompat swich3;
    @BindView(R.id.rl3)
    RelativeLayout rl3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_bo_jian_she_zhi);
        ButterKnife.bind(this);

        swich1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){


                }else {


                }
            }
        });
        swich2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){


                }else {


                }
            }
        });
        swich3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){


                }else {


                }
            }
        });
    }

    @OnClick({R.id.fanhui, R.id.rl1, R.id.rl2, R.id.rl3})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.fanhui) {
            finish();
        }
    }
}
