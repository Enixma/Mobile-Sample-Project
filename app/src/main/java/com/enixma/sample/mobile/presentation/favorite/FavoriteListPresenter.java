package com.enixma.sample.mobile.presentation.favorite;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.domain.getfavoritemobile.GetFavoriteMobileUseCase;
import com.enixma.sample.mobile.domain.getfavoritemobile.GetFavoriteMobileUseCaseRequest;
import com.enixma.sample.mobile.domain.getfavoritemobile.GetFavoriteMobileUseCaseResult;
import com.enixma.sample.mobile.domain.savefavorite.SaveFavoriteUseCase;
import com.enixma.sample.mobile.domain.savefavorite.SaveFavoriteUseCaseRequest;
import com.enixma.sample.mobile.domain.savefavorite.SaveFavoriteUseCaseResult;
import com.enixma.sample.mobile.domain.sortmobile.SortMobileUseCase;
import com.enixma.sample.mobile.domain.sortmobile.SortMobileUseCaseRequest;
import com.enixma.sample.mobile.domain.sortmobile.SortMobileUseCaseResult;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class FavoriteListPresenter implements FavoriteListContract.Action, LifecycleObserver {

    private FavoriteListContract.View view;
    private GetFavoriteMobileUseCase getFavoriteListUseCase;
    private SaveFavoriteUseCase saveFavoriteUseCase;
    private SortMobileUseCase sortMobileUseCase;
    private Disposable getFavoriteListDisposable;
    private Disposable saveFavoriteDisposable;
    private Disposable sortMobileDisposable;
    private SortMobileUseCase.SortBy sortBy;
    private List<MobileEntity> mobileEntityList;

    public FavoriteListPresenter(FavoriteListContract.View view,
                                 GetFavoriteMobileUseCase getFavoriteListUseCase,
                                 SaveFavoriteUseCase saveFavoriteUseCase,
                                 SortMobileUseCase sortMobileUseCase) {
        this.view = view;
        this.getFavoriteListUseCase = getFavoriteListUseCase;
        this.saveFavoriteUseCase = saveFavoriteUseCase;
        this.sortMobileUseCase = sortMobileUseCase;
    }

    @Override
    public void getFavoriteList() {

        getFavoriteListDisposable = getFavoriteListUseCase.execute(new GetFavoriteMobileUseCaseRequest())
                .doOnNext(new Consumer<GetFavoriteMobileUseCaseResult>() {
                    @Override
                    public void accept(GetFavoriteMobileUseCaseResult result) throws Exception {
                        processGetMobileListResult(result);
                    }
                }).subscribe();
    }

    private void processGetMobileListResult(GetFavoriteMobileUseCaseResult result) {
        if (result.getStatus() == GetFavoriteMobileUseCase.Status.SUCCESS) {
            mobileEntityList = result.getMobileEntities();
            sortList();
        } else {
            view.displayNoData();
        }
    }

    @Override
    public void removeFromFavorite(int position) {

        MobileEntity mobileEntity = mobileEntityList.get(position);
        mobileEntity.setFavorite(false);
        saveFavoriteDisposable = saveFavoriteUseCase.execute(new SaveFavoriteUseCaseRequest(mobileEntity))
                .doOnNext(new Consumer<SaveFavoriteUseCaseResult>() {
                    @Override
                    public void accept(SaveFavoriteUseCaseResult saveFavoriteUseCaseResult) throws Exception {
                        getFavoriteList();
                    }
                }).subscribe();
    }

    @Override
    public void sortPriceLowToHigh() {
        sortBy = SortMobileUseCase.SortBy.PRICE_LOW_TO_HIGH;
        sortList();
    }

    @Override
    public void sortPriceHighToLow() {
        sortBy = SortMobileUseCase.SortBy.PRICE_HIGH_TO_LOW;
        sortList();
    }

    @Override
    public void sortRatingFiveToOne() {
        sortBy = SortMobileUseCase.SortBy.RATING_FIVE_TO_ONE;
        sortList();
    }

    private void sortList() {
        if (mobileEntityList == null || mobileEntityList.isEmpty()) {
            return;
        }

        sortMobileDisposable = sortMobileUseCase.execute(new SortMobileUseCaseRequest(sortBy, mobileEntityList))
                .doOnNext(new Consumer<SortMobileUseCaseResult>() {
                    @Override
                    public void accept(SortMobileUseCaseResult sortMobileUseCaseResult) throws Exception {
                        view.populateList(sortMobileUseCaseResult.getMobileEntityList());
                    }
                }).subscribe();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onPresenterDestroy() {
        if (getFavoriteListDisposable != null) {
            getFavoriteListDisposable.dispose();
        }

        if (saveFavoriteDisposable != null) {
            saveFavoriteDisposable.dispose();
        }

        if (sortMobileDisposable != null) {
            sortMobileDisposable.dispose();
        }
    }
}
