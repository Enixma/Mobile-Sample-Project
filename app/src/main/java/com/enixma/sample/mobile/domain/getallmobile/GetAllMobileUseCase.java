package com.enixma.sample.mobile.domain.getallmobile;

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

public class GetAllMobileUseCase implements UseCase<GetAllMobileUseCaseRequest, GetAllMobileUseCaseResult> {

    public enum Status{
        SUCCESS,
        NO_DATA_FOUND
    }

    private IMobileRepository mobileRepository;

    @Inject
    public GetAllMobileUseCase(IMobileRepository mobileRepository) {
        this.mobileRepository = mobileRepository;
    }

    public Observable<GetAllMobileUseCaseResult> execute(final GetAllMobileUseCaseRequest request) {
        return mobileRepository.getAllMobile().
                flatMap(new Function<List<MobileEntity>, ObservableSource<GetAllMobileUseCaseResult>>() {
                    @Override
                    public ObservableSource<GetAllMobileUseCaseResult> apply(List<MobileEntity> mobileEntities) throws Exception {

                        GetAllMobileUseCaseResult result = new GetAllMobileUseCaseResult();
                        if(mobileEntities == null || mobileEntities.isEmpty()){
                            result.setStatus(GetAllMobileUseCase.Status.NO_DATA_FOUND);
                            return Observable.just(result);
                        } else{
                            result.setStatus(GetAllMobileUseCase.Status.SUCCESS);
                            result.setMobileEntities(mobileEntities);
                            return Observable.just(result);
                        }

                    }
                });
    }
}
