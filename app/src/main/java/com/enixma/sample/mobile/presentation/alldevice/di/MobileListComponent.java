package com.enixma.sample.mobile.presentation.alldevice.di;

import com.enixma.sample.mobile.data.di.MobileDataModule;
import com.enixma.sample.mobile.data.di.ServiceFactoryModule;
import com.enixma.sample.mobile.presentation.alldevice.MobileListFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

@Singleton
@Component(modules = {ServiceFactoryModule.class, MobileDataModule.class, MobileListModule.class})
public interface MobileListComponent {
    void inject(MobileListFragment mobileListFragment);
}