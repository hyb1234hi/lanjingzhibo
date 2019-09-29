package com.shengma.lanjing.adapters;

import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.PaiHangListBean;
import com.shengma.lanjing.utils.Utils;


import java.util.List;

public class PaiHangListAdapter extends BaseQuickAdapter<PaiHangListBean.ResultBean,BaseViewHolder> {

    public PaiHangListAdapter(List<PaiHangListBean.ResultBean> list) {
        super(R.layout.paihanglist_item, list);
    }


    @Override
    protected void convert(BaseViewHolder helper, PaiHangListBean.ResultBean item) {
        helper.setText(R.id.name, item.getNickname());
        helper.setText(R.id.dengji, "Lv."+item.getLevel());
        helper.setText(R.id.paihang, (helper.getLayoutPosition()+1)+"");
        double xg = item.getIncome();
        if (xg>=10000){
            helper.setText(R.id.jingbi, Utils.doubleToString(xg)+"万");
        }else {
            helper.setText(R.id.jingbi,xg+"");
        }

        if (helper.getLayoutPosition()==0 || helper.getLayoutPosition()==1 ||helper.getLayoutPosition()==2 ){
            helper.setTextColor(R.id.paihang, Color.parseColor("#F36D87"));
        }else {
            helper.setTextColor(R.id.paihang, Color.parseColor("#ffffff"));
        }
        if (item.getSex()==1){
            helper.setImageResource(R.id.xingbie,R.drawable.nan);
        }else {
            helper.setImageResource(R.id.xingbie,R.drawable.nv);
        }
        Glide.with(mContext)
                .load(item.getHeadImage())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into((ImageView) helper.getView(R.id.touxiang));
    }



}
