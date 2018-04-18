package com.enixma.sample.mobile.domain;

import io.reactivex.Observable;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */
public interface UseCase<P extends UseCase.Request,Q extends UseCase.Result> {

    Observable<Q> execute(P request);

    interface Request{

    }

    interface Result{

    }
}
