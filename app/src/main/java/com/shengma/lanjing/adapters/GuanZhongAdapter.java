package com.shengma.lanjing.adapters;



import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.YongHuListBean;


import java.util.List;

public class GuanZhongAdapter extends BaseQuickAdapter<YongHuListBean,BaseViewHolder> {


    public GuanZhongAdapter(List<YongHuListBean> list) {
        super(R.layout.guanzhong_item, list);

    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, YongHuListBean item) {

        helper.setText(R.id.xiangguang,item.getJingbi()+"");
        Glide.with(mContext)
                .load(item.getHeadImage())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into((ImageView) helper.getView(R.id.touxiang));

    }




}
