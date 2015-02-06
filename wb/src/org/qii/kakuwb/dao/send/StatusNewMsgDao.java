package org.qii.kakuwb.dao.send;

import org.qii.kakuwb.bean.GeoBean;
import org.qii.kakuwb.dao.URLHelper;
import org.qii.kakuwb.support.error.WeiboException;
import org.qii.kakuwb.support.file.FileUploaderHttpHelper;
import org.qii.kakuwb.support.http.HttpMethod;
import org.qii.kakuwb.support.http.HttpUtility;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * User: qii
 * Date: 12-7-29
 */
public class StatusNewMsgDao {

    private String access_token;
    private String pic;
    private GeoBean geoBean;

    public StatusNewMsgDao setGeoBean(GeoBean geoBean) {
        this.geoBean = geoBean;
        return this;
    }

    public StatusNewMsgDao setPic(String pic) {
        this.pic = pic;
        return this;
    }

    public StatusNewMsgDao(String access_token) {

        this.access_token = access_token;
    }

    public boolean sendNewMsg(String str, FileUploaderHttpHelper.ProgressListener listener)
            throws WeiboException {

        if (!TextUtils.isEmpty(pic)) {
            return sendNewMsgWithPic(str, listener);
        }
        String url = URLHelper.STATUSES_UPDATE;
        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", access_token);
        map.put("status", str);
        if (geoBean != null) {
            map.put("lat", String.valueOf(geoBean.getLat()));
            map.put("long", String.valueOf(geoBean.getLon()));
        }

        HttpUtility.getInstance().executeNormalTask(HttpMethod.Post, url, map);
        return true;
    }

    private boolean sendNewMsgWithPic(String str, FileUploaderHttpHelper.ProgressListener listener)
            throws WeiboException {
        String url = URLHelper.STATUSES_UPLOAD;
        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", access_token);
        map.put("status", str);
        if (geoBean != null) {
            map.put("lat", String.valueOf(geoBean.getLat()));
            map.put("long", String.valueOf(geoBean.getLon()));
        }

        return HttpUtility.getInstance().executeUploadTask(url, map, pic, "pic", listener);
    }
}
