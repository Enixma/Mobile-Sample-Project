package com.enixma.sample.mobile.data.repository.datasource.remote;

import android.content.Context;

import com.enixma.sample.mobile.R;
import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.data.entity.MobileImageEntity;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class MobileCloudDataStore implements IMobileCloudDataStore {

    private IMobileWebService mobileWebService;
    private String BASE_URL;
    private String IMAGE_END_POINT;


    public MobileCloudDataStore(Context context, IMobileWebService mobileWebService) {
        this.mobileWebService = mobileWebService;
        BASE_URL = context.getString(R.string.BASE_URL);
        IMAGE_END_POINT = context.getString(R.string.IMAGE_END_POINT);

    }

    @Override
    public Observable<List<MobileEntity>> getAllMobile() {
        return mobileWebService.getAllMobile(BASE_URL);
    }

    @Override
    public Observable<List<MobileImageEntity>> getMobileImages(int mobileId) {
        return mobileWebService.getMobileImages(BASE_URL + mobileId + IMAGE_END_POINT);
    }

}
