package com.shengma.lanjing.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZhengJian2Activity extends AppCompatActivity {

    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.ggg)
    View ggg;
    @BindView(R.id.jk12)
    TextView jk12;
    @BindView(R.id.name2)
    EditText name2;
    @BindView(R.id.jk1)
    TextView jk1;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.jk2)
    TextView jk2;
    @BindView(R.id.haoma)
    EditText haoma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zheng_jian2);
        ButterKnife.bind(this);
        BaoCunBean baoCunBean= MyApplication.myApplication.getBaoCunBean();
        if (baoCunBean!=null){
            name.setText(baoCunBean.getRealName()+"");
            haoma.setText(baoCunBean.getIdNumber()+"");
        }

    }

    @OnClick(R.id.fanhui)
    public void onViewClicked() {
        finish();
    }
}
