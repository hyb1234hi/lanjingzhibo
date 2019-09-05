package com.shengma.lanjing.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyTopView extends View {
    private Path path=null;
    private Paint paint=null;
    private int w,h;

    public MyTopView(Context context) {
        super(context);
        initView();
    }

    public MyTopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyTopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setWH(int w,int h){
        this.w=w;
        this.h=h;
    }

//    FillType.WINDING	取path所有所在区域 默认值
//    FillType.EVEN_ODD	取path所在并不相交区域
//    FillType.INVERSE_WINDING	取path所有未占区域
//    FillType.INVERSE_EVEN_ODD	取path未占或相交区域
//    LinearGradient 的3种模式：
//    CLAMP：当图片小于绘制尺寸时要进行边界拉伸来填充
//    REPEAT：当图片小于绘制尺寸时重复平铺
//    MIRROR：当图片小于绘制尺寸时镜像平铺

    private void initView(){
        path=new Path();
        path.setFillType(Path.FillType.WINDING);
        paint=new Paint();
        LinearGradient linearGradient=new LinearGradient(150,50,150,300,new int[]{
                Color.rgb(255,189,22),
                Color.rgb(221,43,6)},
                new float[]{0,1.0F}, Shader.TileMode.CLAMP);
        //new float[]{},中的数据表示相对位置，将150,50,150,300，划分10个单位，.3，.6，.9表示它的绝对位置。300到400，将直接画出rgb（0,232,210）
        paint.setShader(linearGradient);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.moveTo(0,0);
        path.lineTo(w,0);
        path.lineTo(w,h/6);
        path.lineTo(w-(w/7),(h/6)+(h/7));
        path.lineTo(w/7,(h/6)+(h/7));
        path.lineTo(0,h/6);
        path.lineTo(0,0);
        path.close();
        canvas.drawPath(path,paint);

    }



}
