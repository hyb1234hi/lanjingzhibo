package com.shengma.lanjing.ui.fargments;


import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.ui.GeXinSheZhiActivity;
import com.shengma.lanjing.ui.KaiBoActivity;
import com.shengma.lanjing.ui.QianBaoActivity;
import com.shengma.lanjing.ui.WoDeZiLiaoActivity;
import com.shengma.lanjing.views.MyTopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.dengji)
    TextView dengji;
    @BindView(R.id.dengji2)
    TextView dengji2;
    @BindView(R.id.myid)
    TextView myid;
    @BindView(R.id.textView1)
    LinearLayout textView1;
    @BindView(R.id.guanzhu)
    TextView guanzhu;
    @BindView(R.id.textView2)
    LinearLayout textView2;
    @BindView(R.id.fensi)
    TextView fensi;
    @BindView(R.id.textView3)
    LinearLayout textView3;
    @BindView(R.id.shichang)
    TextView shichang;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.rl3)
    RelativeLayout rl3;
    @BindView(R.id.rl4)
    RelativeLayout rl4;
    @BindView(R.id.rl5)
    RelativeLayout rl5;
    @BindView(R.id.bianji)
    ImageView bianji;

    private Unbinder unbinder;
    private Box<BaoCunBean> baoCunBeanBox = MyApplication.myApplication.getBaoCunBeanBox();
    private BaoCunBean baoCunBean = null;

    public Fragment4() {
        baoCunBean = baoCunBeanBox.get(123456);

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
        Log.d("ZhiBoActivity", "heightPixels:" + heightPixels);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        baoCunBean = baoCunBeanBox.get(123456);
        if (baoCunBean != null) {
            if (getActivity()!=null)
            Glide.with(getActivity())
                    .load(baoCunBean.getHeadImage())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(touxiang);
            //RequestOptions.bitmapTransform(new CircleCrop())//圆形
            //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
            name.setText(baoCunBean.getNickname() + "");
            dengji.setText("Lv." + baoCunBean.getAnchorLevel());
            dengji2.setText("Lv." + baoCunBean.getUserLevel());
            myid.setText("ID:" + baoCunBean.getUserid());
            shichang.setText(baoCunBean.getDuration() + "");
            fensi.setText(baoCunBean.getFans() + "");
            guanzhu.setText(baoCunBean.getIdols() + "");
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4, R.id.rl5, R.id.textView1, R.id.textView2, R.id.textView3,R.id.bianji})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textView1:

                break;
            case R.id.textView2:

                break;
            case R.id.textView3:

                break;
            case R.id.rl1:
                startActivity(new Intent(getActivity(), QianBaoActivity.class));
                break;
            case R.id.rl2:
                startActivity(new Intent(getActivity(), KaiBoActivity.class));
                break;
            case R.id.rl3:

                break;
            case R.id.rl4:
                startActivity(new Intent(getActivity(), GeXinSheZhiActivity.class));
                break;
            case R.id.rl5:

                break;
            case R.id.bianji:
                startActivity(new Intent(getActivity(), WoDeZiLiaoActivity.class));
                break;
        }
    }



}
