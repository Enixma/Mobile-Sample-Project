package com.enixma.sample.mobile.domain.sortmobile;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.domain.UseCase;

import java.util.List;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class SortMobileUseCaseRequest implements UseCase.Request {

    private SortMobileUseCase.SortBy sortBy;
    private List<MobileEntity> mobileEntityList;

    public SortMobileUseCase.SortBy getSortBy() {
        return sortBy;
    }

    public void setSortBy(SortMobileUseCase.SortBy sortBy) {
        this.sortBy = sortBy;
    }

    public List<MobileEntity> getMobileEntityList() {
        return mobileEntityList;
    }

    public void setMobileEntityList(List<MobileEntity> mobileEntityList) {
        this.mobileEntityList = mobileEntityList;
    }

}
