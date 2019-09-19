package com.shengma.lanjing.adapters;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.YongHuListBean;

import java.util.List;

public class PaiHangListAdapter extends BaseQuickAdapter<YongHuListBean,BaseViewHolder> {

    public PaiHangListAdapter(List<YongHuListBean> list) {
        super(R.layout.paihanglist_item, list);
    }


    @Override
    protected void convert(BaseViewHolder helper, YongHuListBean item) {
//        helper.setText(R.id.title, item.getTitle());
//        helper.setText(R.id.dengji, "Lv."+item.getAnchorLevel());
//        helper.setText(R.id.juli, item.getDistance()+"Km");
//        Glide.with(mContext)
//                .load(item.getCoverImg())
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners( 10)))
//                .into((ImageView) helper.getView(R.id.bgbg));

        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }



}
