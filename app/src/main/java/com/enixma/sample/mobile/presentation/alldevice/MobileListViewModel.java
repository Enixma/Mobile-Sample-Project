package com.enixma.sample.mobile.presentation.alldevice;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class MobileListViewModel extends ViewModel {

    private ObservableField<Boolean> hasData = new ObservableField<>(true);

    public ObservableField<Boolean> getHasData() {
        return hasData;
    }

    public void setHasData(ObservableField<Boolean> hasData) {
        this.hasData = hasData;
    }
}
