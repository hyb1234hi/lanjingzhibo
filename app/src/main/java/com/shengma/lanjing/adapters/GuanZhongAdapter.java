package com.shengma.lanjing.adapters;



import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.GuanZhongBean;



import java.util.List;

public class GuanZhongAdapter extends BaseQuickAdapter<GuanZhongBean,BaseViewHolder> {


    public GuanZhongAdapter(List<GuanZhongBean> list) {
        super(R.layout.guanzhong_item, list);

    }



    @Override
    protected void convert(@NonNull BaseViewHolder helper, GuanZhongBean item) {
        if (item.getXingguang()==null){
            helper.setText(R.id.xiangguang,"0");
        }else {
            helper.setText(R.id.xiangguang,item.getXingguang());
        }
        Glide.with(mContext)
                .load(item.getHeadImage())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into((ImageView) helper.getView(R.id.touxiang));

    }




}
