package com.enixma.sample.mobile.domain.downloadmobile;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.domain.UseCase;

import java.util.List;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class DownloadMobileUseCaseResult implements UseCase.Result {

    private DownloadMobileUseCase.Status status;
    private List<MobileEntity> mobileEntities;

    public DownloadMobileUseCase.Status getStatus() {
        return status;
    }

    public void setStatus(DownloadMobileUseCase.Status status) {
        this.status = status;
    }

    public List<MobileEntity> getMobileEntities() {
        return mobileEntities;
    }

    public void setMobileEntities(List<MobileEntity> mobileEntities) {
        this.mobileEntities = mobileEntities;
    }
}
