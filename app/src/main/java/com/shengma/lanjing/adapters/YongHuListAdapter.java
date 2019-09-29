package com.shengma.lanjing.adapters;

import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.YongHuPaiHang;
import com.shengma.lanjing.utils.Utils;

import java.util.List;

public class YongHuListAdapter extends BaseQuickAdapter<YongHuPaiHang.ResultBean,BaseViewHolder> {

    public YongHuListAdapter(List<YongHuPaiHang.ResultBean> list) {
        super(R.layout.yonghulist_item, list);
    }


    @Override
    protected void convert(BaseViewHolder helper, YongHuPaiHang.ResultBean item) {
        helper.setText(R.id.name, item.getNickname());
        helper.setText(R.id.dengji, "Lv."+item.getUserLevel());
        helper.setText(R.id.paihang, (helper.getLayoutPosition()+1)+"");
        double xg = item.getTotal();
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
//        if (item.getType()==1){
//            helper.setGone(R.id.guanliyuan,true);
//        }else {
//            helper.setGone(R.id.guanliyuan,false);
//        }
//        if (item.getType()==2){
//            helper.setGone(R.id.jingyan,true);
//        }else {
//            helper.setGone(R.id.jingyan,false);
//        }
        Glide.with(mContext)
                .load(item.getHeadImage())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into((ImageView) helper.getView(R.id.touxiang));
        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }



}
