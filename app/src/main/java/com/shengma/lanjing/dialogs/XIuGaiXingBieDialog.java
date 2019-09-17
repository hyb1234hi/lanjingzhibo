package com.shengma.lanjing.dialogs;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.MsgWarp;


import org.greenrobot.eventbus.EventBus;


public class XIuGaiXingBieDialog extends DialogFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private Window window;
    private RadioGroup radioGroup_gender;
    private TextView queding,quxiao;
    private int sex=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View  view = inflater.inflate(R.layout.xingbie_dialog, null);
        radioGroup_gender=view.findViewById(R.id.radioGroup_gender);
        queding=view.findViewById(R.id.queding);
        queding.setOnClickListener(this);
        quxiao=view.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(this);
        radioGroup_gender.setOnCheckedChangeListener(this);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 下面这些设置必须在此方法(onStart())中才有效
        window = getDialog().getWindow();
        if (window!=null){
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.queding:
                EventBus.getDefault().post(new MsgWarp(1112,sex+""));
                dismiss();
                break;
            case R.id.quxiao:
                dismiss();
                break;
        }

       // dismiss();
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.nan:
                //当用户点击男性按钮时执行的代码
                System.out.println("===男性===");
                sex=1;
                break;
            case R.id.nv:
                //当用户点击女性按钮时执行的代码
                System.out.println("===女性===");
                sex=2;
                break;

        }
    }
}
