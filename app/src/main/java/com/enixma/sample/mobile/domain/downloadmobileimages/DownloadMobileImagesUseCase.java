package com.enixma.sample.mobile.domain.downloadmobileimages;

import com.enixma.sample.mobile.data.entity.MobileImageEntity;
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

public class DownloadMobileImagesUseCase implements UseCase<DownloadMobileImagesUseCaseRequest, DownloadMobileImagesUseCaseResult> {

    public enum Status{
        SUCCESS,
        NO_DATA_FOUND
    }

    private IMobileRepository mobileRepository;

    @Inject
    public DownloadMobileImagesUseCase(IMobileRepository mobileRepository) {
        this.mobileRepository = mobileRepository;
    }

    public Observable<DownloadMobileImagesUseCaseResult> execute(final DownloadMobileImagesUseCaseRequest request) {
        return mobileRepository.downloadMobileImages(request.getMobileId()).
                flatMap(new Function<List<MobileImageEntity>, ObservableSource<DownloadMobileImagesUseCaseResult>>() {
                    @Override
                    public ObservableSource<DownloadMobileImagesUseCaseResult> apply(List<MobileImageEntity> mobileImageEntities) throws Exception {
                        DownloadMobileImagesUseCaseResult result = new DownloadMobileImagesUseCaseResult();
                        if(mobileImageEntities == null || mobileImageEntities.isEmpty()){
                            result.setStatus(DownloadMobileImagesUseCase.Status.NO_DATA_FOUND);
                            return Observable.just(result);
                        } else{
                            result.setStatus(DownloadMobileImagesUseCase.Status.SUCCESS);
                            result.setMobileImageEntityList(mobileImageEntities);
                            return Observable.just(result);
                        }
                    }
                });
    }
}
