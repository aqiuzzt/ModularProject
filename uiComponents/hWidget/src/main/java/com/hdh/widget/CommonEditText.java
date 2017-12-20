package com.hdh.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;



/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/24 14:26
 */
public class CommonEditText extends AppCompatEditText {
    private static final String TAG = "CommonEditText";

    public CommonEditText(Context context) {
        super(context);
    }

    public CommonEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyPreIme() called with: keyCode = [" + keyCode + "], event = [" + event + "]");
        return super.onKeyPreIme(keyCode, event);
    }


}
