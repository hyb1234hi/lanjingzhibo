package com.shengma.lanjing.dialogs;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.fragment.app.DialogFragment;

import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.liveroom.MLVBLiveRoom;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MeiYanDialog extends DialogFragment {

    @BindView(R.id.guanbi)
    ImageView guanbi;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.progressBar)
    AppCompatSeekBar progressBar;
    @BindView(R.id.meiyan)
    TextView meiyan;
    @BindView(R.id.meibai)
    TextView meibai;
    @BindView(R.id.hongrun)
    TextView hongrun;
    private Window window;
    private Unbinder unbinder;
    private int type=1;
    private BaoCunBean baoCunBean = MyApplication.myApplication.getBaoCunBean();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.meiyan_dialog, null);
        unbinder = ButterKnife.bind(this, view);
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d("MeiYanDialog", "i:" + i);
                if (baoCunBean!=null){
                    if (type==1){
                        baoCunBean.setMeiyan(i);
                        MyApplication.myApplication.getBaoCunBeanBox().put(baoCunBean);
                        MLVBLiveRoom.sharedInstance(MyApplication.myApplication).setBeautyStyle(0,i,baoCunBean.getMeibai(),baoCunBean.getHongrun());
                    }else if (type==2){
                        baoCunBean.setMeibai(i);
                        MyApplication.myApplication.getBaoCunBeanBox().put(baoCunBean);
                        MLVBLiveRoom.sharedInstance(MyApplication.myApplication).setBeautyStyle(0,baoCunBean.getMeiyan(),i,baoCunBean.getHongrun());
                    }else {
                        baoCunBean.setHongrun(i);
                        MyApplication.myApplication.getBaoCunBeanBox().put(baoCunBean);
                        MLVBLiveRoom.sharedInstance(MyApplication.myApplication).setBeautyStyle(0,baoCunBean.getMeiyan(),baoCunBean.getMeibai(),i);
                    }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        progressBar.setProgress(baoCunBean.getMeiyan());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 下面这些设置必须在此方法(onStart())中才有效
        window = getDialog().getWindow();
        if (window != null) {
            // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
            window.setBackgroundDrawableResource(android.R.color.transparent);
            // 设置动画
            window.setWindowAnimations(R.style.bottomDialog);
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM;
            // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
            params.width = getResources().getDisplayMetrics().widthPixels;
            window.setAttributes(params);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.guanbi, R.id.meiyan, R.id.meibai, R.id.hongrun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.guanbi:
                dismiss();
                break;
            case R.id.meiyan:
                type=1;
                title.setText("美颜");
                progressBar.setProgress(baoCunBean.getMeiyan());
                break;
            case R.id.meibai:
                type=2;
                title.setText("美白");
                progressBar.setProgress(baoCunBean.getMeibai());
                break;
            case R.id.hongrun:
                title.setText("红润");
                progressBar.setProgress(baoCunBean.getHongrun());
                type=3;
                break;
        }
    }
}
