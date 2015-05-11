package com.lanolink.zouni.net;

import android.content.Context;

import com.lanolink.zouni.exception.NetworkException;

/**
 *
 */
public class NetService {

    // http://221.123.184.166:8028/hbss/bank/3001/1/1.0.0/1/14021812553656895210/480_800/0,0,0
    // http://221.123.184.166:8028/hbss/bank/3001/1/1.0.0/1/480_800/0,0,0/1
    private final String url = "";

    private static NetService instance = null;
    private static Context mContext = null;

    public static NetService getInstance() {
        if (null == instance) {
            instance = new NetService();
        }
        return instance;
    }

    private NetService() {
    }

    public synchronized String doHttpRequest(String type, String httpRequest)
            throws NetworkException {
        synchronized (instance) {
            StringBuffer sb = new StringBuffer(url);
            sb.append(type);
            sb.append("/");
            sb.append(httpRequest);
            return NetUtils.openUrl(sb.toString(), "get");
        }
    }

}
