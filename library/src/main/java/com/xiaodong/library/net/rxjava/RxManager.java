package com.xiaodong.library.net.rxjava;

import com.xiaodong.library.base.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by xiaodong.jin on 2018/11/15.
 * description：
 */

public class RxManager {
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    public <T extends BaseResponse> DisposableObserver<T> addObserver(final Observable<T> netWorkObservable,
                                                                      final RxObservableListener<T> rxObservableListener){

        DisposableObserver<T> observer = netWorkObservable.compose(RxSchedulers.<T>io_main())

                .subscribeWith(new RxSubscriber<T>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        rxObservableListener.onNetStart(null);
                    }

                    @Override
                    public void _onNext(T t) {

                        rxObservableListener.onNext(t);
                    }

                    @Override
                    public void _onError(NetWorkCodeException.ResponseThrowable e) {
                        rxObservableListener.onNetError(e);
                    }

                    @Override
                    public void _onComplete() {
                        rxObservableListener.onComplete();
                    }
                });

        if (observer != null) {
            mCompositeDisposable.add(observer);
        }
        return observer;
    }

    public void clear() {

    }
}
