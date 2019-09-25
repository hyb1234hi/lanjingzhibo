package com.shengma.lanjing.wxapi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    /**
     * 微信登录相关
     */
    private IWXAPI api;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(this, Consts.APP_ID,true);
        //将应用的appid注册到微信
        api.registerApp(Consts.APP_ID);
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            boolean result =  api.handleIntent(getIntent(), this);
            if(!result){
                Log.d("微信界面:","参数不合法，未被SDK处理，退出");
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        api.handleIntent(data,this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d("ChongZhiActivity","baseReq:"+ JSON.toJSONString(baseReq));
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.d("ChongZhiActivity","baseResp:"+JSON.toJSONString(baseResp));
        Log.d("ChongZhiActivity","baseResp:"+baseResp.errStr+","+baseResp.openId+","+baseResp.transaction+","+baseResp.errCode);
        String result = "";
        switch(baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result ="发送成功";
                Log.d("WXEntryActivity", result);
                JsonObject jsonObject = GsonUtil.parse(JSON.toJSONString(baseResp)).getAsJsonObject();
                String code = jsonObject.get("code").getAsString();
                EventBus.getDefault().post(new MsgWarp(1001,code));
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "发送取消";
                Log.d("WXEntryActivity", result);
                EventBus.getDefault().post(new MsgWarp(-1001,"取消授权"));
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                Log.d("WXEntryActivity", result);
                EventBus.getDefault().post(new MsgWarp(-1001,"拒绝授权"));
                finish();
                break;
            default:
                result = "发送返回";
                Log.d("WXEntryActivity", result);
                EventBus.getDefault().post(new MsgWarp(-1001,"取消授权"));
                finish();
                break;

        }

        if(baseResp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
            Log.d("ChongZhiActivity","onPayFinish,errCode="+baseResp.errCode);
          //  0	成功	展示成功页面
           // -1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
          //  -2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
        }

    }
}