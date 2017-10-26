package com.bwie.yuekaomoni.model;


import com.bwie.yuekaomoni.Bean;
import com.bwie.yuekaomoni.utils.GsonObjectCallback;
import com.bwie.yuekaomoni.utils.OkHttp3Utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 作者：张玉轲
 * 时间：2017/10/25
 */

public class HttpModel {

    public void getData(final ModleListener listener){
        OkHttp3Utils.getInstance().doGet("http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=20&gender=2&ts=1871746850&page=1", new GsonObjectCallback<Bean>() {
            @Override
            public void onUi(Bean bean) {
                listener.getListener(bean);
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }


    public interface ModleListener{
        void getListener(Bean bean);
    }
}
