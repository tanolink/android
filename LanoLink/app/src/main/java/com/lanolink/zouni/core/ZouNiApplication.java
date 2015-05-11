package com.lanolink.zouni.core;

import android.app.Application;

/**
 * Created by wangduo on 15/5/11.
 */
public class ZouNiApplication extends Application {

    private boolean debug = true;

    private static ZouNiApplication app;

    public static ZouNiApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public boolean isDebug() {
        return debug;
    }
}
