package com.ants.theantsgo.tools;


import com.alibaba.fastjson.JSON;
import com.ants.theantsgo.util.JSONUtils;

import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by OTKJ on 2018/5/8.
 */

public class ObserTool {
    private static ObserTool instance;

    public static ObserTool gainInstance() {
        synchronized (ObserTool.class) {
            if (null == instance) {
                instance = new ObserTool();
            }
        }
        return instance;
    }

    /**
     * 按照特定泛型将json字符串转通过rxjava1异步换成bean对象
     * @param jsonStr  要转换的字符串
     * @param cls  要转换成为的类型
     * @param listener  转换成功后的回调接口
     * @param <T>
     * @return   无用，暂时因为泛型写的这个东西
     */
    public <T> T jsonToBean(final String jsonStr, final Class<T> cls, final BeanListener listener) {
        final Observable<T> observable = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onNext(JSON.parseObject(jsonStr, cls));
            }
        });

        final Subscriber<T> subscriber = new Subscriber<T>() {

            @Override
            public void onCompleted() {
                unsubscribe();
            }

            @Override
            public void onError(Throwable e) {
                unsubscribe();
            }

            @Override
            public void onNext(T t) {
                listener.returnObj(t);
            }
        };
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        return null;
    }


    public void dealData(final String sourceObj, final Listener listener) {
        final Observable<Map<String, String>> observable = Observable.create(new Observable.OnSubscribe<Map<String, String>>() {
            @Override
            public void call(Subscriber<? super Map<String, String>> subscriber) {
                subscriber.onNext(JSONUtils.parseKeyAndValueToMap(sourceObj));
            }
        });

        final Subscriber<Map<String, String>> subscriber = new Subscriber<Map<String, String>>() {

            @Override
            public void onCompleted() {
                unsubscribe();
            }

            @Override
            public void onError(Throwable e) {
                unsubscribe();
            }

            @Override
            public void onNext(Map<String, String> stringStringMap) {
                listener.returneData(stringStringMap);
            }
        };

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Observer<? super Map<String, String>>) subscriber);
    }
    public interface BeanListener{
        void returnObj(Object t);
    }
    public interface Listener {
        void returneData(Map<String, String> map);
    }
}
