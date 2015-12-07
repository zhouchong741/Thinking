package com.zc741.thinking;

import cn.sharesdk.framework.authorize.AuthorizeAdapter;

/**
 * Created by chong on 2015/12/7.
 */
public class MyAdapter extends AuthorizeAdapter {
    @Override
    public void onCreate() {
        //隐藏标题栏的 “Powered by ShareSDK”
        hideShareSDKLogo();
    }
}
