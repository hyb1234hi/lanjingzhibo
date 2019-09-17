package com.shengma.lanjing.ui.fargments;


import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.shengma.lanjing.R;
import com.shengma.lanjing.views.GZFragmentPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.tv11)
    TextView tv11;
    @BindView(R.id.im11)
    ImageView im11;
    @BindView(R.id.tv22)
    TextView tv22;
    @BindView(R.id.im22)
    ImageView im22;
    @BindView(R.id.tv33)
    TextView tv33;
    @BindView(R.id.im33)
    ImageView im33;
    @BindView(R.id.sousuo)
    RelativeLayout sousuo;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.rl3)
    RelativeLayout rl3;
    private ViewPager viewpage;
    private Unbinder unbinder;

    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        unbinder = ButterKnife.bind(this, view);
        viewpage = view.findViewById(R.id.viewpage1);
        GZFragmentPagerAdapter myFragmentPagerAdapter = new GZFragmentPagerAdapter(getChildFragmentManager());
        viewpage.setAdapter(myFragmentPagerAdapter);
        //设置当前页的ID
        viewpage.setCurrentItem(0);
        //添加翻页监听事件
        viewpage.addOnPageChangeListener(this);
        viewpage.setOffscreenPageLimit(3);

        return view;
    }

    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (viewpage.getCurrentItem()) {
                case 0: {
                    resize();
                    tv11.setTextColor(Color.BLACK);
                    tv11.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    im11.setVisibility(View.VISIBLE);

                    break;
                }
                case 1: {
                    resize();
                    tv22.setTextColor(Color.BLACK);
                    tv22.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    im22.setVisibility(View.VISIBLE);
                    break;
                }
                case 2: {
                    resize();
                    tv33.setTextColor(Color.BLACK);
                    tv33.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    im33.setVisibility(View.VISIBLE);
                    break;
                }

            }
        }
    }

    private void resize() {
        tv11.setTextColor(Color.parseColor("#666666"));
        tv22.setTextColor(Color.parseColor("#666666"));
        tv33.setTextColor(Color.parseColor("#666666"));
        tv11.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        tv22.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        tv33.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        im11.setVisibility(View.GONE);
        im22.setVisibility(View.GONE);
        im33.setVisibility(View.GONE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.sousuo)
    public void onViewClicked() {


    }

    @OnClick({R.id.rl1, R.id.rl2, R.id.rl3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl1:
                viewpage.setCurrentItem(0);
                resize();
                tv11.setTextColor(Color.BLACK);
                tv11.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                im11.setVisibility(View.VISIBLE);
                break;
            case R.id.rl2:
                viewpage.setCurrentItem(1);
                resize();
                tv22.setTextColor(Color.BLACK);
                tv22.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                im22.setVisibility(View.VISIBLE);
                break;
            case R.id.rl3:
                viewpage.setCurrentItem(2);
                resize();
                tv33.setTextColor(Color.BLACK);
                tv33.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                im33.setVisibility(View.VISIBLE);
                break;
        }
    }
}
