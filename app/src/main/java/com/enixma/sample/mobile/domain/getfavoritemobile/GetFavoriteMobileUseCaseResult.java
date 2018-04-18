package com.enixma.sample.mobile.domain.getfavoritemobile;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.domain.UseCase;

import java.util.List;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class GetFavoriteMobileUseCaseResult implements UseCase.Result {
    private GetFavoriteMobileUseCase.Status status;
    private List<MobileEntity> mobileEntities;


    public GetFavoriteMobileUseCase.Status getStatus() {
        return status;
    }

    public void setStatus(GetFavoriteMobileUseCase.Status status) {
        this.status = status;
    }

    public List<MobileEntity> getMobileEntities() {
        return mobileEntities;
    }

    public void setMobileEntities(List<MobileEntity> mobileEntities) {
        this.mobileEntities = mobileEntities;
    }
}
