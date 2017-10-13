package com.luhuan.rxpicker.utils;


import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * 简易的rxbus数据回传
 */
public class RxBus {
    private final Subject<Object,Object> mBus;

    private RxBus() {
        mBus =  new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus singleton(){
        return Holder.BUS;
    }

    public void post(Object object){
        mBus.onNext(object);
    }

    public <T> Observable<T> toObservable(Class<T> tClass){
        return mBus.ofType(tClass);
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }
}
