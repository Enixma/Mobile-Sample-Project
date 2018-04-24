package com.enixma.sample.mobile.data.repository.datasource.db;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.rx.CollectionChange;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */
public abstract class RealmDataStore {

    private Realm realm;

    public abstract RealmConfiguration getRealmConfiguration();

    private void beginTransaction() {
        if (realm == null || realm.isClosed()) {
            getRealmInstance();
        }
        if (!realm.isInTransaction()) {
            realm.beginTransaction();
        }
    }

    private void getRealmInstance() {
        if (getRealmConfiguration() != null) {
            realm = Realm.getInstance(getRealmConfiguration());
        } else {
            realm = Realm.getDefaultInstance();
        }
    }

    public <E extends RealmObject> void copyOrUpdateToRealm(E object) {
        beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
    }

    public <E extends RealmObject> List<E> findFirstCopy(Class<E> realmClass) {
        List<E> results = new ArrayList<>();
        getRealmInstance();
        E realmObject = realm.where(realmClass).findFirst();
        if (realmObject != null) {
            E result = realm.copyFromRealm(realmObject);
            results.add(result);
        }
        return results;
    }

    public <E extends RealmObject> List<E> findFirstCopy(Class<E> realmClass, String fieldName, String value) {
        List<E> results = new ArrayList<>();
        getRealmInstance();
        E realmObject = realm.where(realmClass).equalTo(fieldName, value).findFirst();
        if (realmObject != null) {
            E result = realm.copyFromRealm(realmObject);
            results.add(result);
        }

        return results;
    }

    public <E extends RealmObject> List<E> findFirstCopy(Class<E> realmClass, String fieldName, int value) {
        List<E> results = new ArrayList<>();
        getRealmInstance();
        E realmObject = realm.where(realmClass).equalTo(fieldName, value).findFirst();
        if (realmObject != null) {
            E result = realm.copyFromRealm(realmObject);
            results.add(result);
        }

        return results;
    }

    public <E extends RealmObject> Observable<List<E>> findAllCopies(Class<E> realmClass) {
        getRealmInstance();
        return realm.where(realmClass)
                .findAllAsync()
                .asChangesetObservable()
                .flatMap(new Function<CollectionChange<RealmResults<E>>, ObservableSource<List<E>>>() {
                    @Override
                    public ObservableSource<List<E>> apply(CollectionChange<RealmResults<E>> realmResultsCollectionChange) throws Exception {
                        List<E> results = new ArrayList<>();
                        if (realmResultsCollectionChange.getCollection() != null && !realmResultsCollectionChange.getCollection().isEmpty()) {
                            results = realm.copyFromRealm(realmResultsCollectionChange.getCollection());
                        }
                        return Observable.just(results);
                    }
                });

    }

    public <E extends RealmObject> Observable<List<E>> findAllCopies(Class<E> realmClass, String fieldName, String value) {
        getRealmInstance();
        return realm.where(realmClass)
                .equalTo(fieldName, value)
                .findAllAsync()
                .asChangesetObservable()
                .flatMap(new Function<CollectionChange<RealmResults<E>>, ObservableSource<List<E>>>() {
                    @Override
                    public ObservableSource<List<E>> apply(CollectionChange<RealmResults<E>> realmResultsCollectionChange) throws Exception {
                        List<E> results = new ArrayList<>();
                        if (realmResultsCollectionChange.getCollection() != null && !realmResultsCollectionChange.getCollection().isEmpty()) {
                            results = realm.copyFromRealm(realmResultsCollectionChange.getCollection());
                        }
                        return Observable.just(results);
                    }
                });
    }

    public <E extends RealmObject> Observable<List<E>> findAllCopies(Class<E> realmClass, String fieldName, int value) {
        getRealmInstance();
        return realm.where(realmClass)
                .equalTo(fieldName, value)
                .findAllAsync()
                .asChangesetObservable()
                .flatMap(new Function<CollectionChange<RealmResults<E>>, ObservableSource<List<E>>>() {
                    @Override
                    public ObservableSource<List<E>> apply(CollectionChange<RealmResults<E>> realmResultsCollectionChange) throws Exception {
                        List<E> results = new ArrayList<>();
                        if (realmResultsCollectionChange.getCollection() != null && !realmResultsCollectionChange.getCollection().isEmpty()) {
                            results = realm.copyFromRealm(realmResultsCollectionChange.getCollection());
                        }
                        return Observable.just(results);
                    }
                });
    }

    public <E extends RealmObject> Observable<List<E>> findAllCopies(Class<E> realmClass, String fieldName, boolean value) {
        getRealmInstance();
        return realm.where(realmClass)
                .equalTo(fieldName, value)
                .findAllAsync()
                .asChangesetObservable()
                .flatMap(new Function<CollectionChange<RealmResults<E>>, ObservableSource<List<E>>>() {
                    @Override
                    public ObservableSource<List<E>> apply(CollectionChange<RealmResults<E>> realmResultsCollectionChange) throws Exception {
                        List<E> results = new ArrayList<>();
                        if (realmResultsCollectionChange.getCollection() != null && !realmResultsCollectionChange.getCollection().isEmpty()) {
                            results = realm.copyFromRealm(realmResultsCollectionChange.getCollection());
                        }
                        return Observable.just(results);
                    }
                });
    }

}