package cn.pedant.SafeWebViewBridge.jsbridge;

import android.app.Activity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.pedant.SafeWebViewBridge.jsbridge.model.JSRequest;
import cn.pedant.SafeWebViewBridge.jsbridge.model.JSResponse;
import cn.pedant.SafeWebViewBridge.jsbridge.utils.Constants;

/**
 * Created by Harbor on 2016/3/10.
 */
public class LightWebViewClient extends WebViewClient {

    private Class injectedCls;
    private ExecutorService executorService;
    private WeakReference<Activity> activityWeakReference;

    /**
     * @param activity
     * @param optClass should instanceOf {@link cn.pedant.SafeWebViewBridge.jsbridge.utils.ActivityJSBridge}
     */
    public LightWebViewClient(Activity activity, Class optClass) {
        activityWeakReference = new WeakReference<Activity>(activity);
        injectedCls = optClass;
        executorService = Executors.newFixedThreadPool(3);
    }

    @Override
    public void onLoadResource(final WebView view, final String url) {
        super.onLoadResource(view, url);

        shouldParseJs(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (shouldParseJs(view, url)) {
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    private boolean shouldParseJs(final WebView view, final String url){
        if (!url.startsWith(Constants.SCHEMA_HOST)) {
            return false;
        }

        try {
            if (executorService != null && !executorService.isShutdown() && activityWeakReference.get() != null && !activityWeakReference.get().isFinishing()) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        process(view, url);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void process(WebView view, String url) {
        final JSRequest request = new JSRequest();
        final JSResponse response = new JSResponse();

        try {
            request.parse(url);

            Method method = injectedCls.getDeclaredMethod(request.method, WebView.class, JSRequest.class, JSResponse.class);
            method.invoke(null, view, request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //return callBack
        JSBridgeResult.callBackData(view, request, response);

    }
}
