package com.enixma.sample.mobile.domain.downloadmobileimages;

import com.enixma.sample.mobile.data.entity.MobileImageEntity;
import com.enixma.sample.mobile.domain.UseCase;

import java.util.List;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class DownloadMobileImagesUseCaseResult implements UseCase.Result {

    private DownloadMobileImagesUseCase.Status status;
    private List<MobileImageEntity> mobileImageEntityList;

    public DownloadMobileImagesUseCase.Status getStatus() {
        return status;
    }

    public void setStatus(DownloadMobileImagesUseCase.Status status) {
        this.status = status;
    }

    public List<MobileImageEntity> getMobileImageEntityList() {
        return mobileImageEntityList;
    }

    public void setMobileImageEntityList(List<MobileImageEntity> mobileImageEntityList) {
        this.mobileImageEntityList = mobileImageEntityList;
    }
}
