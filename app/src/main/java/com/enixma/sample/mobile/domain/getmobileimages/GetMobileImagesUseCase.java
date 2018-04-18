package com.enixma.sample.mobile.domain.getmobileimages;

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

public class GetMobileImagesUseCase implements UseCase<GetMobileImagesUseCaseRequest, GetMobileImagesUseCaseResult> {
    public enum Status{
        SUCCESS,
        NO_DATA_FOUND
    }

    private IMobileRepository mobileRepository;

    @Inject
    public GetMobileImagesUseCase(IMobileRepository mobileRepository) {
        this.mobileRepository = mobileRepository;
    }

    public Observable<GetMobileImagesUseCaseResult> execute(final GetMobileImagesUseCaseRequest request) {
        return mobileRepository.getMobileImages(request.getMobileId()).
                flatMap(new Function<List<MobileImageEntity>, ObservableSource<GetMobileImagesUseCaseResult>>() {
                    @Override
                    public ObservableSource<GetMobileImagesUseCaseResult> apply(List<MobileImageEntity> mobileImageEntities) throws Exception {
                        GetMobileImagesUseCaseResult result = new GetMobileImagesUseCaseResult();
                        if(mobileImageEntities == null || mobileImageEntities.isEmpty()){
                            result.setStatus(GetMobileImagesUseCase.Status.NO_DATA_FOUND);
                            return Observable.just(result);
                        } else{
                            result.setStatus(GetMobileImagesUseCase.Status.SUCCESS);
                            result.setMobileImageEntityList(mobileImageEntities);
                            return Observable.just(result);
                        }
                    }
                });
    }
}
