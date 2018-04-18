package com.enixma.sample.mobile.data.entity;

import io.realm.annotations.RealmModule;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */
@RealmModule(library = true, classes = {
        MobileEntity.class,
        MobileImageEntity.class
}
)
public class MobileRealmModule {
}
