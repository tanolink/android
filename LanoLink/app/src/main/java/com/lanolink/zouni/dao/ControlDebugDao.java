package com.lanolink.zouni.dao;

import android.content.Context;

import com.lanolink.zouni.business.IControl;

/**
 *
 */
public class ControlDebugDao extends Dao implements IControl {

    public ControlDebugDao(Context mContext) {
        super(mContext);
        String s = "{\"message\":\"success\",\"result\":0,\"list\":["
                + "{\"detailurl\":\"111\",\"imgid\":2,\"imgurl\":\"22222222222\",\"seqno\":1,\"title\":\"ad\"},"
                + "{\"detailurl\":\"http://\",\"imgid\":1,\"imgurl\":\"221211111111111\",\"seqno\":2,\"title\":\"advert\"}]}";

        String ret = "{   \"result\":\"0\",\"message\":\"\",\"data\":["
                + "{\"imgid\":\"1\",\"seqno\":1,\"imgurl\":\"http://www.z4root8.com/zb_users/upload/2012/12/2012123164013721.png\",\"title\":\"掌上社保\",\"detailurl\":\"http://115.238.37.4:8080/ecpc/ps.do?operate=0050&imei=0000&tag=0&news_id=86&accessible=true\"},"
                + "{\"imgid\":\"2\",\"seqno\":2,\"imgurl\":\"http://www.z4root8.com/zb_users/upload/2012/12/2012123164013721.png\",\"title\":\"拾味馆\",\"detailurl\":\"http://115.238.37.4:8080/ecpc/ps.do?operate=0050&imei=0000&tag=0&news_id=86&accessible=true\"},"
                + "{\"imgid\":\"3\",\"seqno\":3,\"imgurl\":\"http://www.z4root8.com/zb_users/upload/2012/12/2012123164013721.png\",\"title\":\"无锡市民卡\",\"detailurl\":\"http://115.238.37.4:8080/ecpc/ps.do?operate=0050&imei=0000&tag=0&news_id=86&accessible=true\"}]}";

    }

}
