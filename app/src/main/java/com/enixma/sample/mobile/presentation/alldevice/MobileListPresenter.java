package com.enixma.sample.mobile.presentation.alldevice;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.domain.downloadmobile.DownloadMobileUseCase;
import com.enixma.sample.mobile.domain.downloadmobile.DownloadMobileUseCaseRequest;
import com.enixma.sample.mobile.domain.downloadmobile.DownloadMobileUseCaseResult;
import com.enixma.sample.mobile.domain.getallmobile.GetAllMobileUseCase;
import com.enixma.sample.mobile.domain.getallmobile.GetAllMobileUseCaseRequest;
import com.enixma.sample.mobile.domain.getallmobile.GetAllMobileUseCaseResult;
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

public class MobileListPresenter implements MobileListContract.Action, LifecycleObserver {

    private MobileListContract.View view;
    private GetAllMobileUseCase getAllMobileUseCase;
    private DownloadMobileUseCase downloadMobileUseCase;
    private SaveFavoriteUseCase saveFavoriteUseCase;
    private SortMobileUseCase sortMobileUseCase;
    private Disposable getAllMobileDisposable;
    private Disposable downloadMobileDisposable;
    private Disposable saveFavoriteDisposable;
    private Disposable sortMobileDisposable;
    private boolean isLoading;
    private SortMobileUseCase.SortBy sortBy;
    private List<MobileEntity> mobileEntityList;

    public MobileListPresenter(MobileListContract.View view,
                               GetAllMobileUseCase getAllMobileUseCase,
                               DownloadMobileUseCase downloadMobileUseCase,
                               SaveFavoriteUseCase saveFavoriteUseCase,
                               SortMobileUseCase sortMobileUseCase) {
        this.view = view;
        this.getAllMobileUseCase = getAllMobileUseCase;
        this.downloadMobileUseCase = downloadMobileUseCase;
        this.saveFavoriteUseCase = saveFavoriteUseCase;
        this.sortMobileUseCase = sortMobileUseCase;
    }

    @Override
    public void getMobileList() {

        getAllMobileDisposable = getAllMobileUseCase.execute(new GetAllMobileUseCaseRequest())
                .doOnNext(new Consumer<GetAllMobileUseCaseResult>() {
                    @Override
                    public void accept(GetAllMobileUseCaseResult result) throws Exception {
                        processGetMobileListResult(result);
                    }
                }).subscribe();
    }

    private void processGetMobileListResult(GetAllMobileUseCaseResult result) {
        if (result.getStatus() == GetAllMobileUseCase.Status.SUCCESS) {
            mobileEntityList = result.getMobileEntities();
            sortList();
        } else {
            view.displayNoData();
            downloadMobileList();
        }
    }

    @Override
    public void downloadMobileList() {

        if (isLoading) {
            return;
        }

        isLoading = true;

        downloadMobileDisposable = downloadMobileUseCase.execute(new DownloadMobileUseCaseRequest())
                .doOnNext(new Consumer<DownloadMobileUseCaseResult>() {
                    @Override
                    public void accept(DownloadMobileUseCaseResult result) throws Exception {
                        processDownloadMobileListResult(result);
                    }
                }).subscribe();
    }

    private void processDownloadMobileListResult(DownloadMobileUseCaseResult result) {
        view.stopRefreshLoading();
        isLoading = false;
        if (result.getStatus() != DownloadMobileUseCase.Status.SUCCESS) {
            view.displayNoData();
        }
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

    @Override
    public void setFavorite(int pos) {
        MobileEntity mobileEntity = mobileEntityList.get(pos);
        mobileEntity.setFavorite(!mobileEntity.isFavorite());
        saveFavoriteDisposable = saveFavoriteUseCase.execute(new SaveFavoriteUseCaseRequest(mobileEntity)).subscribe();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onPresenterDestroy() {
        if (getAllMobileDisposable != null) {
            getAllMobileDisposable.dispose();
        }

        if (downloadMobileDisposable != null) {
            downloadMobileDisposable.dispose();
        }

        if (saveFavoriteDisposable != null) {
            saveFavoriteDisposable.dispose();
        }

        if (sortMobileDisposable != null) {
            sortMobileDisposable.dispose();
        }
    }
}
