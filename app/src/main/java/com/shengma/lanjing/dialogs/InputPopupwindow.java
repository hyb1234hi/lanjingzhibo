package com.shengma.lanjing.dialogs;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.MsgWarp;

import org.greenrobot.eventbus.EventBus;

public class InputPopupwindow extends PopupWindow {
    private Activity context;
    private EditText editText;
    private Button button;

    public InputPopupwindow(Activity context) {
        super(context);
        init(context);
    }

    public InputPopupwindow(Activity context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Activity context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.widget_popupwindow, null);//alt+ctrl+f
        this.context = context;
        editText=view.findViewById(R.id.shuru);
        button=view.findViewById(R.id.fasong);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MsgWarp(1005,editText.getText().toString().trim()));
            }
        });
        initPopWindow(view);

    };
    private void initPopWindow(View view) {
        this.setContentView(view);
        // 设置弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
       // backgroundAlpha(context, 0.6f);//0.0-1.0
    }

    public EditText getEditText(){
        return editText;
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED); // 在显示输入法之后调用，否则popupwindow会在窗口底层
        super.showAtLocation(parent, gravity, x, y);

    }

    private void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }



}
