package com.enixma.sample.mobile;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.data.repository.IMobileRepository;
import com.enixma.sample.mobile.domain.getallmobile.GetAllMobileUseCase;
import com.enixma.sample.mobile.domain.getallmobile.GetAllMobileUseCaseRequest;
import com.enixma.sample.mobile.domain.getallmobile.GetAllMobileUseCaseResult;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Created by nakarinj on 19/4/2018 AD.
 */

public class GetAllMobileUseCaseTest {

    @Mock
    IMobileRepository mobileRepository;

    GetAllMobileUseCase useCase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        useCase = new GetAllMobileUseCase(mobileRepository);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void execute_whenOneEntityExists_expectSuccess() throws Exception {
        GetAllMobileUseCaseRequest request = new GetAllMobileUseCaseRequest();

        List<MobileEntity> mobileEntities = new ArrayList<>();
        MobileEntity mobileEntity = new MobileEntity();
        mobileEntity.setId(1);
        mobileEntities.add(mobileEntity);
        Observable<List<MobileEntity>> mobileObservable = Observable.just(mobileEntities);

        Mockito.when(mobileRepository.getAllMobile()).thenReturn(mobileObservable);
        GetAllMobileUseCaseResult result = useCase.execute(request).blockingSingle();
        assertEquals(result.getStatus(), GetAllMobileUseCase.Status.SUCCESS);
        assertEquals(result.getMobileEntities().get(0).getId(), 1);
    }

    @Test
    public void execute_whenNoDataExists_expectNoDataFound() throws Exception {
        GetAllMobileUseCaseRequest request = new GetAllMobileUseCaseRequest();

        List<MobileEntity> mobileEntities = new ArrayList<>();
        Observable<List<MobileEntity>> mobileObservable = Observable.just(mobileEntities);

        Mockito.when(mobileRepository.getAllMobile()).thenReturn(mobileObservable);
        GetAllMobileUseCaseResult result = useCase.execute(request).blockingSingle();
        assertEquals(result.getStatus(), GetAllMobileUseCase.Status.NO_DATA_FOUND);
        assertNull(result.getMobileEntities());
    }
}
