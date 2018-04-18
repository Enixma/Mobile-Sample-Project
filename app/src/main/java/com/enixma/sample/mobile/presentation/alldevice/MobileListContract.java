package com.enixma.sample.mobile.presentation.alldevice;

import com.enixma.sample.mobile.data.entity.MobileEntity;

import java.util.List;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public interface MobileListContract {
    interface View {
        void populateList(List<MobileEntity> mobileEntityList);

        void displayNoData();

        void stopRefreshLoading();
    }

    interface Action {
        void getMobileList();

        void downloadMobileList();

        void sortPriceLowToHigh();

        void sortPriceHighToLow();

        void sortRatingFiveToOne();

        void setFavorite(int position);
    }
}
