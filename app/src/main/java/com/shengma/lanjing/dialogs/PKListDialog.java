package com.shengma.lanjing.dialogs;


import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shengma.lanjing.R;
import com.shengma.lanjing.adapters.GuanZhuAdapter;
import com.shengma.lanjing.adapters.PKAdapter;
import com.shengma.lanjing.liveroom.roomutil.commondef.RoomInfo;

import java.util.List;


public class PKListDialog extends DialogFragment implements View.OnClickListener {

    private Window window;
    private TextView quxiao;
    private ImageView fanhui;
    private RecyclerView recyclerView;
    private EditText editText;
    private List<RoomInfo> list;
    private PKAdapter adapter=null;


    public PKListDialog(List<RoomInfo> list) {
        this.list = list;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View  view = inflater.inflate(R.layout.pklist_dialog, null);
        fanhui=view.findViewById(R.id.fanhui);
        fanhui.setOnClickListener(this);
        quxiao=view.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(this);
        recyclerView=view.findViewById(R.id.recyclerview);
        editText=view.findViewById(R.id.sousuo);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置Adapter
        adapter=new PKAdapter(list);
        recyclerView.setAdapter(adapter);
//        editText.setFocusable(true);
//        editText.setFocusableInTouchMode(true);
//        editText.requestFocus();

      //  quxiao.setFocusableInTouchMode(true);//解决clearFocus无效
      //  editText.clearFocus();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                InputMethodManager inManager = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        },100);


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
            case R.id.quxiao:

                break;
            case R.id.fanhui:
                dismiss();
                break;
        }
    }



}
