package com.lanolink.zouni.dao;

import android.content.Context;

import com.lanolink.zouni.business.IControl;

/**
 *
 */
public class ControlDao extends Dao implements IControl {

    public final String TYPE_SOCIAL = "social";
    public final String TYPE_FUND = "fund";
    public final String TYPE_BANK = "bank";
    public final String TYPE_LFSERVICE = "lfsevice";
    public final String TYPE_MORE = "more";

    public ControlDao(Context mContext) {
        super(mContext);
    }


}
