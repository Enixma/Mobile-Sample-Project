package com.enixma.sample.mobile.domain.sortmobile;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.domain.UseCase;

import java.util.List;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class SortMobileUseCaseResult implements UseCase.Result {

    private List<MobileEntity> mobileEntityList;

    public List<MobileEntity> getMobileEntityList() {
        return mobileEntityList;
    }

    public void setMobileEntityList(List<MobileEntity> mobileEntityList) {
        this.mobileEntityList = mobileEntityList;
    }
}
