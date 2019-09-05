package com.shengma.lanjing.ui.fargments;


import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.views.MyTopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.objectbox.Box;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment {
    @BindView(R.id.mytopview)
    MyTopView mytopview;
    @BindView(R.id.touxiang)
    ImageView touxiang;
    private Unbinder unbinder;
    private Box<BaoCunBean> baoCunBeanBox = MyApplication.myApplication.getBaoCunBeanBox();
    private BaoCunBean baoCunBean=null;

    public Fragment4() {
        baoCunBean=baoCunBeanBox.get(123456);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment4, container, false);
        unbinder = ButterKnife.bind(this, view);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        mytopview.setWH(widthPixels, heightPixels);

        if (baoCunBean!=null){
            Glide.with(getActivity())
                    .load(baoCunBean.getHeadImage())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(touxiang);

            //RequestOptions.bitmapTransform(new CircleCrop())//圆形
            //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角

        }


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
