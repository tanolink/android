package com.lanolink.zouni.result;

import android.util.Log;

import com.lanolink.zouni.data.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * json解析基类
 */
public abstract class BaseResult {

    private final String RESULT_STR = "result";
    private final String MESSAGE_STR = "message";
    protected final String DATA_STR = "data";

    protected String result = null;
    protected String message = null;
    protected JSONObject jobj = null;

    public void fromJSONString(String jsonStr)
            throws JSONException {
        Log.d("JSON Result", jsonStr);
        jobj = new JSONObject(jsonStr);
        this.result = jobj.getString(RESULT_STR);
        this.message = jobj.getString(MESSAGE_STR);
        if (result.equals(Config.RESULT_OK)) {
            parseDetail();
        }
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    protected abstract void parseDetail() throws JSONException;
}
