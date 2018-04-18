package com.enixma.sample.mobile.domain.getmobileimages;

import com.enixma.sample.mobile.domain.UseCase;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class GetMobileImagesUseCasRequest implements UseCase.Request {
    private int mobileId;

    public int getMobileId() {
        return mobileId;
    }

    public void setMobileId(int mobileId) {
        this.mobileId = mobileId;
    }
}
