package com.hdh.common.rx;

import android.view.View;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Martin on 2017/3/11.
 */

public class RxViewEvent {

    /**
     * View点击事件转换
     *
     * @param view
     * @return
     */
    public static Observable<Object> click(@NonNull View view) {
        return new ViewClickObservable(view);
    }

    /**
     * 防止多次点击产生多个事件(600ms内只有第一次点击有效果)
     *
     * @param view
     * @param delegate
     */
    public static void debounce(@NonNull View view, @NonNull RxClickDelegate delegate) {
        final RxClickDelegate finalDelegate = delegate;
        final View finalView = view;
        click(view).throttleFirst(600, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override public void accept(@NonNull Object o) throws Exception {
                finalDelegate.onClick(finalView);
            }
        });
    }
}
