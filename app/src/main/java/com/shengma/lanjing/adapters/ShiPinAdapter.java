package com.shengma.lanjing.adapters;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.ShiPingBean;


import java.util.List;

public class ShiPinAdapter extends BaseQuickAdapter<ShiPingBean.ResultBean,BaseViewHolder> {

    public ShiPinAdapter(List<ShiPingBean.ResultBean> list) {
        super(R.layout.shiping_item, list);
    }


    @Override
    protected void convert(BaseViewHolder helper, ShiPingBean.ResultBean item) {
        helper.setText(R.id.title, item.getTitle());
        helper.setText(R.id.dengji, "Lv."+item.getUserLevel());
        helper.setText(R.id.renshu, item.getNums()+"");
        Glide.with(mContext)
                .load(item.getImg())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners( 20)))
                .into((ImageView) helper.getView(R.id.bgbg));

        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }



}
