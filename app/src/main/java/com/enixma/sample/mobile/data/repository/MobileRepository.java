package com.enixma.sample.mobile.data.repository;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.data.entity.MobileImageEntity;
import com.enixma.sample.mobile.data.repository.datasource.db.IMobileDiskDataStore;
import com.enixma.sample.mobile.data.repository.datasource.remote.IMobileCloudDataStore;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
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
                }).doOnNext(new Consumer<List<MobileEntity>>() {
                    @Override
                    public void accept(List<MobileEntity> mobileEntities) throws Exception {
                        updateAndRetrieveAllMobileFromDB(mobileEntities);
                    }
                });
    }

    private Observable<List<MobileEntity>> updateAndRetrieveAllMobileFromDB(List<MobileEntity> mobileEntities){
        return mobileDiskDataStore.updateMobile(mobileEntities).andThen(mobileDiskDataStore.getAllMobile());
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
                }).doOnNext(new Consumer<List<MobileImageEntity>>() {
                    @Override
                    public void accept(List<MobileImageEntity> mobileImageEntities) throws Exception {
                        updateAndRetrieveMobileImagesFromDB(mobileId, mobileImageEntities);
                    }
                });
    }

    private Observable<List<MobileImageEntity>> updateAndRetrieveMobileImagesFromDB(int mobileId, List<MobileImageEntity> mobileEntities){
        return mobileDiskDataStore.updateMobileImage(mobileEntities).andThen(mobileDiskDataStore.getMobileImages(mobileId));
    }

    @Override
    public Completable saveFavoriteStatus(MobileEntity mobileEntity) {
        return mobileDiskDataStore.saveFavoriteStatus(mobileEntity);
    }
}
