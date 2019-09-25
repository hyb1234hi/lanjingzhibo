package com.shengma.lanjing.adapters;




import android.graphics.Color;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.LiaoTianBean;

import java.util.List;

public class LiaoTianAdapter extends BaseMultiItemQuickAdapter<LiaoTianBean,BaseViewHolder> {

    public LiaoTianAdapter(List<LiaoTianBean> list) {
        super(list);
        addItemType(1,R.layout.liaotian_item);//带等级的
        addItemType(2, R.layout.liaotian_item2);//进入直播间

    }


    @Override
    protected void convert(BaseViewHolder helper, LiaoTianBean item) {
        switch (helper.getItemViewType()) {
            case 1:
                if (item.getNeirong().length()>=8){
                    String ss1 = item.getNeirong().substring(0,8);
                    String ss2 = item.getNeirong().substring(8,item.getNeirong().length());
                    helper.setGone(R.id.neirong2,true);
                    helper.setText(R.id.name1, item.getNickname());
                    helper.setText(R.id.neirong1, ss1);
                    helper.setText(R.id.neirong2, ss2);
                    helper.setText(R.id.dengjilt, "Lv."+item.getDengji());
                }else {
                    helper.setGone(R.id.neirong2,false);
                    helper.setText(R.id.name1, item.getNickname());
                    helper.setText(R.id.neirong1, item.getNeirong());
                    helper.setText(R.id.dengjilt, "Lv."+item.getDengji());
                }

                break;
            case 2:
                if (item.getNickname().equals("")){
                    helper.setTextColor(R.id.neirong,Color.parseColor("#62B7FA"));
                    helper.setText(R.id.name, item.getNickname());
                    helper.setText(R.id.neirong, item.getNeirong());
                }else {
                    helper.setTextColor(R.id.neirong,Color.parseColor("#ffffff"));
                    helper.setText(R.id.name, item.getNickname());
                    helper.setText(R.id.neirong, item.getNeirong());
                }

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
