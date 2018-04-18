package com.enixma.sample.mobile.presentation.detail.di;

import com.enixma.sample.mobile.domain.downloadmobileimages.DownloadMobileImagesUseCase;
import com.enixma.sample.mobile.domain.getmobileimages.GetMobileImagesUseCase;
import com.enixma.sample.mobile.presentation.detail.DetailContract;
import com.enixma.sample.mobile.presentation.detail.DetailPresenter;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

@Module
public class DetailModule {

    private DetailContract.View view;

    public DetailModule(DetailContract.View view) {
        this.view = view;
    }

    @Provides
    @Inject
    public DetailContract.Action provideDetailPresenter(GetMobileImagesUseCase getFavoriteMobileUseCase, DownloadMobileImagesUseCase downloadMobileImagesUseCase) {
        return new DetailPresenter(view, getFavoriteMobileUseCase, downloadMobileImagesUseCase);
    }

}