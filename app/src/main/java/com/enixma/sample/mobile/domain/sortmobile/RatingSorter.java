package com.enixma.sample.mobile.domain.sortmobile;

import com.enixma.sample.mobile.data.entity.MobileEntity;

import java.util.Comparator;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class RatingSorter implements Comparator<MobileEntity> {
    public int compare(MobileEntity one, MobileEntity another) {
        return  Double.compare(one.getRating(), another.getRating());
    }
}
