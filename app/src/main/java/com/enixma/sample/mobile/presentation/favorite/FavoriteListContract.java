package com.enixma.sample.mobile.presentation.favorite;

import com.enixma.sample.mobile.data.entity.MobileEntity;

import java.util.List;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public interface FavoriteListContract {
    interface View{
        void populateList(List<MobileEntity> mobileEntityList);
        void displayNoData();
    }

    interface Action {
        void getFavoriteList();
        void removeFromFavorite(int position);
        void sortPriceLowToHigh();
        void sortPriceHighToLow();
        void sortRatingFiveToOne();
    }
}
