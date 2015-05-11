package com.lanolink.zouni.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.lanolink.zouni.data.Config;

/**
 * 数据存储
 */
public class CommonUtil {

    private static CommonUtil instance;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private final String FIRSTENTER = "firstEntry"; // the first time you launch the app

    // 寻找系统中有无符合以 name 字符串作为文件名的优先级设置文件
    private CommonUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences(Config.SAVE_COM,
                Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.commit();
    }

    public static synchronized CommonUtil getInstance(Context context) {
        if (null == instance) {
            instance = new CommonUtil(context);
        }
        return instance;
    }

    public void clearAll() {
        mEditor.clear();
        mEditor.commit();
    }

    public boolean isFirstEntry() {
        return mSharedPreferences.getBoolean(FIRSTENTER, true);
    }

    public void setFirstEntry(boolean isFirstEntry) {
        mEditor.putBoolean(FIRSTENTER, isFirstEntry);
        mEditor.commit();
    }

}
