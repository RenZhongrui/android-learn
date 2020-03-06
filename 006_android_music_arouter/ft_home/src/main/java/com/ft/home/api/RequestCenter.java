package com.ft.home.api;


import com.ft.home.model.discory.BaseRecommandModel;
import com.ft.home.model.discory.BaseRecommandMoreModel;
import com.ft.home.model.friend.BaseFriendModel;
import com.lib.network.CommonOkHttpClient;
import com.lib.network.listener.DisposeDataHandle;
import com.lib.network.listener.DisposeDataListener;
import com.lib.network.request.CommonRequest;
import com.lib.network.request.RequestParams;

/**
 * 请求中心
 */
public class RequestCenter {

    static class HttpConstants {
        private static final String ROOT_URL = "http://imooc.com/api";
        //private static final String ROOT_URL = "http://39.97.122.129";

        /**
         * 首页请求接口
         */
        private static String HOME_RECOMMAND = ROOT_URL + "/module_voice/home_recommand";

        private static String HOME_RECOMMAND_MORE = ROOT_URL + "/module_voice/home_recommand_more";

        private static String HOME_FRIEND = ROOT_URL + "/module_voice/home_friend";

    }

    //根据参数发送所有post请求
    public static void getRequest(String url, RequestParams params, DisposeDataListener listener,
                                  Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.
                createGetRequest(url, params), new DisposeDataHandle(listener, clazz));
    }

    public static void requestRecommandData(DisposeDataListener listener) {
        RequestCenter.getRequest(HttpConstants.HOME_RECOMMAND, null, listener,
                BaseRecommandModel.class);
    }

    public static void requestRecommandMore(DisposeDataListener listener) {
        RequestCenter.getRequest(HttpConstants.HOME_RECOMMAND_MORE, null, listener,
                BaseRecommandMoreModel.class);
    }

    public static void requestFriendData(DisposeDataListener listener) {
        RequestCenter.getRequest(HttpConstants.HOME_FRIEND, null, listener, BaseFriendModel.class);
    }

}
