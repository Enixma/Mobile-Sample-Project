package com.enixma.sample.mobile.data.repository;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.data.entity.MobileImageEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public interface IMobileRepository {

    Observable<List<MobileEntity>> getAllMobile();

    Observable<List<MobileEntity>> getFavoriteMobile();

    Observable<List<MobileEntity>> downloadAllMobile();

    Observable<List<MobileImageEntity>> getMobileImages(int mobileId);

    Observable<List<MobileImageEntity>> downloadMobileImages(int mobileId);

    Completable saveFavoriteStatus(MobileEntity mobileEntity);
}
