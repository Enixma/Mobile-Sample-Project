package com.enixma.sample.mobile.presentation.alldevice.di;

import com.enixma.sample.mobile.domain.downloadmobile.DownloadMobileUseCase;
import com.enixma.sample.mobile.domain.getallmobile.GetAllMobileUseCase;
import com.enixma.sample.mobile.domain.savefavorite.SaveFavoriteUseCase;
import com.enixma.sample.mobile.domain.sortmobile.SortMobileUseCase;
import com.enixma.sample.mobile.presentation.alldevice.MobileListContract;
import com.enixma.sample.mobile.presentation.alldevice.MobileListPresenter;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

@Module
public class MobileListModule {

    private MobileListContract.View view;

    public MobileListModule(MobileListContract.View view) {
        this.view = view;

    }

    @Provides
    @Inject
    public MobileListContract.Action provideMobileListPresenter(GetAllMobileUseCase getAllMobileUseCase,
                                                                DownloadMobileUseCase downloadMobileUseCase,
                                                                SaveFavoriteUseCase saveFavoriteUseCase) {


        return new MobileListPresenter(view, getAllMobileUseCase, downloadMobileUseCase, saveFavoriteUseCase, new SortMobileUseCase());
    }

}
