package com.enixma.sample.mobile.data.repository.datasource.remote;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.data.entity.MobileImageEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public interface IMobileWebService {

    @GET
    Observable<List<MobileEntity>> getAllMobile(@Url String url);

    @GET
    Observable<List<MobileImageEntity>> getMobileImages(@Url String url);
}
