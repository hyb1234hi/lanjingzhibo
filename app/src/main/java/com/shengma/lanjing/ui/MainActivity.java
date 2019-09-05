package com.shengma.lanjing.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.shengma.lanjing.R;
import com.shengma.lanjing.views.ControlScrollViewPager;
import com.shengma.lanjing.views.MyFragmentPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.ll3)
    LinearLayout ll3;
    @BindView(R.id.ll4)
    LinearLayout ll4;
    @BindView(R.id.im1)
    ImageView im1;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.im2)
    ImageView im2;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.im3)
    ImageView im3;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.im4)
    ImageView im4;
    @BindView(R.id.tv4)
    TextView tv4;


    private ControlScrollViewPager viewpage;
    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewpage = findViewById(R.id.viewpage);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewpage.setAdapter(myFragmentPagerAdapter);
        //设置当前页的ID
        viewpage.setCurrentItem(0);
        //添加翻页监听事件
        viewpage.addOnPageChangeListener(this);
        viewpage.setOffscreenPageLimit(4);

    }


    @OnClick({R.id.ll1, R.id.ll2, R.id.ll3, R.id.ll4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll1:
                resetSelected();
                tv1.setTextColor(Color.parseColor("#3EE1F7"));
                im1.setBackgroundResource(R.drawable.shouye1);
                viewpage.setCurrentItem(PAGE_ONE);
                break;
            case R.id.ll2:
                resetSelected();
                tv2.setTextColor(Color.parseColor("#F36D87"));
                im2.setBackgroundResource(R.drawable.fujin1);
                viewpage.setCurrentItem(PAGE_TWO);
                break;
            case R.id.ll3:
                resetSelected();
                tv3.setTextColor(Color.parseColor("#FB9210"));
                im3.setBackgroundResource(R.drawable.xiaoxi1);
                viewpage.setCurrentItem(PAGE_THREE);
                break;
            case R.id.ll4:
                resetSelected();
                tv4.setTextColor(Color.parseColor("#9327F5"));
                im4.setBackgroundResource(R.drawable.wode1);
                viewpage.setCurrentItem(PAGE_FOUR);
                break;
        }
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
                case PAGE_ONE: {
                    resetSelected();
                    tv1.setTextColor(Color.parseColor("#3EE1F7"));
                    im1.setBackgroundResource(R.drawable.shouye1);
                    break;
                }
                case PAGE_TWO: {
                    resetSelected();
                    tv2.setTextColor(Color.parseColor("#F36D87"));
                    im2.setBackgroundResource(R.drawable.fujin1);
                    break;
                }
                case PAGE_THREE: {
                    resetSelected();
                    tv3.setTextColor(Color.parseColor("#FB9210"));
                    im3.setBackgroundResource(R.drawable.xiaoxi1);
                    break;
                }
                case PAGE_FOUR: {
                    resetSelected();
                    tv4.setTextColor(Color.parseColor("#9327F5"));
                    im4.setBackgroundResource(R.drawable.wode1);
                    break;
                }
            }
        }
    }

    private void resetSelected(){
        tv1.setTextColor(Color.parseColor("#999999"));
        tv2.setTextColor(Color.parseColor("#999999"));
        tv3.setTextColor(Color.parseColor("#999999"));
        tv4.setTextColor(Color.parseColor("#999999"));
        im1.setBackgroundResource(R.drawable.shouye2);
        im2.setBackgroundResource(R.drawable.fujin2);
        im3.setBackgroundResource(R.drawable.xiaoxi2);
        im4.setBackgroundResource(R.drawable.wode2);
    }

}
