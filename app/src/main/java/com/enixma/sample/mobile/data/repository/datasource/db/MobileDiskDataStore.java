package com.enixma.sample.mobile.data.repository.datasource.db;

import android.content.Context;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.data.entity.MobileImageEntity;
import com.enixma.sample.mobile.data.entity.MobileRealmModule;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class MobileDiskDataStore extends RealmDataStore implements IMobileDiskDataStore {

    private RealmConfiguration realmConfiguration;
    private Context context;

    public MobileDiskDataStore(Context context) {
        this.context = context;
        Realm.init(context);
        this.realmConfiguration = new RealmConfiguration.Builder()
                .name(context.getPackageName().concat(".mobile.realm"))
                .modules(new MobileRealmModule())
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    public RealmConfiguration getRealmConfiguration() {
        return realmConfiguration;
    }

    @Override
    public Observable<List<MobileEntity>> getAllMobile() {
        List<MobileEntity> results = findAllCopies(MobileEntity.class);
        return Observable.just(results);
    }

    @Override
    public Observable<List<MobileEntity>> getFavoriteMobile() {
        List<MobileEntity> results = findAllCopies(MobileEntity.class, "isFavorite" , true);
        return Observable.just(results);
    }

    @Override
    public Observable<List<MobileImageEntity>> getMobileImages(int mobileId) {
        List<MobileImageEntity> results = findAllCopies(MobileImageEntity.class, "mobileId", mobileId);
        return Observable.just(results);
    }

    @Override
    public Completable updateMobile(List<MobileEntity> mobileEntities) {

        for (MobileEntity mobileEntity : mobileEntities) {
            List<MobileEntity> result = findFirstCopy(MobileEntity.class, "id", mobileEntity.getId());
            if (result.isEmpty()) {
                copyOrUpdateToRealm(mobileEntity);
            } else {
                MobileEntity original = result.get(0);
                original.setBrand(mobileEntity.getBrand());
                original.setName(mobileEntity.getName());
                original.setDescription(mobileEntity.getDescription());
                original.setThumbImageURL(mobileEntity.getThumbImageURL());
                original.setRating(mobileEntity.getRating());
                original.setPrice(mobileEntity.getPrice());
                copyOrUpdateToRealm(original);
            }
        }

        return Completable.complete();
    }


    @Override
    public Completable updateMobileImage(List<MobileImageEntity> mobileImageEntities) {

        if (!mobileImageEntities.isEmpty()) {
            List<MobileEntity> result = findFirstCopy(MobileEntity.class, "id", mobileImageEntities.get(0).getMobileId());
            if (!result.isEmpty()) {
                MobileEntity mobileEntity = result.get(0);
                RealmList<MobileImageEntity> imageEntities = new RealmList<MobileImageEntity>();
                imageEntities.addAll(mobileImageEntities);
                mobileEntity.setMobileImageEntities(imageEntities);
                copyOrUpdateToRealm(mobileEntity);
            }
        }

        return Completable.complete();
    }

    @Override
    public Completable saveFavoriteStatus(MobileEntity mobileEntity) {
        copyOrUpdateToRealm(mobileEntity);
        return Completable.complete();
    }

}
