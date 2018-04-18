package com.enixma.sample.mobile.presentation.pager.di;

import com.enixma.sample.mobile.data.di.AppSharedPreferencesModule;
import com.enixma.sample.mobile.presentation.pager.MobilePagerFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

@Singleton
@Component(modules = {AppSharedPreferencesModule.class})
public interface MobilePagerComponent {
    void inject(MobilePagerFragment mobilePagerFragment);
}