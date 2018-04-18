package com.enixma.sample.mobile;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.data.repository.IMobileRepository;
import com.enixma.sample.mobile.domain.savefavorite.SaveFavoriteUseCase;
import com.enixma.sample.mobile.domain.savefavorite.SaveFavoriteUseCaseRequest;
import com.enixma.sample.mobile.domain.savefavorite.SaveFavoriteUseCaseResult;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by nakarinj on 19/4/2018 AD.
 */

public class SaveFavoriteUseCaseTest {

    @Mock
    IMobileRepository mobileRepository;

    SaveFavoriteUseCase useCase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        useCase = new SaveFavoriteUseCase(mobileRepository);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void execute_requestSave_expectComplete() throws Exception {
        SaveFavoriteUseCaseRequest request = new SaveFavoriteUseCaseRequest(new MobileEntity());
        Mockito.when(mobileRepository.saveFavoriteStatus(request.getMobileEntity())).thenReturn(Completable.complete());
        SaveFavoriteUseCaseResult result = useCase.execute(request).blockingSingle();
        assertNotNull(result);
    }
}
