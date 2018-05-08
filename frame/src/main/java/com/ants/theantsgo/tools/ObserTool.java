package com.ants.theantsgo.tools;



import com.ants.theantsgo.util.JSONUtils;

import org.json.JSONObject;

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

   public interface Listener {
        void returneData(Map<String, String> map);
    }
}
