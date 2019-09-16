package com.shengma.lanjing.adapters;

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

        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }



}
