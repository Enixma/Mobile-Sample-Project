package com.enixma.sample.mobile.data.repository.datasource.db;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */
public abstract class RealmDataStore {

    private Realm realm;

    public abstract RealmConfiguration getRealmConfiguration();

    private void beginTransaction() {
        if (realm == null || realm.isClosed()) {
            if (getRealmConfiguration() != null) {
                realm = Realm.getInstance(getRealmConfiguration());
            } else {
                realm = Realm.getDefaultInstance();
            }
        }

        if (!realm.isInTransaction()) {
            realm.beginTransaction();
        }
    }

    private void commitTransaction() {
        realm.commitTransaction();
        realm.close();
    }

    public <E extends RealmObject> void copyOrUpdateToRealm(E object) {
        beginTransaction();
        realm.copyToRealmOrUpdate(object);
        commitTransaction();
    }

    public <E extends RealmObject> void copyToRealm(E object) {
        beginTransaction();
        realm.copyToRealm(object);
        commitTransaction();
    }

    public <E extends RealmObject> List<E> findFirstCopy(Class<E> realmClass) {
        List<E> results = new ArrayList<>();
        beginTransaction();
        E realmObject = realm.where(realmClass).findFirst();
        if (realmObject != null) {
            E result = realm.copyFromRealm(realmObject);
            results.add(result);
        }
        commitTransaction();
        return results;
    }

    public <E extends RealmObject> List<E> findFirstCopy(Class<E> realmClass, String fieldName, String value) {
        List<E> results = new ArrayList<>();
        beginTransaction();
        E realmObject = realm.where(realmClass).equalTo(fieldName, value).findFirst();
        if (realmObject != null) {
            E result = realm.copyFromRealm(realmObject);
            results.add(result);
        }
        commitTransaction();

        return results;
    }

    public <E extends RealmObject> List<E> findFirstCopy(Class<E> realmClass, String fieldName, int value) {
        List<E> results = new ArrayList<>();
        beginTransaction();
        E realmObject = realm.where(realmClass).equalTo(fieldName, value).findFirst();
        if (realmObject != null) {
            E result = realm.copyFromRealm(realmObject);
            results.add(result);
        }
        commitTransaction();

        return results;
    }

    public <E extends RealmObject> List<E> findAllCopies(Class<E> realmClass) {
        List<E> results = new ArrayList<>();
        beginTransaction();
        RealmResults<E> realmObjectList = realm.where(realmClass).findAll();
        if (realmObjectList != null && !realmObjectList.isEmpty()) {
            results = realm.copyFromRealm(realmObjectList);
        }
        commitTransaction();

        return results;
    }

    public <E extends RealmObject> List<E> findAllCopies(Class<E> realmClass, String fieldName, String value) {
        List<E> results = new ArrayList<>();
        beginTransaction();
        RealmResults<E> realmObjectList = realm.where(realmClass).equalTo(fieldName, value).findAll();
        if (realmObjectList != null && !realmObjectList.isEmpty()) {
            results = realm.copyFromRealm(realmObjectList);
        }
        commitTransaction();

        return results;
    }

    public <E extends RealmObject> List<E> findAllCopies(Class<E> realmClass, String fieldName, int value) {
        List<E> results = new ArrayList<>();
        beginTransaction();
        RealmResults<E> realmObjectList = realm.where(realmClass).equalTo(fieldName, value).findAll();
        if (realmObjectList != null && !realmObjectList.isEmpty()) {
            results = realm.copyFromRealm(realmObjectList);
        }
        commitTransaction();

        return results;
    }

    public <E extends RealmObject> List<E> findAllCopies(Class<E> realmClass, String fieldName, boolean value) {
        List<E> results = new ArrayList<>();
        beginTransaction();
        RealmResults<E> realmObjectList = realm.where(realmClass).equalTo(fieldName, value).findAll();
        if (realmObjectList != null && !realmObjectList.isEmpty()) {
            results = realm.copyFromRealm(realmObjectList);
        }
        commitTransaction();

        return results;
    }

}