package com.shengma.lanjing.adapters;



import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.MyXiaoXiBean;
import java.util.List;

public class XiaoXiAdapter extends BaseQuickAdapter<MyXiaoXiBean.ResultBean,BaseViewHolder> {

    public XiaoXiAdapter(List<MyXiaoXiBean.ResultBean> list) {
        super(R.layout.xiaoxi_item, list);
    }


    @Override
    protected void convert(BaseViewHolder helper, MyXiaoXiBean.ResultBean item) {
        helper.setText(R.id.title, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.time, item.getCreateTime());
        helper.setText(R.id.neirong, Html.fromHtml(item.getContent()));
//        Glide.with(mContext)
//                .load(item.getHeadImage())
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners( 10)))
//                .into((ImageView) helper.getView(R.id.bgbg));

        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
        //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
    }



}
