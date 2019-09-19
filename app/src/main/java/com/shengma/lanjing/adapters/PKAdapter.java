package com.shengma.lanjing.adapters;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.liveroom.roomutil.commondef.RoomInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PKAdapter extends BaseQuickAdapter<RoomInfo,BaseViewHolder> {
        private ImageView touxiang,xingbie;
        private TextView dengji,name;
        private Button pk;


    public PKAdapter(List<RoomInfo> list) {
        super(R.layout.pk_item, list);
    }


    @Override
    protected void convert(BaseViewHolder helper, RoomInfo item) {
        helper.setText(R.id.name, item.pushers.get(0).userName);
        helper.setText(R.id.dengji, "Lv.0");
        Glide.with(mContext)
                .load(item.pushers.get(0).userAvatar)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into((ImageView) helper.getView(R.id.touxiang));
         Button view= helper.getView(R.id.pk);
         view.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Log.d(TAG, "roomCreator:"+item.roomCreator);
                 EventBus.getDefault().post(new MsgWarp(1006,item.roomCreator));
             }
         });


        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }



}
