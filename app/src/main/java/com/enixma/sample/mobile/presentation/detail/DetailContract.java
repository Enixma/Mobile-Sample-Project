package com.enixma.sample.mobile.presentation.detail;

import com.enixma.sample.mobile.data.entity.MobileImageEntity;

import java.util.List;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public interface DetailContract {
    interface View {
        void populateList(List<MobileImageEntity> mobileImageEntities);
    }

    interface Action {
        void getMobileImages(int mobileId);
        void downloadImages(int mobileId);
    }
}
