package com.shengma.lanjing.adapters;

import android.graphics.Color;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.LiveType;


import java.util.List;

public class PingDaoAdapter extends BaseQuickAdapter<LiveType.ResultBean,BaseViewHolder> {


    public PingDaoAdapter(List<LiveType.ResultBean> list) {
        super(R.layout.pingdao_item, list);

    }



    @Override
    protected void convert(BaseViewHolder helper, LiveType.ResultBean item) {

            helper.setText(R.id.button,item.getName());
        if (item.getIsXZ()==1){
            helper.setBackgroundRes(R.id.button,R.drawable.xuanzhongpd);
            helper.setTextColor(R.id.button, Color.parseColor("#5DAAF8"));
        }else {
            helper.setBackgroundRes(R.id.button,R.drawable.wxuanzhongpd);
            helper.setTextColor(R.id.button, Color.parseColor("#AAB5CA"));
        }

    }




}
