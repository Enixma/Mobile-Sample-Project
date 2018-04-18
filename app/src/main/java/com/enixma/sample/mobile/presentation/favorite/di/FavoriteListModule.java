package com.enixma.sample.mobile.presentation.favorite.di;

import com.enixma.sample.mobile.domain.getfavoritemobile.GetFavoriteMobileUseCase;
import com.enixma.sample.mobile.domain.sortmobile.SortMobileUseCase;
import com.enixma.sample.mobile.presentation.favorite.FavoriteListContract;
import com.enixma.sample.mobile.presentation.favorite.FavoriteListPresenter;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

@Module
public class FavoriteListModule {

    private FavoriteListContract.View view;

    public FavoriteListModule(FavoriteListContract.View view) {
        this.view = view;
    }

    @Provides
    @Inject
    public FavoriteListContract.Action provideFavoriteListPresenter(GetFavoriteMobileUseCase getFavoriteMobileUseCase) {
        return new FavoriteListPresenter(view, getFavoriteMobileUseCase, new SortMobileUseCase());
    }

}
