package com.hdh.common.rx;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/28 11:28
 */
public class RxHandleThread {

    /**
     * 简化Rx的操作 执行io操作
     *
     * @param delegate
     * @param <T>
     * @return
     */
    public static <T> Disposable executeIO(@NonNull ThreadDelegate<T> delegate) {
        final ThreadDelegate<T> finalDelegate = delegate;
        Disposable disposable = Observable.create(new ObservableOnSubscribe<T>() {
            @Override public void subscribe(ObservableEmitter<T> e) throws Exception {
                T result = finalDelegate.doInBackground();
                e.onNext(result);
            }
        }).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<T>() {
            @Override public void accept(@NonNull T t) throws Exception {
                finalDelegate.handleResult(t);
            }
        });
        return disposable;
    }

    /**
     * 简化Rx的操作 执行复杂计算
     *
     * @param delegate
     * @param <T>
     * @return
     */
    public static <T> Disposable executeCompute(@NonNull ThreadDelegate<T> delegate) {
        final ThreadDelegate<T> finalDelegate = delegate;
        Disposable disposable = Observable.create(new ObservableOnSubscribe<T>() {
            @Override public void subscribe(ObservableEmitter<T> e) throws Exception {
                T result = finalDelegate.doInBackground();
                e.onNext(result);
            }
        }).observeOn(Schedulers.computation()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<T>() {
            @Override public void accept(@NonNull T t) throws Exception {
                finalDelegate.handleResult(t);
            }
        });
        return disposable;
    }


    /**
     * 简化Rx的操作 执行io操作
     */
    public static Disposable executeIO(@NonNull ThreadVoidDelegate delegate) {
        final ThreadVoidDelegate finalDelegate = delegate;
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Notification>() {
            @Override public void subscribe(ObservableEmitter<Notification> e) throws Exception {
                finalDelegate.doInBackground();
                e.onNext(Notification.INSTANCE);
            }
        }).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Notification>() {
            @Override public void accept(@NonNull Notification notification) throws Exception {

            }
        });
        return disposable;
    }

    /**
     * 简化Rx的操作 执行复杂计算
     */
    public static Disposable executeCompute(@NonNull ThreadVoidDelegate delegate) {
        final ThreadVoidDelegate finalDelegate = delegate;
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Notification>() {
            @Override public void subscribe(ObservableEmitter<Notification> e) throws Exception {
                finalDelegate.doInBackground();
                e.onNext(Notification.INSTANCE);
            }
        }).observeOn(Schedulers.computation()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Notification>() {
            @Override public void accept(@NonNull Notification notification) throws Exception {

            }
        });
        return disposable;
    }
}
