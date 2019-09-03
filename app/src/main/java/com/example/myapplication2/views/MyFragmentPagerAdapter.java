package com.example.myapplication2.views;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication2.ui.MainActivity;
import com.example.myapplication2.ui.fargments.Fragment1;
import com.example.myapplication2.ui.fargments.Fragment2;
import com.example.myapplication2.ui.fargments.Fragment3;
import com.example.myapplication2.ui.fargments.Fragment4;

import org.jetbrains.annotations.NotNull;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private Fragment1 myFragment1 = null;
    private Fragment2 myFragment2 = null;
    private Fragment3 myFragment3 = null;
    private Fragment4 myFragment4 = null;

    public MyFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        myFragment1 = new Fragment1();
        myFragment2 = new Fragment2();
        myFragment3 = new Fragment3();
        myFragment4 = new Fragment4();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = myFragment1;
                break;
            case MainActivity.PAGE_TWO:
                fragment = myFragment2;
                break;
            case MainActivity.PAGE_THREE:
                fragment = myFragment3;
                break;
            case MainActivity.PAGE_FOUR:
                fragment = myFragment4;
                break;
        }
        return fragment;
    }
}

