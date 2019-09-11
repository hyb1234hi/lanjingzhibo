package com.shengma.lanjing.adapters;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.LiaoTianBean;

import java.util.List;

public class LiaoTianAdapter extends BaseQuickAdapter<LiaoTianBean,BaseViewHolder> {

    public LiaoTianAdapter(List<LiaoTianBean> list) {
        super(R.layout.liaotian_item, list);
    }


    @Override
    protected void convert(BaseViewHolder helper, LiaoTianBean item) {
        helper.setText(R.id.neirong, item.getNeirong());

//        Glide.with(mContext)
//                .load(item.getHeadImage())
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners( 10)))
//                .into((ImageView) helper.getView(R.id.bgbg));

        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }



}
