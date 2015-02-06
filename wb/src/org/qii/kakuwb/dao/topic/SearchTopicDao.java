package org.qii.kakuwb.dao.topic;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.qii.kakuwb.bean.MessageBean;
import org.qii.kakuwb.bean.TopicResultListBean;
import org.qii.kakuwb.dao.URLHelper;
import org.qii.kakuwb.support.debug.AppLogger;
import org.qii.kakuwb.support.error.WeiboException;
import org.qii.kakuwb.support.http.HttpMethod;
import org.qii.kakuwb.support.http.HttpUtility;
import org.qii.kakuwb.support.settinghelper.SettingUtility;
import org.qii.kakuwb.support.utils.TimeUtility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: qii
 * Date: 12-9-26
 */
public class SearchTopicDao {

    protected String getUrl() {
        return URLHelper.TOPIC_SEARCH;
    }

    private String getMsgListJson() throws WeiboException {
        String url = getUrl();

        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", access_token);
        map.put("q", q);
        map.put("count", count);
        map.put("page", page);

        String jsonData = HttpUtility.getInstance().executeNormalTask(HttpMethod.Get, url, map);

        return jsonData;
    }

    public TopicResultListBean getGSONMsgList() throws WeiboException {

        String json = getMsgListJson();
        Gson gson = new Gson();

        TopicResultListBean value = null;
        try {
            value = gson.fromJson(json, TopicResultListBean.class);
        } catch (JsonSyntaxException e) {

            AppLogger.e(e.getMessage());
            return null;
        }
        if (value != null && value.getStatuses() != null && value.getStatuses().size() > 0) {
            List<MessageBean> msgList = value.getStatuses();
            Iterator<MessageBean> iterator = msgList.iterator();

            while (iterator.hasNext()) {
                MessageBean msg = iterator.next();
                if (msg.getUser() == null) {
                    iterator.remove();
                } else {
                    msg.getListViewSpannableString();
                    TimeUtility.dealMills(msg);
                }
            }
        }

        return value;
    }

    public SearchTopicDao(String token, String q) {
        this.access_token = token;
        this.q = q;
        this.count = SettingUtility.getMsgCount();
    }

    private String access_token;
    private String q;
    private String count;
    private String page;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
