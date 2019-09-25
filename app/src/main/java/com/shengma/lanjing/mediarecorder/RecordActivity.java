package com.shengma.lanjing.mediarecorder;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hw.videoprocessor.VideoProcessor;
import com.hw.videoprocessor.util.VideoProgressListener;
import com.shengma.lanjing.R;
import com.shengma.lanjing.dialogs.ShangChuanShiPingDialog;
import com.shengma.lanjing.mediarecorder.util.CameraUtil;
import com.shengma.lanjing.mediarecorder.util.FileUtil;
import com.shengma.lanjing.mediarecorder.util.RecorderStatus;
import com.shengma.lanjing.utils.ToastUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RecordActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {
    private static final String TAG = "RecordActivity";
    @BindView(R.id.fanzhuang)
    View fanzhuang;
    @BindView(R.id.guanbi)
    View guanbi;
    //视图控件
    private TextureView mTextureV;
    private SurfaceTexture mSurfaceTexture;
    private Chronometer chronometer;
    private String path;
    //存储文件
    private Camera mCamera;
    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private int mRotationDegree;
    private  ZLoadingDialog dialog;
    private ShangChuanShiPingDialog shangChuanShiPingDialog=null;
    //录制相关参数
    private MediaRecorder mMediaRecorder;
    private int mFps;//帧率
    private RecorderStatus mStatus = RecorderStatus.RELEASED;//录制状态

    //录制出错的回调
    private MediaRecorder.OnErrorListener onErrorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
            try {
                if (mMediaRecorder != null) {
                    mMediaRecorder.reset();
                }
            } catch (Exception e) {
                Toast.makeText(RecordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);
        hideStatusBarAndNavBar(this);
        FileUtil.init(this);
        initView();
        dialog = new ZLoadingDialog(RecordActivity.this);
    }

    /**
     * 隐藏导航栏和状态栏
     */
    public static void hideStatusBarAndNavBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void initView() {
        mTextureV = findViewById(R.id.surface);
        mTextureV.setSurfaceTextureListener(this);
        chronometer = findViewById(R.id.record_time);
        chronometer.setFormat("%s");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaRecorder();
        releaseCamera();
    }

    /**
     * 开始录制和停止录制
     *
     * @param v
     */
    public void control(View v) {
        if (mStatus == RecorderStatus.RECORDING) {
            stopRecord();
            //显示弹窗
            if (path != null) {
                ShangChuanShiPingDialog shangChuanShiPingDialog = new ShangChuanShiPingDialog(path);
                shangChuanShiPingDialog.show(getSupportFragmentManager(), "dddffgryrty");
            } else {
                ToastUtils.showInfo(RecordActivity.this, "存储发生错误");
            }
        } else {
            startRecord();
        }
    }

    /**
     * 开始录制
     */
    private void startRecord() {
        initCamera();
        mCamera.unlock();
        initMediaRecorder();
        try {
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        mStatus = RecorderStatus.RECORDING;
    }

    private void qiehuanShe() {
        if (mCameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        } else {
            mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        initCamera();

    }

    /**
     * 停止录制
     */
    private void stopRecord() {
        releaseMediaRecorder();
        releaseCamera();
    }

    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surface, int width, int height) {
        mSurfaceTexture = surface;
        initCamera();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        //缓冲区大小变化时回调，该方法不需要用户自己处理
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        stopRecord();
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        //当每一帧数据可用时回调，不做处理
    }

//    ==============================================================================

    /**
     * 初始化相机
     */
    private void initCamera() {
        if (mSurfaceTexture == null) return;
        if (mCamera != null) {
            releaseCamera();
        }
        try {
            mCamera = Camera.open(mCameraId);
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "摄像头打开失败");
        }
        if (mCamera == null) {
            Toast.makeText(this, "没有可用相机", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mCamera.setPreviewTexture(mSurfaceTexture);
            mRotationDegree = CameraUtil.getCameraDisplayOrientation(this, mCameraId);
            mCamera.setDisplayOrientation(mRotationDegree);
            setCameraParameter(mCamera);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置相机的参数
     *
     * @param camera
     */
    private void setCameraParameter(Camera camera) {
        if (camera == null) return;
        Camera.Parameters parameters = camera.getParameters();
        //获取相机支持的>=20fps的帧率，用于设置给MediaRecorder
        //因为获取的数值是*1000的，所以要除以1000
        List<int[]> previewFpsRange = parameters.getSupportedPreviewFpsRange();
        for (int[] ints : previewFpsRange) {
            if (ints[0] >= 20000) {
                mFps = ints[0] / 1000;
                break;
            }
        }
        //设置聚焦模式
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        } else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        } else {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }


        //设置预览尺寸,因为预览的尺寸和最终是录制视频的尺寸无关，所以我们选取最大的数值
        //通常最大的是手机的分辨率，这样可以让预览画面尽可能清晰并且尺寸不变形，前提是TextureView的尺寸是全屏或者接近全屏
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        parameters.setPreviewSize(supportedPreviewSizes.get(0).width, supportedPreviewSizes.get(0).height);
        //缩短Recording启动时间
        parameters.setRecordingHint(true);
        //是否支持影像稳定能力，支持则开启
        if (parameters.isVideoStabilizationSupported())
            parameters.setVideoStabilization(true);
        camera.setParameters(parameters);
    }


    /**
     * 释放相机
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }


    /**
     * 初始化MediaRecorder
     */
    private void initMediaRecorder() {
        //如果是处于release状态，那么只有重新new一个进入initial状态
        //否则其他状态都可以通过reset()方法回到initial状态
        if (mStatus == RecorderStatus.RELEASED) {
            mMediaRecorder = new MediaRecorder();
        } else {
            mMediaRecorder.reset();
        }
        //设置选择角度，顺时针方向，因为默认是逆向90度的，这样图像就是正常显示了,这里设置的是观看保存后的视频的角度
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setOrientationHint(mRotationDegree);
        mMediaRecorder.setOnErrorListener(onErrorListener);
        //采集声音来源、mic是麦克风
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //采集图像来源、
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //设置编码参数
//        setProfile();
        setConfig();

        mMediaRecorder.setPreviewDisplay(new Surface(mSurfaceTexture));
        //设置输出的文件路径
        path = FileUtil.newMp4File().getAbsolutePath();
        mMediaRecorder.setOutputFile(path);

    }


    /**
     * 释放MediaRecorder
     */
    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            if (mStatus == RecorderStatus.RELEASED) return;
            mMediaRecorder.setOnErrorListener(null);
            mMediaRecorder.setPreviewDisplay(null);
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            mStatus = RecorderStatus.RELEASED;
            //停止计时
            chronometer.stop();
        }
    }


    /**
     * 通过系统的CamcorderProfile设置MediaRecorder的录制参数
     */
    private void setProfile() {
        CamcorderProfile profile = null;
        if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_1080P)) {
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_1080P);
        } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_720P)) {
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_720P);
        } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_480P)) {
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
        }
        if (profile != null) {
            mMediaRecorder.setProfile(profile);
        }
    }


    /**
     * 自定义MediaRecorder的录制参数
     */
    private void setConfig() {
        //设置封装格式 默认是MP4
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        //音频编码
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //图像编码
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        //声道
        mMediaRecorder.setAudioChannels(1);
        //设置最大录像时间 单位：毫秒
        mMediaRecorder.setMaxDuration(30 * 1000);
        //设置最大录制的大小60M 单位，字节
        mMediaRecorder.setMaxFileSize(30 * 1024 * 1024);
        //再用44.1Hz采样率
        mMediaRecorder.setAudioEncodingBitRate(22050);
        //设置帧率，该帧率必须是硬件支持的，可以通过Camera.CameraParameter.getSupportedPreviewFpsRange()方法获取相机支持的帧率
        mMediaRecorder.setVideoFrameRate(mFps);
        //设置码率
        mMediaRecorder.setVideoEncodingBitRate(500 * 1024 * 8);
        //设置视频尺寸，通常搭配码率一起使用，可调整视频清晰度
        mMediaRecorder.setVideoSize(1280, 720);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 66 && resultCode == RESULT_OK && null != data) {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = {MediaStore.Video.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedVideo,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
           String path2 = cursor.getString(columnIndex);
            path=FileUtil.newMp4File().getAbsolutePath();
            cursor.close();
            dialog.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)//设置类型
                    .setLoadingColor(Color.parseColor("#FF3EE1F7"))//颜色
                    .setHintText("压缩中...")
                    .setHintTextSize(16) // 设置字体大小 dp
                    .setHintTextColor(Color.WHITE)  // 设置字体颜色
                    .setDurationTime(0.6) // 设置动画时间百分比 - 0.5倍
                    .setDialogBackgroundColor(Color.parseColor("#bb111111")) // 设置背景色，默认白色
                    .show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                        VideoProcessor.processor(RecordActivity.this)
                                .input(path2)
                                .output(path)
                                //以下参数全部为可选
                                .outWidth(1280)
                                .outHeight(720)
                                // .startTimeMs(startTimeMs)//用于剪辑视频
                                // .endTimeMs(endTimeMs)    //用于剪辑视频
                                // .speed(speed)            //改变视频速率，用于快慢放
                                // .changeAudioSpeed(changeAudioSpeed) //改变视频速率时，音频是否同步变化
                                 .bitrate(500 * 1024 * 8)       //输出视频比特率
                                 .frameRate(mFps)   //帧率
                                // .iFrameInterval(iFrameInterval)  //关键帧距，为0时可输出全关键帧视频（部分机器上需为-1）
                                .progressListener(new VideoProgressListener() {
                                    @Override
                                    public void onProgress(float progress) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                               if (progress>=0.99){
                                                   dialog.dismiss();
                                                   //显示弹窗
                                                   if (shangChuanShiPingDialog==null){
                                                       shangChuanShiPingDialog = new ShangChuanShiPingDialog(path);
                                                       shangChuanShiPingDialog.show(getSupportFragmentManager(), "dddffgryrty");
                                                   }
                                                }
                                            }
                                        });
                                        Log.d(TAG, "progress:" + progress);
                                    }

                                }).process();
                       } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, e.getMessage()+"视频压缩异常");
                }}
                }).start();


        }
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
    }

    @OnClick({R.id.fanzhuang, R.id.guanbi,R.id.xiangce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanzhuang:
                qiehuanShe();
                break;
            case R.id.guanbi:
                finish();
                break;
            case R.id.xiangce:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 66);

                break;
        }
    }
}
