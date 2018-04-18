package com.enixma.sample.mobile.presentation.detail.mapper;

import com.enixma.sample.mobile.presentation.alldevice.MobileListItem;
import com.enixma.sample.mobile.presentation.detail.DetailModel;
import com.enixma.sample.mobile.presentation.favorite.FavoriteListItem;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class ListItemToModelMapper {

    public static DetailModel map(MobileListItem item){
        return new DetailModel(item.getId(), item.getBrand(), item.getName(), item.getDescription(), item.getRating(), item.getPrice());
    }

    public static DetailModel map(FavoriteListItem item){
        return new DetailModel(item.getId(), item.getBrand(), item.getName(), item.getDescription(), item.getRating(), item.getPrice());
    }

}
