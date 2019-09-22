package com.shengma.lanjing.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.beans.XiaZaiLiWuBean;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by zhangxutong on 2016/2/16.
 */
public class GridViewAdapter2 extends BaseAdapter {
    private List<XiaZaiLiWuBean> mDatas;
    private LayoutInflater mLayoutInflater;
    /**
     * 页数下标,从0开始
     */
    private int mIndex;
    /**
     * 每页显示最大条目个数 ,默认是dimes.xml里 HomePageHeaderColumn 属性值的两倍
     */
    private int mPageSize;
    private Context context;

    public GridViewAdapter2(Context context, List<XiaZaiLiWuBean> mDatas, int mIndex) {
        this.mDatas = mDatas;
        this.context=context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mIndex = mIndex;
        mPageSize = 4 * 2;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mDatas.size() > (mIndex+1)*mPageSize,
     * 如果够，则直接返回每一页显示的最大条目个数mPageSize,
     * 如果不够，则有几项返回几,(mDatas.size() - mIndex * mPageSize);
     */
    @Override
    public int getCount() {
        return mDatas.size() > (mIndex + 1) * mPageSize ? mPageSize : (mDatas.size() - mIndex * mPageSize);

    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position + mIndex * mPageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + mIndex * mPageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Log.i("TAG", "position:" + position);
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_gridview_header, parent, false);
            vh = new ViewHolder();
            vh.name = (TextView) convertView.findViewById(R.id.name);
            vh.jingbi = (TextView) convertView.findViewById(R.id.jingbi);
            vh.iv = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + mIndex * mPageSize，
         */
        int pos = position + mIndex * mPageSize;
        vh.name.setText(mDatas.get(pos).getGiftName());
        vh.jingbi.setText(mDatas.get(pos).getNum()+"个");
        if (mDatas.get(pos).isA()){
            vh.iv.setBackgroundResource(R.color.image_color_red);
        }else {
            vh.iv.setBackgroundResource(R.color.transparent);
        }
       // Log.d("GridViewAdapter", mDatas.get(pos).getGiftUrl()+"  "+mDatas.get(pos).getId());
        Glide.with(context)
                .load(mDatas.get(pos).getGiftUrl())
                //.apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(vh.iv);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Log.d("GridViewAdapter", position+"mDatas.get(pos).getId():" + mDatas.get(pos).getId());
                EventBus.getDefault().post(new MsgWarp(2009,position+mIndex*mPageSize+"",mDatas.get(pos).getType()+""));
            }
        });
        return convertView;
    }


   private class ViewHolder {
        private TextView name,jingbi;
        private ImageView iv;
    }
}
