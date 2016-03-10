package cn.pedant.SafeWebViewBridge.jsbridge.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.WebView;

import org.json.JSONObject;

import cn.pedant.SafeWebViewBridge.jsbridge.JSBridgeResult;
import cn.pedant.SafeWebViewBridge.jsbridge.model.JSRequest;
import cn.pedant.SafeWebViewBridge.jsbridge.model.JSResponse;

/**
 * Created by pc on 2016/3/10.
 */
public class ActivityJSBridgeImpl {

    private static ActivityJSBridgeImpl activityJSBridge;
    private ActivityJSBridgeImplOnResult activityJSBridgeImplOnResult;

    private JSRequest mRequest;
    private JSResponse mResponse;
    private WebView mWebView;

    public static ActivityJSBridgeImpl getInstance() {
        if (activityJSBridge == null) {
            synchronized (ActivityJSBridgeImpl.class) {
                activityJSBridge = new ActivityJSBridgeImpl();
            }
        }
        return activityJSBridge;
    }

    public ActivityJSBridgeImpl() {
        super();
        this.activityJSBridgeImplOnResult = new ActivityJSBridgeImplOnResult(this);
    }

    public void do1(WebView webView, JSRequest request, JSResponse response) {
        response.finished = true;
        //do what do you want
        webView.post(new Runnable() {
            @Override
            public void run() {
                // do the fucking UI
            }
        });

    }

    public void do2(WebView webView, JSRequest request, JSResponse response) {
        this.mRequest = request;
        this.mResponse = response;
        this.mWebView = webView;

        //do what do you want, no need fucking UI
        ((Activity)webView.getContext()).startActivityForResult(new Intent("选择图片"), ActivityJSBridgeImplOnResult.RequestCode_Do2);
    }

    public void callBackData(JSONObject jsonObject, String error){
        mResponse.finished = true;
        if(!TextUtils.isEmpty(error)){
            mResponse.success = false;
            mResponse.error = error;
        }else if(jsonObject != null){
            mResponse.datas = jsonObject;
        }else{
            mResponse.success = false;
            mResponse.error = "已取消操作";
        }
        JSBridgeResult.callBackData(mWebView, mRequest, mResponse);
    }

    /**
     * called by {@link Activity#onActivityResult(int, int, Intent)}
     * @param reqCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int reqCode, int resultCode, Intent data){
        activityJSBridgeImplOnResult.onActivityResult(reqCode, resultCode, data);
    }

}
