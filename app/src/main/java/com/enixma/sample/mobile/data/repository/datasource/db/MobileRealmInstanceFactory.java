package com.enixma.sample.mobile.data.repository.datasource.db;

import android.content.Context;

import com.enixma.sample.mobile.data.entity.MobileRealmModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by nakarinj on 24/4/2018 AD.
 */

public class MobileRealmInstanceFactory {

    public static Realm getRealmInstance(Context context) {

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(context.getPackageName().concat(".mobile.realm"))
                .modules(new MobileRealmModule())
                .deleteRealmIfMigrationNeeded()
                .build();
        return Realm.getInstance(realmConfiguration);
    }
}
