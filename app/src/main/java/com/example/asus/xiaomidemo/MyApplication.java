package com.example.asus.xiaomidemo;

import android.app.Application;

import com.lzy.okgo.OkGo;

/**
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
//        AppUtil.getInstalledApp(this);
    }
}
