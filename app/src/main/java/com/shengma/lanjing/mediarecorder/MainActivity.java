package com.shengma.lanjing.mediarecorder;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.shengma.lanjing.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_void);
        initView();
        requestPermission();
    }

    private void initView() {

    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT > 22) {
            requestPermissions(new String[]{Manifest.permission.CAMERA
                    , Manifest.permission.RECORD_AUDIO
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }



    public void customCapture(View v) {
        Intent intent = new Intent(this, RecordActivity.class);
        startActivityForResult(intent, 11);
    }

}
