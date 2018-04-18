package com.enixma.sample.mobile.domain.downloadmobile;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.data.repository.IMobileRepository;
import com.enixma.sample.mobile.domain.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class DownloadMobileUseCase implements UseCase<DownloadMobileUseCaseRequest, DownloadMobileUseCaseResult> {

    public enum Status{
        SUCCESS,
        NO_DATA_FOUND
    }

    private IMobileRepository mobileRepository;

    @Inject
    public DownloadMobileUseCase(IMobileRepository mobileRepository) {
        this.mobileRepository = mobileRepository;
    }

    public Observable<DownloadMobileUseCaseResult> execute(final DownloadMobileUseCaseRequest request) {
        return mobileRepository.downloadAllMobile().
                flatMap(new Function<List<MobileEntity>, ObservableSource<DownloadMobileUseCaseResult>>() {
                    @Override
                    public ObservableSource<DownloadMobileUseCaseResult> apply(List<MobileEntity> mobileEntities) throws Exception {

                        DownloadMobileUseCaseResult result = new DownloadMobileUseCaseResult();
                        if(mobileEntities == null || mobileEntities.isEmpty()){
                            result.setStatus(Status.NO_DATA_FOUND);
                            return Observable.just(result);
                        } else{
                            result.setStatus(Status.SUCCESS);
                            result.setMobileEntities(mobileEntities);
                            return Observable.just(result);
                        }

                    }
                });
    }
}
