package com.enixma.sample.mobile;

import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.domain.sortmobile.SortMobileUseCase;
import com.enixma.sample.mobile.domain.sortmobile.SortMobileUseCaseRequest;
import com.enixma.sample.mobile.domain.sortmobile.SortMobileUseCaseResult;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by nakarinj on 19/4/2018 AD.
 */

public class SortMobileUseCaseTest {

    SortMobileUseCase useCase;
    List<MobileEntity> mobileEntities;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        useCase = new SortMobileUseCase();
        mobileEntities = new ArrayList<>();

        MobileEntity entityOne = new MobileEntity();
        entityOne.setId(1);
        entityOne.setPrice(10);
        entityOne.setRating(4.55);

        MobileEntity entityTwo = new MobileEntity();
        entityTwo.setId(2);
        entityTwo.setPrice(10.9);
        entityTwo.setRating(1);

        MobileEntity entityThree = new MobileEntity();
        entityThree.setId(3);
        entityThree.setPrice(20);
        entityThree.setRating(1.9);

        MobileEntity entityFour = new MobileEntity();
        entityFour.setId(4);
        entityFour.setPrice(300);
        entityFour.setRating(5);

        MobileEntity entityFive = new MobileEntity();
        entityFive.setId(5);
        entityFive.setPrice(300.99);
        entityFive.setRating(3);

        mobileEntities.add(entityFive);
        mobileEntities.add(entityFour);
        mobileEntities.add(entityOne);
        mobileEntities.add(entityThree);
        mobileEntities.add(entityTwo);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void execute_sortPriceLowToHigh_expectIdOneToFive() throws Exception {
        SortMobileUseCaseRequest request = new SortMobileUseCaseRequest(SortMobileUseCase.SortBy.PRICE_LOW_TO_HIGH, mobileEntities);
        SortMobileUseCaseResult result = useCase.execute(request).blockingSingle();
        assertEquals(result.getMobileEntityList().get(0).getId(), 1);
        assertEquals(result.getMobileEntityList().get(1).getId(), 2);
        assertEquals(result.getMobileEntityList().get(2).getId(), 3);
        assertEquals(result.getMobileEntityList().get(3).getId(), 4);
        assertEquals(result.getMobileEntityList().get(4).getId(), 5);
    }

    @Test
    public void execute_sortPriceHighToLow_expectIdFiveToOne() throws Exception {
        SortMobileUseCaseRequest request = new SortMobileUseCaseRequest(SortMobileUseCase.SortBy.PRICE_HIGH_TO_LOW, mobileEntities);
        SortMobileUseCaseResult result = useCase.execute(request).blockingSingle();
        assertEquals(result.getMobileEntityList().get(0).getId(), 5);
        assertEquals(result.getMobileEntityList().get(1).getId(), 4);
        assertEquals(result.getMobileEntityList().get(2).getId(), 3);
        assertEquals(result.getMobileEntityList().get(3).getId(), 2);
        assertEquals(result.getMobileEntityList().get(4).getId(), 1);
    }

    @Test
    public void execute_sortRatingFiveToOne_expectIdFourOneFiveThreeTwo() throws Exception {
        SortMobileUseCaseRequest request = new SortMobileUseCaseRequest(SortMobileUseCase.SortBy.RATING_FIVE_TO_ONE, mobileEntities);
        SortMobileUseCaseResult result = useCase.execute(request).blockingSingle();
        assertEquals(result.getMobileEntityList().get(0).getId(), 4);
        assertEquals(result.getMobileEntityList().get(1).getId(), 1);
        assertEquals(result.getMobileEntityList().get(2).getId(), 5);
        assertEquals(result.getMobileEntityList().get(3).getId(), 3);
        assertEquals(result.getMobileEntityList().get(4).getId(), 2);
    }

    @Test
    public void execute_sortEmptyListByPriceLowToHigh_expectEmptyList() throws Exception {
        SortMobileUseCaseRequest request = new SortMobileUseCaseRequest(SortMobileUseCase.SortBy.PRICE_LOW_TO_HIGH, new ArrayList<MobileEntity>());
        SortMobileUseCaseResult result = useCase.execute(request).blockingSingle();
        assertTrue(result.getMobileEntityList().isEmpty());

    }

    @Test
    public void execute_sortEmptyListByPriceHighToLow_expectEmptyList() throws Exception {
        SortMobileUseCaseRequest request = new SortMobileUseCaseRequest(SortMobileUseCase.SortBy.PRICE_HIGH_TO_LOW, new ArrayList<MobileEntity>());
        SortMobileUseCaseResult result = useCase.execute(request).blockingSingle();
        assertTrue(result.getMobileEntityList().isEmpty());

    }

    @Test
    public void execute_sortEmptyListByRatingiveToOne_expectEmptyList() throws Exception {
        SortMobileUseCaseRequest request = new SortMobileUseCaseRequest(SortMobileUseCase.SortBy.RATING_FIVE_TO_ONE, new ArrayList<MobileEntity>());
        SortMobileUseCaseResult result = useCase.execute(request).blockingSingle();
        assertTrue(result.getMobileEntityList().isEmpty());

    }
}
