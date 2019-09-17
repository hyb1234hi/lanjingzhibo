package com.shengma.lanjing.adapters;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.FenSiBean;

import java.util.List;

public class WoDefensiAdapter extends BaseQuickAdapter<FenSiBean.ResultBean,BaseViewHolder> {

    public WoDefensiAdapter(List<FenSiBean.ResultBean> list) {
        super(R.layout.wodefensi_item, list);
    }


    @Override
    protected void convert(BaseViewHolder helper, FenSiBean.ResultBean item) {
        helper.setText(R.id.name, item.getNickname());
        helper.setText(R.id.idid, "ID"+item.getId());
        if (item.getSex()==1){
            helper.setBackgroundRes(R.id.xingbie,R.drawable.nan);
        }else {
            helper.setBackgroundRes(R.id.xingbie,R.drawable.nv);
        }
        Glide.with(mContext)
                .load(item.getHeadImage())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into((ImageView) helper.getView(R.id.touxiang));
//
//        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
//        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }



}
