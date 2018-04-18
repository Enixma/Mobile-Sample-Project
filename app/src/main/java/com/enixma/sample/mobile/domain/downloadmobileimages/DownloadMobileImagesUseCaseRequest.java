package com.enixma.sample.mobile.domain.downloadmobileimages;

import com.enixma.sample.mobile.domain.UseCase;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class DownloadMobileImagesUseCaseRequest implements UseCase.Request {
    private int mobileId;

    public int getMobileId() {
        return mobileId;
    }

    public void setMobileId(int mobileId) {
        this.mobileId = mobileId;
    }
}
