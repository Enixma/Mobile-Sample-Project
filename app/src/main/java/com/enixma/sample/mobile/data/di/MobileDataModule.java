package com.enixma.sample.mobile.data.di;

import android.content.Context;

import com.enixma.sample.mobile.data.repository.IMobileRepository;
import com.enixma.sample.mobile.data.repository.MobileRepository;
import com.enixma.sample.mobile.data.repository.datasource.db.IMobileDiskDataStore;
import com.enixma.sample.mobile.data.repository.datasource.db.MobileDiskDataStore;
import com.enixma.sample.mobile.data.repository.datasource.remote.IMobileCloudDataStore;
import com.enixma.sample.mobile.data.repository.datasource.remote.IMobileWebService;
import com.enixma.sample.mobile.data.repository.datasource.remote.MobileCloudDataStore;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import retrofit2.Retrofit;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */
@Module
public class MobileDataModule {

    private Context context;
    private Realm realm;

    public MobileDataModule(Context context, Realm realm){
        this.context = context;
        this.realm = realm;
    }

    @Provides
    @Singleton
    IMobileDiskDataStore provideMobileDiskDataStore() {
        return new MobileDiskDataStore(realm);
    }


    @Provides
    @Singleton
    @Inject
    IMobileWebService provideMobileWebService(Retrofit restAdapter) {
        return restAdapter.create(IMobileWebService.class);
    }

    @Provides
    @Singleton
    @Inject
    IMobileCloudDataStore provideMobileCloudDataStore(IMobileWebService mobileWebService) {
        return new MobileCloudDataStore(context, mobileWebService);
    }

    @Provides
    @Singleton
    @Inject
    IMobileRepository provideMobileRepository(IMobileDiskDataStore mobileDiskDataStore, IMobileCloudDataStore mobileCloudDataStore) {
        return new MobileRepository(mobileDiskDataStore, mobileCloudDataStore);
    }
}