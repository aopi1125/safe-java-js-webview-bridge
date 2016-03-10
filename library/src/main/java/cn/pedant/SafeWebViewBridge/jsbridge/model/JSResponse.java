package cn.pedant.SafeWebViewBridge.jsbridge.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Harbor on 2016/3/10.
 */
public class JSResponse {

    public boolean success = true;
    public String error;
    public JSONObject datas;

    public boolean finished = false;//标识是否处理完业务，才可以回传数据给JavaScript

    public String encode() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("success", success);
            jsonObject.put("error", error);
            jsonObject.put("datas", datas);
        } catch (JSONException e) {
            e.printStackTrace();
            success = false;

        }

        return jsonObject.toString();

    }
}
