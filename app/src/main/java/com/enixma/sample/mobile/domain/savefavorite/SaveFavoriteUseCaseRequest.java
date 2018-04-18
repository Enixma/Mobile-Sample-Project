package com.enixma.sample.mobile.domain.savefavorite;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.domain.UseCase;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class SaveFavoriteUseCaseRequest implements UseCase.Request {
    private MobileEntity mobileEntity;

    public MobileEntity getMobileEntity() {
        return mobileEntity;
    }

    public void setMobileEntity(MobileEntity mobileEntity) {
        this.mobileEntity = mobileEntity;
    }
}
