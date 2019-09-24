package com.shengma.lanjing.adapters;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.FuJinBean;


import java.util.List;

public class FuJinAdapter extends BaseQuickAdapter<FuJinBean.ResultBean,BaseViewHolder> {

    public FuJinAdapter(List<FuJinBean.ResultBean> list) {
        super(R.layout.fujing_item, list);
    }


    @Override
    protected void convert(BaseViewHolder helper, FuJinBean.ResultBean item) {
        helper.setText(R.id.title, item.getTitle());
        helper.setText(R.id.dengji, "Lv."+item.getAnchorLevel());
        helper.setText(R.id.juli, item.getDistance()+"Km");
        Glide.with(mContext)
                .load(item.getCoverImg())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners( 10)))
                .into((ImageView) helper.getView(R.id.bgbg));

        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }



}
