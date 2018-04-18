package com.enixma.sample.mobile;

import com.enixma.sample.mobile.data.entity.MobileImageEntity;
import com.enixma.sample.mobile.data.repository.IMobileRepository;
import com.enixma.sample.mobile.domain.downloadmobileimages.DownloadMobileImagesUseCase;
import com.enixma.sample.mobile.domain.downloadmobileimages.DownloadMobileImagesUseCaseRequest;
import com.enixma.sample.mobile.domain.downloadmobileimages.DownloadMobileImagesUseCaseResult;
import com.enixma.sample.mobile.domain.getmobileimages.GetMobileImagesUseCase;
import com.enixma.sample.mobile.domain.getmobileimages.GetMobileImagesUseCaseRequest;
import com.enixma.sample.mobile.domain.getmobileimages.GetMobileImagesUseCaseResult;

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

public class DownloadMobileImagesUseCaseTest {
    @Mock
    IMobileRepository mobileRepository;

    DownloadMobileImagesUseCase useCase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        useCase = new DownloadMobileImagesUseCase(mobileRepository);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void execute_whenOneEntityExists_expectSuccess() throws Exception {
        DownloadMobileImagesUseCaseRequest request = new DownloadMobileImagesUseCaseRequest(1);

        List<MobileImageEntity> mobileImageEntities = new ArrayList<>();
        MobileImageEntity mobileImageEntity = new MobileImageEntity();
        mobileImageEntity.setMobileId(1);
        mobileImageEntities.add(mobileImageEntity);
        Observable<List<MobileImageEntity>> mobileImageObservable = Observable.just(mobileImageEntities);

        Mockito.when(mobileRepository.downloadMobileImages(1)).thenReturn(mobileImageObservable);
        DownloadMobileImagesUseCaseResult result = useCase.execute(request).blockingSingle();
        assertEquals(result.getStatus(), DownloadMobileImagesUseCase.Status.SUCCESS);
        assertEquals(result.getMobileImageEntityList().get(0).getMobileId(), 1);
    }

    @Test
    public void execute_whenNoDataExists_expectNoDataFound() throws Exception {
        DownloadMobileImagesUseCaseRequest request = new DownloadMobileImagesUseCaseRequest(1);

        List<MobileImageEntity> mobileEntities = new ArrayList<>();
        Observable<List<MobileImageEntity>> mobileObservable = Observable.just(mobileEntities);

        Mockito.when(mobileRepository.downloadMobileImages(1)).thenReturn(mobileObservable);
        DownloadMobileImagesUseCaseResult result = useCase.execute(request).blockingSingle();
        assertEquals(result.getStatus(), DownloadMobileImagesUseCase.Status.NO_DATA_FOUND);
        assertNull(result.getMobileImageEntityList());
    }
}
