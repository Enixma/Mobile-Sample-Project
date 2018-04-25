package com.enixma.sample.mobile.presentation;

import android.support.multidex.MultiDexApplication;

import io.realm.Realm;

/**
 * Created by nakarinj on 24/4/2018 AD.
 */

public class MobileApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
    }
}
