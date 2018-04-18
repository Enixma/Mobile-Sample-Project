package com.enixma.sample.mobile.presentation.detail;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.enixma.sample.mobile.domain.downloadmobileimages.DownloadMobileImagesUseCase;
import com.enixma.sample.mobile.domain.downloadmobileimages.DownloadMobileImagesUseCaseRequest;
import com.enixma.sample.mobile.domain.downloadmobileimages.DownloadMobileImagesUseCaseResult;
import com.enixma.sample.mobile.domain.getmobileimages.GetMobileImagesUseCaseRequest;
import com.enixma.sample.mobile.domain.getmobileimages.GetMobileImagesUseCase;
import com.enixma.sample.mobile.domain.getmobileimages.GetMobileImagesUseCaseResult;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class DetailPresenter implements DetailContract.Action, LifecycleObserver {

    private DetailContract.View view;
    private GetMobileImagesUseCase getMobileImagesUseCase;
    private DownloadMobileImagesUseCase downloadMobileImagesUseCase;
    private Disposable getMobileImagesDisposable;
    private Disposable downloadMobileImagesDisposable;

    public DetailPresenter(DetailContract.View view,
                           GetMobileImagesUseCase getMobileImagesUseCase,
                           DownloadMobileImagesUseCase downloadMobileImagesUseCase) {
        this.view = view;
        this.getMobileImagesUseCase = getMobileImagesUseCase;
        this.downloadMobileImagesUseCase = downloadMobileImagesUseCase;
    }

    @Override
    public void getMobileImages(final int mobileId) {
        getMobileImagesDisposable = getMobileImagesUseCase.execute(new GetMobileImagesUseCaseRequest(mobileId))
                .doOnNext(new Consumer<GetMobileImagesUseCaseResult>() {
                    @Override
                    public void accept(GetMobileImagesUseCaseResult result) throws Exception {
                        processGetImagesResult(mobileId, result);
                    }
                }).subscribe();
    }

    private void processGetImagesResult(int mobileId, GetMobileImagesUseCaseResult result) {
        if (result.getStatus() == GetMobileImagesUseCase.Status.SUCCESS) {
            view.populateList(result.getMobileImageEntityList());
            downloadImages(mobileId);
        } else {
            downloadImages(mobileId);
        }
    }

    private void downloadImages(int mobileId) {
        downloadMobileImagesDisposable = downloadMobileImagesUseCase.execute(new DownloadMobileImagesUseCaseRequest(mobileId))
                .doOnNext(new Consumer<DownloadMobileImagesUseCaseResult>() {
                    @Override
                    public void accept(DownloadMobileImagesUseCaseResult result) throws Exception {
                        processDownloadImagesResult(result);
                    }
                }).subscribe();
    }

    private void processDownloadImagesResult(DownloadMobileImagesUseCaseResult result) {
        if (result.getStatus() == DownloadMobileImagesUseCase.Status.SUCCESS) {
            view.populateList(result.getMobileImageEntityList());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onPresenterDestroy() {
        if (getMobileImagesDisposable != null) {
            getMobileImagesDisposable.dispose();
        }
        if (downloadMobileImagesDisposable != null) {
            downloadMobileImagesDisposable.dispose();
        }
    }
}
