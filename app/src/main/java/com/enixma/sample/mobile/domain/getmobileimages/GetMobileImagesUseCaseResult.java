package com.enixma.sample.mobile.domain.getmobileimages;

import com.enixma.sample.mobile.data.entity.MobileImageEntity;
import com.enixma.sample.mobile.domain.UseCase;

import java.util.List;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class GetMobileImagesUseCaseResult implements UseCase.Result {
    private GetMobileImagesUseCase.Status status;
    private List<MobileImageEntity> mobileImageEntityList;

    public GetMobileImagesUseCase.Status getStatus() {
        return status;
    }

    public void setStatus(GetMobileImagesUseCase.Status status) {
        this.status = status;
    }

    public List<MobileImageEntity> getMobileImageEntityList() {
        return mobileImageEntityList;
    }

    public void setMobileImageEntityList(List<MobileImageEntity> mobileImageEntityList) {
        this.mobileImageEntityList = mobileImageEntityList;
    }
}
