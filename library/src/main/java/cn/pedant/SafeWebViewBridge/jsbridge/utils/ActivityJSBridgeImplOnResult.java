package cn.pedant.SafeWebViewBridge.jsbridge.utils;

import android.content.Intent;
import android.webkit.WebView;

import org.json.JSONObject;

import cn.pedant.SafeWebViewBridge.jsbridge.JSBridgeResult;
import cn.pedant.SafeWebViewBridge.jsbridge.model.JSRequest;
import cn.pedant.SafeWebViewBridge.jsbridge.model.JSResponse;

/**
 * Created by pc on 2016/3/10.
 */
public class ActivityJSBridgeImplOnResult {

    public static final int RequestCode_Do2 = 2;

    private ActivityJSBridgeImpl activityJSBridge;

    public ActivityJSBridgeImplOnResult(ActivityJSBridgeImpl activityJSBridge){
        this.activityJSBridge = activityJSBridge;
    }


    public void do2(int reqCode, int resultCode, Intent data) {

        //return the data
        JSONObject jsonObject = new JSONObject();
        //jsonObject = changeOf(data);
        this.activityJSBridge.callBackData(jsonObject, null);
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data){
        if(reqCode == RequestCode_Do2){
            do2(reqCode, resultCode, data);
        }

    }
}
