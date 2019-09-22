package com.shengma.lanjing.adapters;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.ZhiBoBean;

import java.util.List;

public class ZHiBoAdapter extends BaseQuickAdapter<ZhiBoBean.ResultBean,BaseViewHolder> {

    public ZHiBoAdapter(List<ZhiBoBean.ResultBean> list) {
        super(R.layout.zhibo_item, list);
    }


    @Override
    protected void convert(BaseViewHolder helper, ZhiBoBean.ResultBean item) {
        helper.setText(R.id.title, item.getTitle());
        helper.setText(R.id.dengji, "Lv."+item.getAnchorLevel());
        helper.setText(R.id.renshu, item.getOnlineNums()+"");
        Glide.with(mContext)
                .load(item.getCoverImg())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners( 10)))
                .into((ImageView) helper.getView(R.id.bgbg));

        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }



}
