package com.enixma.sample.mobile.data.repository;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.data.entity.MobileImageEntity;
import com.enixma.sample.mobile.data.repository.datasource.db.IMobileDiskDataStore;
import com.enixma.sample.mobile.data.repository.datasource.remote.IMobileCloudDataStore;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class MobileRepository implements IMobileRepository {

    private IMobileDiskDataStore mobileDiskDataStore;
    private IMobileCloudDataStore mobileCloudDataStore;


    public MobileRepository(IMobileDiskDataStore mobileDiskDataStore, IMobileCloudDataStore mobileCloudDataStore) {
        this.mobileDiskDataStore = mobileDiskDataStore;
        this.mobileCloudDataStore = mobileCloudDataStore;
    }

    @Override
    public Observable<List<MobileEntity>> getAllMobile() {
        return mobileDiskDataStore.getAllMobile();
    }

    @Override
    public Observable<List<MobileEntity>> getFavoriteMobile() {
        return mobileDiskDataStore.getFavoriteMobile();
    }

    @Override
    public Observable<List<MobileEntity>> downloadAllMobile() {
        return mobileCloudDataStore.getAllMobile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .onErrorReturn(new Function<Throwable, List<MobileEntity>>() {
                    @Override
                    public List<MobileEntity> apply(Throwable throwable) throws Exception {
                        return new ArrayList<MobileEntity>();
                    }
                }).flatMap(new Function<List<MobileEntity>, ObservableSource<List<MobileEntity>>>() {
                    @Override
                    public ObservableSource<List<MobileEntity>> apply(List<MobileEntity> mobileEntities) throws Exception {
                        return mobileDiskDataStore.updateMobile(mobileEntities).andThen(Observable.just(mobileEntities));
                    }
                });
    }

    @Override
    public Observable<List<MobileImageEntity>> getMobileImages(int mobileId) {
        return mobileDiskDataStore.getMobileImages(mobileId);
    }

    @Override
    public Observable<List<MobileImageEntity>> downloadMobileImages(final int mobileId) {
        return mobileCloudDataStore.getMobileImages(mobileId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .onErrorReturn(new Function<Throwable, List<MobileImageEntity>>() {
                    @Override
                    public List<MobileImageEntity> apply(Throwable throwable) throws Exception {
                        return new ArrayList<MobileImageEntity>();
                    }
                }).flatMap(new Function<List<MobileImageEntity>, ObservableSource<List<MobileImageEntity>>>() {
                    @Override
                    public ObservableSource<List<MobileImageEntity>> apply(List<MobileImageEntity> mobileImageEntities) throws Exception {
                        return mobileDiskDataStore.updateMobileImage(mobileImageEntities).andThen(Observable.just(mobileImageEntities));
                    }
                });
    }

    @Override
    public Completable saveFavoriteStatus(MobileEntity mobileEntity) {
        return mobileDiskDataStore.saveFavoriteStatus(mobileEntity);
    }
}
