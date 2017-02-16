package com.wzn.libaray.event;

import android.support.annotation.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Wind_Fantasy on 16/4/14.
 */
public class RxBus {
    private static volatile RxBus defaultInstance;
    private final Subject<Object, Object> bus;
    //    private final Subject<Object, Object> busSticky;
    private final Map<Class<?>, Object> stickyEvents;


    //private final PublishSubject<Object> _bus = PublishSubject.create();

    // If multiple threads are going to emit events to this
    // then it must be made thread-safe like this instead
    public static RxBus getDefault() {
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance;
    }

    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
        stickyEvents = new ConcurrentHashMap<>();
//        busSticky = new SerializedSubject<>(ReplaySubject.create());
//        busSticky = new SerializedSubject<>(BehaviorSubject.create());
    }


    public void post(Object o) {
        bus.onNext(o);
    }

    private boolean checkStickyEventsNonNull() {
        return null != stickyEvents;
    }

    public void postSticky(Object o) {
        if (null != o && checkStickyEventsNonNull()){
            synchronized (stickyEvents){
                stickyEvents.put(o.getClass(), o);
            }
        }
//        busSticky.onNext(o);
    }

    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    public <T> Observable<T> getStickyEvent(@NonNull Class<T> eventType) {
        if (checkStickyEventsNonNull()) {
            synchronized (stickyEvents){
                return Observable.just((T) stickyEvents.get(eventType));
            }
        } else
            return Observable.empty();
//        return busSticky.ofType(eventType);
    }


    public void removeStickyEvent(Object eventType) {
        if (null != eventType)
            removeStickyEvent(eventType.getClass());
    }

    public void removeStickyEvent(Class eventType) {
        if (checkStickyEventsNonNull() && null != eventType) {
            synchronized (stickyEvents){
                stickyEvents.remove(eventType);
            }
        }
    }

    public void removeAllStickyEvent(){
        if (checkStickyEventsNonNull()) {
            synchronized (stickyEvents){
                stickyEvents.clear();
            }
        }
    }


    public void complete() {
        if (null != bus)
            bus.onCompleted();
    }
}
