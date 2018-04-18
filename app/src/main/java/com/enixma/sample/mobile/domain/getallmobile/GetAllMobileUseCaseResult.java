package com.enixma.sample.mobile.domain.getallmobile;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.domain.UseCase;

import java.util.List;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class GetAllMobileUseCaseResult implements UseCase.Result {
    private GetAllMobileUseCase.Status status;
    private List<MobileEntity> mobileEntities;

    public GetAllMobileUseCase.Status getStatus() {
        return status;
    }

    public void setStatus(GetAllMobileUseCase.Status status) {
        this.status = status;
    }

    public List<MobileEntity> getMobileEntities() {
        return mobileEntities;
    }

    public void setMobileEntities(List<MobileEntity> mobileEntities) {
        this.mobileEntities = mobileEntities;
    }
}
