package com.example.liangzihong.myweather;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by Liang Zihong on 2018/3/21.
 */

public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context=getApplicationContext();
        LitePal.initialize(context);
        LitePal.getDatabase();
    }

    public static Context getContext(){
        return context;
    }

}
