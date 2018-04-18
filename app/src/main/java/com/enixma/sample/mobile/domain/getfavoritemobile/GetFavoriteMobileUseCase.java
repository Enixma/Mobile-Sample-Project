package com.enixma.sample.mobile.domain.getfavoritemobile;

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

public class GetFavoriteMobileUseCase implements UseCase<GetFavoriteMobileUseCaseRequest, GetFavoriteMobileUseCaseResult> {

    public enum Status{
        SUCCESS,
        NO_DATA_FOUND
    }

    private IMobileRepository mobileRepository;

    @Inject
    public GetFavoriteMobileUseCase(IMobileRepository mobileRepository) {
        this.mobileRepository = mobileRepository;
    }

    public Observable<GetFavoriteMobileUseCaseResult> execute(final GetFavoriteMobileUseCaseRequest request) {
        return mobileRepository.getFavoriteMobile().
                flatMap(new Function<List<MobileEntity>, ObservableSource<GetFavoriteMobileUseCaseResult>>() {
                    @Override
                    public ObservableSource<GetFavoriteMobileUseCaseResult> apply(List<MobileEntity> mobileEntities) throws Exception {
                        GetFavoriteMobileUseCaseResult result = new GetFavoriteMobileUseCaseResult();
                        if(mobileEntities == null || mobileEntities.isEmpty()){
                            result.setStatus(GetFavoriteMobileUseCase.Status.NO_DATA_FOUND);
                            return Observable.just(result);
                        } else{
                            result.setStatus(GetFavoriteMobileUseCase.Status.SUCCESS);
                            result.setMobileEntities(mobileEntities);
                            return Observable.just(result);
                        }
                    }
                });
    }
}
