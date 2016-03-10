package cn.pedant.SafeWebViewBridge.jsbridge.model;

import android.text.TextUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import cn.pedant.SafeWebViewBridge.jsbridge.utils.Constants;

/**
 * Created by Harbor on 2016/3/10.
 */
public class JSRequest {
    public String method;
    public String callBackID;
    public JSONObject params;

    public void parse(String request) throws Exception {
        if (TextUtils.isEmpty(request) || request.trim().length() <= 0) {
            return;
        }

        String[] reqs = request.split(":");

        doCheckParam(reqs);

        /***
         *  reqs[0] is {@link cn.pedant.SafeWebViewBridge.jsbridge.utils.Constants#SCHEMA_HOST}
         */

        this.callBackID = reqs[1];
        this.method = reqs[2];

        String stringParam = reqs[3];

        if (TextUtils.isEmpty(stringParam) || stringParam.trim().length() <= 0) {
            return;
        }

        try {
            stringParam = URLDecoder.decode(stringParam, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("参数错误，无法解码");
        }

        if (Constants.DEFAULT_PARAM.equalsIgnoreCase(stringParam)) {
            return;
        } else {
            this.params = new JSONObject(stringParam);
        }

    }

    private void doCheckParam(String[] requests) throws Exception {
        if (requests == null) {
            throw new IllegalArgumentException("请求非法");
        }
    }
}
