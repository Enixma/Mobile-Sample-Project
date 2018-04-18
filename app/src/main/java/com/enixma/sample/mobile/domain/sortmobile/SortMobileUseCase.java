package com.enixma.sample.mobile.domain.sortmobile;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.domain.UseCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class SortMobileUseCase implements UseCase<SortMobileUseCaseRequest, SortMobileUseCaseResult> {

    public enum SortBy{
        PRICE_LOW_TO_HIGH,
        PRICE_HIGH_TO_LOW,
        RATING_FIVE_TO_ONE
    }

    public Observable<SortMobileUseCaseResult> execute(final SortMobileUseCaseRequest request) {

        SortMobileUseCaseResult result = new SortMobileUseCaseResult();

        if(request.getMobileEntityList() == null || request.getMobileEntityList().isEmpty()){
            result.setMobileEntityList(new ArrayList<MobileEntity>());
            return Observable.just(result);
        } else {

            switch (request.getSortBy()) {
                case PRICE_LOW_TO_HIGH:
                    result.setMobileEntityList(getListSortedByPriceLowToHigh(request.getMobileEntityList()));
                    break;
                case PRICE_HIGH_TO_LOW:
                    result.setMobileEntityList(getListSortedByPriceHighToLow(request.getMobileEntityList()));
                    break;
                case RATING_FIVE_TO_ONE:
                    result.setMobileEntityList(getListSortedByRatingFiveToOne(request.getMobileEntityList()));
                    break;
                default:
                    result.setMobileEntityList(getListSortedByPriceLowToHigh(request.getMobileEntityList()));
                    break;
            }

            return Observable.just(result);
        }
    }

    private ArrayList<MobileEntity> getListSortedByPriceLowToHigh(List<MobileEntity> mobileEntities){
        ArrayList<MobileEntity> sortedList = new ArrayList<>();
        Collections.sort(mobileEntities, new PriceSorter());
        sortedList.addAll(mobileEntities);

        return sortedList;
    }

    private ArrayList<MobileEntity> getListSortedByPriceHighToLow(List<MobileEntity> mobileEntities){
        ArrayList<MobileEntity> sortedList = new ArrayList<>();
        Collections.sort(mobileEntities, new PriceSorter());
        Collections.reverse(mobileEntities);
        sortedList.addAll(mobileEntities);

        return sortedList;
    }

    private ArrayList<MobileEntity> getListSortedByRatingFiveToOne(List<MobileEntity> mobileEntities){
        ArrayList<MobileEntity> sortedList = new ArrayList<>();
        Collections.sort(mobileEntities, new RatingSorter());
        Collections.reverse(mobileEntities);
        sortedList.addAll(mobileEntities);

        return sortedList;
    }
}
