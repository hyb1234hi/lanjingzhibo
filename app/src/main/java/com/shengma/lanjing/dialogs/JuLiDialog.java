package com.shengma.lanjing.dialogs;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.shengma.lanjing.R;



public class JuLiDialog extends DialogFragment implements View.OnClickListener {

    private Window window;
    private Button l1,l2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View  view = inflater.inflate(R.layout.juli_dialog, null);
        l1=view.findViewById(R.id.xianshi);
        l2=view.findViewById(R.id.yingcang);
        l1.setOnClickListener(this);
        l2.setOnClickListener(this);


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
            case R.id.xianshi:


                break;
            case R.id.yingcang:

                break;

       }
    }

//    @Override
//    public void show(FragmentManager manager, String tag) {
//        try {
//            Field mDismissed = this.getClass().getSuperclass().getDeclaredField("mDismissed");
//            Field mShownByMe = this.getClass().getSuperclass().getDeclaredField("mShownByMe");
//            mDismissed.setAccessible(true);
//            mShownByMe.setAccessible(true);
//            mDismissed.setBoolean(this, false);
//            mShownByMe.setBoolean(this, true);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        FragmentTransaction ft = manager.beginTransaction();
//        ft.add(this, tag);
//        ft.commitAllowingStateLoss();
//    }


}
