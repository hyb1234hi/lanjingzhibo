package com.shengma.lanjing.ui.wuguanjingyao;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.shengma.lanjing.R;
import com.shengma.lanjing.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuanYuWoMenActivity extends AppCompatActivity {

    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.ggg)
    View ggg;
    @BindView(R.id.banbenhao)
    TextView banbenhao;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_yu_wo_men);
        ButterKnife.bind(this);
        //获取包管理器
        PackageManager pm = getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            //返回版本号
            banbenhao.setText(packageInfo.versionName+"");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }


    @OnClick({R.id.fanhui, R.id.rl2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.rl2:
                ToastUtils.showInfo(GuanYuWoMenActivity.this,"已经是最新版本");
                break;
        }
    }
}
