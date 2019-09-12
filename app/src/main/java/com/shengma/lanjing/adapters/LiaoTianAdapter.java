package com.shengma.lanjing.adapters;


import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.LiaoTianBean;

import java.util.List;

public class LiaoTianAdapter extends BaseMultiItemQuickAdapter<LiaoTianBean,BaseViewHolder> {

    public LiaoTianAdapter(List<LiaoTianBean> list) {
        super(list);
        addItemType(LiaoTianBean.ONE,R.layout.liaotian_item);
        addItemType(LiaoTianBean.TOW, R.layout.liaotian_item2);

    }


    @Override
    protected void convert(BaseViewHolder helper, LiaoTianBean item) {
        switch (helper.getItemViewType()) {
            case LiaoTianBean.ONE:
                helper.setText(R.id.name1, item.getNickname());
                helper.setText(R.id.neirong1, item.getNeirong());
                helper.setText(R.id.dengjilt, "Lv."+item.getDengji());
                break;
            case LiaoTianBean.TOW:
                helper.setText(R.id.name, item.getNickname());
                helper.setText(R.id.neirong, "加入直播间");
                break;
        }


//        Glide.with(mContext)
//                .load(item.getHeadImage())
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners( 10)))
//                .into((ImageView) helper.getView(R.id.bgbg));

        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }



}
