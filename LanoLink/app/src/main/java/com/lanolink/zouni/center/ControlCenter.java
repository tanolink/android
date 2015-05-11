package com.lanolink.zouni.center;

import android.content.Context;

import com.lanolink.zouni.core.ZouNiApplication;
import com.lanolink.zouni.business.IControl;
import com.lanolink.zouni.dao.ControlDao;
import com.lanolink.zouni.dao.ControlDebugDao;

/**
 * 业务请求控制类
 */
public class ControlCenter {

    private static ControlCenter instance;
    private ZouNiApplication app;
    private IControl dao;

    private ControlCenter(Context mContext) {
        app = (ZouNiApplication) mContext.getApplicationContext();
        if (!app.isDebug()) {
            dao = new ControlDao(mContext);
        } else {
            dao = new ControlDebugDao(mContext);
        }
    }

    public static synchronized ControlCenter getInstance(Context mContext) {
        if (null == instance) {
            instance = new ControlCenter(mContext);
        }
        return instance;
    }

}
