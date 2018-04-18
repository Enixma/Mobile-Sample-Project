package com.enixma.sample.mobile.data.di;

import android.content.Context;

import com.enixma.sample.mobile.data.repository.datasource.sharedprefs.AppSharedPreferences;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */
@Module
public class AppSharedPreferencesModule {

    private AppSharedPreferences appSharedPreferences;

    public AppSharedPreferencesModule(Context context) {
        appSharedPreferences = new AppSharedPreferences(context);
    }

    @Provides
    AppSharedPreferences provideAppSharedPreferences() {
        return appSharedPreferences;
    }
}