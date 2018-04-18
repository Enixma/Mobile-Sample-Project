package com.enixma.sample.mobile.presentation.detail.di;

import com.enixma.sample.mobile.data.di.MobileDataModule;
import com.enixma.sample.mobile.data.di.ServiceFactoryModule;
import com.enixma.sample.mobile.presentation.detail.DetailFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

@Singleton
@Component(modules = {ServiceFactoryModule.class, MobileDataModule.class, DetailModule.class})
public interface DetailComponent {
    void inject(DetailFragment detailFragment);
}

