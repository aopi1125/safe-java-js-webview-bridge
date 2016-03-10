package cn.pedant.SafeWebViewBridge.jsbridge.utils;

import android.webkit.WebView;

import cn.pedant.SafeWebViewBridge.jsbridge.model.JSRequest;
import cn.pedant.SafeWebViewBridge.jsbridge.model.JSResponse;

/**
 * Created by harbor on 2016/3/10.只定义需要的JS桥方法
 */
public class ActivityJSBridge {

    public static void do1(WebView webView, JSRequest request, JSResponse response) {
        ActivityJSBridgeImpl.getInstance().do1(webView, request, response);
    }

    public static void do2(WebView webView, JSRequest request, JSResponse response) {
        ActivityJSBridgeImpl.getInstance().do1(webView, request, response);
    }

    //...........
}
