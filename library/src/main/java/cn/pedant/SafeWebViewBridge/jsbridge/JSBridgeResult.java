package cn.pedant.SafeWebViewBridge.jsbridge;

import android.webkit.WebView;

import cn.pedant.SafeWebViewBridge.jsbridge.model.JSRequest;
import cn.pedant.SafeWebViewBridge.jsbridge.model.JSResponse;
import cn.pedant.SafeWebViewBridge.jsbridge.utils.Constants;

/**
 * Created by harbor on 2016/3/10
 */
public class JSBridgeResult {

    public static void callBackData(final WebView webView, JSRequest request, JSResponse response) {
        if(!response.finished){
            return;
        }
        final String callBackID = request.callBackID;
        final String data = response.encode();
        webView.post(new Runnable() {
            @Override
            public void run() {
                String url = String.format(Constants.CALLBACK_URL_JS, callBackID, data);
                webView.loadUrl(url);
            }
        });
    }

}
