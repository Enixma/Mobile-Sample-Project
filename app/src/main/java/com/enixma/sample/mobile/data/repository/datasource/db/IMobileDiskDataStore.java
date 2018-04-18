package com.enixma.sample.mobile.data.repository.datasource.db;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.data.entity.MobileImageEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public interface IMobileDiskDataStore {

    Observable<List<MobileEntity>> getAllMobile();

    Observable<List<MobileEntity>> getFavoriteMobile();

    Observable<List<MobileImageEntity>> getMobileImages(int mobileId);

    Completable updateMobile(List<MobileEntity> mobileEntities);

    Completable updateMobileImage(List<MobileImageEntity> mobileImageEntities);

    Completable saveFavoriteStatus(MobileEntity mobileEntity);

}
