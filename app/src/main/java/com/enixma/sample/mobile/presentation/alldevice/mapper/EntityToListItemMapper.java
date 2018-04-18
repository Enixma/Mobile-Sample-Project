package com.enixma.sample.mobile.presentation.alldevice.mapper;

import android.content.Context;

import com.enixma.sample.mobile.R;
import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.presentation.alldevice.MobileListItem;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class EntityToListItemMapper {

    public static MobileListItem map(Context context, MobileEntity entity) {
        return new MobileListItem(entity.getId(),
                entity.getBrand(),
                entity.getName(),
                entity.getDescription(),
                entity.getThumbImageURL(),
                context.getString(R.string.price_prefix) + entity.getPrice(),
                context.getString(R.string.rating_prefix) + entity.getRating(),
                entity.isFavorite());
    }
}
