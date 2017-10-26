package com.bwie.yuekaomoni;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import imageloader.bwie.com.imageloaderlibrary.UtilImage;

/**
 * 作者：张玉轲
 * 时间：2017/10/25
 */

public class MyApp extends Application{
    public static MyApp mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        ImageLoaderConfiguration configuration= UtilImage.getConfiguration(this);
        ImageLoader.getInstance().init(configuration);
    }
    public static MyApp getInstance() {
        return mInstance;
    }
}
