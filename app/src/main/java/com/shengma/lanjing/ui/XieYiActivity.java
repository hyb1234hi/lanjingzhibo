package com.shengma.lanjing.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.shengma.lanjing.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XieYiActivity extends AppCompatActivity {

    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.pdfview)
    PDFView pdfview;


    public void setFile(String ffff){

        pdfview.fromAsset(ffff)
                //  .pages(0, 2, 1, 3, 3, 3)
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                // .onDraw(onDrawListener)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {

                    }
                })
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {

                    }
                })
                .load();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xie_yi);
        ButterKnife.bind(this);
        setFile(getIntent().getStringExtra("file"));


    }

    @OnClick(R.id.fanhui)
    public void onViewClicked() {
        finish();
    }

}
