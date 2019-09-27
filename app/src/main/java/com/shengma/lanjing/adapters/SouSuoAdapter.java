package com.shengma.lanjing.adapters;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.SouSuoBean;

import java.util.List;

public class SouSuoAdapter extends BaseQuickAdapter<SouSuoBean.ResultBean,BaseViewHolder> {

    public SouSuoAdapter(List<SouSuoBean.ResultBean> list) {
        super(R.layout.sousuo_item, list);
    }


    @Override
    protected void convert(BaseViewHolder helper, SouSuoBean.ResultBean item) {
        helper.setText(R.id.name, item.getNickname());
        helper.setText(R.id.idid, item.getId()+"");
        Glide.with(mContext)
                .load(item.getHeadImage())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into((ImageView) helper.getView(R.id.touxiang));
        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }



}
