package com.shengma.lanjing.adapters;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.ZhiBoSHiChangBean;

import java.util.List;

public class ZHiBoShiChangAdapter extends BaseQuickAdapter<ZhiBoSHiChangBean.ResultBean,BaseViewHolder> {

    public ZHiBoShiChangAdapter(List<ZhiBoSHiChangBean.ResultBean> list) {
        super(R.layout.zhiboshichang_item, list);
    }


    @Override
    protected void convert(BaseViewHolder helper, ZhiBoSHiChangBean.ResultBean item) {
        helper.setText(R.id.shijian, item.getDay());
        helper.setText(R.id.shichang, item.getMinutes()+"");
//        Glide.with(mContext)
//                .load(item.getHeadImage())
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners( 10)))
//                .into((ImageView) helper.getView(R.id.bgbg));
//
//        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
//        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }



}
