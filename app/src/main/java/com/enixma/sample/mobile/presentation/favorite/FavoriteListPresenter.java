package com.enixma.sample.mobile.presentation.favorite;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.domain.getfavoritemobile.GetFavoriteMobileUseCase;
import com.enixma.sample.mobile.domain.getfavoritemobile.GetFavoriteMobileUseCaseRequest;
import com.enixma.sample.mobile.domain.getfavoritemobile.GetFavoriteMobileUseCaseResult;
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
    private SortMobileUseCase sortMobileUseCase;
    private Disposable getFavoriteListDisposable;
    private Disposable sortMobileDisposable;
    private boolean isLoading;
    private SortMobileUseCase.SortBy sortBy;
    private List<MobileEntity> mobileEntityList;

    public FavoriteListPresenter(FavoriteListContract.View view,
                                 GetFavoriteMobileUseCase getFavoriteListUseCase,
                                 SortMobileUseCase sortMobileUseCase) {
        this.view = view;
        this.getFavoriteListUseCase = getFavoriteListUseCase;
        this.sortMobileUseCase = sortMobileUseCase;
    }

    @Override
    public void getFavoriteList() {
        if (isLoading) {
            return;
        }
        isLoading = true;

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
            isLoading = false;
        }
    }

    @Override
    public void sortPriceLowToHigh() {
        sortBy = SortMobileUseCase.SortBy.PRICE_LOW_TO_HIGH;
        getFavoriteList();
    }

    @Override
    public void sortPriceHighToLow() {
        sortBy = SortMobileUseCase.SortBy.PRICE_HIGH_TO_LOW;
        getFavoriteList();
    }

    @Override
    public void sortRatingFiveToOne() {
        sortBy = SortMobileUseCase.SortBy.RATING_FIVE_TO_ONE;
        getFavoriteList();
    }

    private void sortList() {
        if (mobileEntityList == null || mobileEntityList.isEmpty()) {
            isLoading = false;
            return;
        }

        sortMobileDisposable = sortMobileUseCase.execute(new SortMobileUseCaseRequest(sortBy, mobileEntityList))
                .doOnNext(new Consumer<SortMobileUseCaseResult>() {
                    @Override
                    public void accept(SortMobileUseCaseResult sortMobileUseCaseResult) throws Exception {
                        view.populateList(sortMobileUseCaseResult.getMobileEntityList());
                        isLoading = false;
                    }
                }).subscribe();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onPresenterDestroy() {
        if (getFavoriteListDisposable != null) {
            getFavoriteListDisposable.dispose();
        }
        if (sortMobileDisposable != null) {
            sortMobileDisposable.dispose();
        }
    }
}