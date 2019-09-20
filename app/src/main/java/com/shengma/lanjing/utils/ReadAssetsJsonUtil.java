package com.shengma.lanjing.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.airbnb.lottie.ImageAssetDelegate;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.shengma.lanjing.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.content.ContentValues.TAG;

public class ReadAssetsJsonUtil {

    public static JSONObject getJSONObject(String fileName, Context context){
        try {
            return new JSONObject(getJson(fileName, context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJson(String fileName, Context context){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 读取本地文件
     */
    public static String readJSON(String id) {
        String path = MyApplication.SDPATH+File.separator+id+File.separator+id+".json";
        Log.d("BoFangActivity", "JSON地址:"+path);
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(path);
        if (!file.exists()) {
            return "";
        }
        if (file.isDirectory()) {
            Log.e("TestFile", "The File doesn't not exist.");
            return "";
        } else {
            try {
                InputStream instream = new FileInputStream(file);
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                while ((line = buffreader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                instream.close();
            } catch (java.io.FileNotFoundException e) {
                Log.e("TestFile", "The File doesn't not exist.");
                return "";
            } catch (IOException e) {
                Log.e("TestFile", e.getMessage()+"");
                return "";
            }
        }
        return stringBuilder.toString();//
    }



    /**
     * 播放sdcard的动画
     * @param jsonFile	json文件
     * @param imagesDir json文件引用的image文件的目录
     * @throws Exception
     */
    private void  showSdcardLottieEffects(File jsonFile, File imagesDir,LottieAnimationView specialEffectLottieAnim) throws Exception{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile));
        String content = null;
        StringBuilder stringBuilder = new StringBuilder();
        while ((content = bufferedReader.readLine()) != null){
            stringBuilder.append(content);
        }

        JSONObject jsonObject = new JSONObject(stringBuilder.toString());
        final String absolutePath = imagesDir.getAbsolutePath();
        //提供一个代理接口从 SD 卡读取 images 下的图片
          specialEffectLottieAnim.setImageAssetDelegate(new ImageAssetDelegate() {
            @Override
            public Bitmap fetchBitmap(LottieImageAsset asset) {
                Bitmap bitmap = null;
                FileInputStream fileInputStream = null;
                try {
                    fileInputStream = new FileInputStream(absolutePath + File.separator + asset.getFileName());
                    bitmap = BitmapFactory.decodeStream(fileInputStream);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        if (bitmap == null) {
                            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                    } catch (IOException e) {
                       Log.d(TAG, "e:" + e);
                    }
                }
                return bitmap;
            }
        });


//        LottieCompositionFactory.fromJson(getResources(), jsonObject, new OnCompositionLoadedListener() {
//            @Override
//            public void onCompositionLoaded(@Nullable LottieComposition composition) {
//                if(composition == null){
//                    return;
//                }
//                specialEffectLottieAnim.cancelAnimation();
//                specialEffectLottieAnim.setProgress(0);
//                specialEffectLottieAnim.setComposition(composition);
//                specialEffectLottieAnim.playAnimation();
//                specialEffectLottieAnim.setVisibility(View.VISIBLE);
//            }
//        });

    }


}
