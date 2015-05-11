package com.lanolink.zouni.util;

import android.text.TextUtils;

import com.lanolink.zouni.result.BaseResult;

import org.json.JSONException;

import java.text.ParseException;

public class JsonParser {

    /**
     * @param result
     * @param str
     * @return
     * @throws java.text.ParseException
     */
    public static BaseResult toResult(BaseResult result, final String str) throws ParseException {
        if (TextUtils.isEmpty(str)) {
            throw new ParseException("JSON parse error! empty string", 0);
        }
        try {
            result.fromJSONString(str);
        } catch (JSONException e) {
            throw new ParseException("JSON parse error!; " + str, 0);
        }
        return result;
    }
}
