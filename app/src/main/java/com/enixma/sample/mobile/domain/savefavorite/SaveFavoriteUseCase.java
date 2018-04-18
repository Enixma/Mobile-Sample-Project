package com.enixma.sample.mobile.domain.savefavorite;

import com.enixma.sample.mobile.data.repository.IMobileRepository;
import com.enixma.sample.mobile.domain.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */
public class SaveFavoriteUseCase implements UseCase<SaveFavoriteUseCaseRequest, SaveFavoriteUseCaseResult> {

    private IMobileRepository mobileRepository;

    @Inject
    public SaveFavoriteUseCase(IMobileRepository mobileRepository) {
        this.mobileRepository = mobileRepository;
    }

    public Observable<SaveFavoriteUseCaseResult> execute(final SaveFavoriteUseCaseRequest request) {

        return mobileRepository.saveFavoriteStatus(request.getMobileEntity())
                .andThen(Observable.just(new SaveFavoriteUseCaseResult()));

    }
}
