package com.enixma.sample.mobile.presentation.favorite.mapper;

import android.content.Context;

import com.enixma.sample.mobile.R;
import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.presentation.favorite.FavoriteListItem;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class EntityToListItemMapper {
    public static FavoriteListItem map(Context context, MobileEntity entity) {
        return new FavoriteListItem(entity.getId(),
                entity.getBrand(),
                entity.getName(),
                entity.getDescription(),
                entity.getThumbImageURL(),
                "" + entity.getPrice(),
                context.getString(R.string.rating_prefix) + entity.getRating(),
                entity.isFavorite());
    }
}
