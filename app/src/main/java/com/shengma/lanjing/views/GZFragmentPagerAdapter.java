package com.shengma.lanjing.views;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.shengma.lanjing.ui.fargments.SYFragment1;
import com.shengma.lanjing.ui.fargments.SYFragment2;
import com.shengma.lanjing.ui.fargments.SYFragment3;

import org.jetbrains.annotations.NotNull;

public class GZFragmentPagerAdapter extends FragmentPagerAdapter {

    private SYFragment1 myFragment1 = null;
    private SYFragment2 myFragment2 = null;
    private SYFragment3 myFragment3 = null;


    public GZFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        myFragment1 = new SYFragment1();
        myFragment2 = new SYFragment2();
        myFragment3 = new SYFragment3();

    }

    @Override
    public int getCount() {
        return 3;
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
            case 0:
                fragment = myFragment1;
                break;
            case 1:
                fragment = myFragment2;
                break;
            case 2:
                fragment = myFragment3;
                break;
        }
        return fragment;
    }
}

