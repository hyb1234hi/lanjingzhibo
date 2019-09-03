package com.example.myapplication2.ui.fargments;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SYFragment1 extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;



    public SYFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_syfragment1, container, false);
        swipeRefreshLayout=view.findViewById(R.id.refreshLayout);
        recyclerView=view.findViewById(R.id.recyclerview);
        //设置进度View的组合颜色，在手指上下滑时使用第一个颜色，在刷新中，会一个个颜色进行切换
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#3EE1F7"), Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE);



        return view;
    }

}
