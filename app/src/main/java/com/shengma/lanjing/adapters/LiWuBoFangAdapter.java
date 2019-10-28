package com.shengma.lanjing.adapters;

import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.LiWuBoFangBean;

import java.util.List;

public class LiWuBoFangAdapter extends BaseQuickAdapter<LiWuBoFangBean,BaseViewHolder> {

    public LiWuBoFangAdapter(List<LiWuBoFangBean> list) {
        super(R.layout.liwubofang_item, list);
    }


    @Override
    protected void convert(BaseViewHolder helper, LiWuBoFangBean item) {
        helper.setText(R.id.name, item.getName()+" 赠送了");
        helper.setText(R.id.liwuname, item.getLiwuName()+"");
        Glide.with(mContext)
                .load(item.getHeadImage())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into((ImageView) helper.getView(R.id.touxiang));

        Glide.with(mContext)
                .load(item.getLiwuPath())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners( 6)))
                .into((ImageView) helper.getView(R.id.liwuim));
        if (item.getNum()>1) {
            ValueAnimator animator = ValueAnimator.ofInt(1, item.getNum());
            Interpolator interpolator = new DecelerateInterpolator();
            animator.setInterpolator(interpolator);
            //如下传入多个参数，效果则为0->5,5->3,3->10
            //ValueAnimator animator = ValueAnimator.ofInt(0,5,3,10);
            //设置动画的基础属性
            animator.setDuration(1200);//播放时长
            // animator.setStartDelay(300);//延迟播放
            animator.setRepeatCount(0);//重放次数
            //  animator.setRepeatMode(ValueAnimator.RESTART);
            //重放模式
            //ValueAnimator.START：正序
            //ValueAnimator.REVERSE：倒序
            //设置更新监听
            //值 改变一次，该方法就执行一次
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //获取改变后的值
                    int currentValue = (int) animation.getAnimatedValue();
                    //输出改变后的值
                    //  Log.d("1111", "onAnimationUpdate: " + currentValue);
                    //改变后的值发赋值给对象的属性值
                    helper.setText(R.id.shuliang, "X"+currentValue );
                    //刷新视图
                    helper.itemView.requestLayout();
                }
            });
            animator.start();
        }else {
            helper.setText(R.id.shuliang,  "X1");
        }

        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角


    }



}
