package com.enixma.sample.mobile.data.repository.datasource.remote;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.data.entity.MobileImageEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public interface IMobileCloudDataStore {

    Observable<List<MobileEntity>> getAllMobile();

    Observable<List<MobileImageEntity>> getMobileImages(int mobileId);
}
