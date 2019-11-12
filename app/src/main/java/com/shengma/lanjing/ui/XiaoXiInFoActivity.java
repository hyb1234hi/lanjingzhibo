package com.shengma.lanjing.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shengma.lanjing.R;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XiaoXiInFoActivity extends AppCompatActivity {

    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.text)
    TextView text;
    private String tes="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiao_xi_in_fo);
        ButterKnife.bind(this);
        tes = getIntent().getStringExtra("info");
        if (tes==null){
            tes="";
        }
        RichText.from(tes).into(text);
    }

    @OnClick(R.id.fanhui)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        RichText.recycle();
        super.onDestroy();
    }
}
