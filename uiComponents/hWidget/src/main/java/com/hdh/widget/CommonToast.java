package com.hdh.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by albert on 17/6/30.
 */

public class CommonToast {

    private static Toast toast;
    private static CountDownTimer cdt;
    private static long LONG_SHOW_TIME = 3000;
    private static long SHORT_SHOW_TIME = 1500;


    public static void showLong(Context ctx, int id) {

        if (toast == null) {
            toast = Toast.makeText(ctx, id, Toast.LENGTH_LONG);
        } else {
            toast.setText(id);
        }
        toast.show();

        if (cdt == null) {
            cdt = new CountDownTimer(LONG_SHOW_TIME, 1000) {

                @Override
                public void onTick(long arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    toast.cancel();
                }
            };
        } else {
            cdt.cancel();
            cdt = new CountDownTimer(LONG_SHOW_TIME, 1000) {

                @Override
                public void onTick(long arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    toast.cancel();
                }
            };
        }
        cdt.start();
    }

    public static void showLong(Context ctx, String text) {
        if (toast == null) {
            toast = Toast.makeText(ctx, text, Toast.LENGTH_LONG);
        } else {
            toast.setText(text);
        }
        toast.show();
        if (cdt == null) {
            cdt = new CountDownTimer(LONG_SHOW_TIME, 1000) {

                @Override
                public void onTick(long arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    toast.cancel();
                }
            };
        } else {
            cdt.cancel();
            cdt = new CountDownTimer(LONG_SHOW_TIME, 1000) {

                @Override
                public void onTick(long arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    toast.cancel();
                }
            };
        }
        cdt.start();

    }

    public static void showShort(Context ctx, int id) {
        if (toast == null) {
            toast = Toast.makeText(ctx, id, Toast.LENGTH_SHORT);
        } else {
            toast.setText(id);
        }
        toast.show();

        if (cdt == null) {
            cdt = new CountDownTimer(SHORT_SHOW_TIME, 1000) {

                @Override
                public void onTick(long arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    toast.cancel();
                }
            };
        } else {
            cdt.cancel();
            cdt = new CountDownTimer(SHORT_SHOW_TIME, 1000) {

                @Override
                public void onTick(long arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    toast.cancel();
                }
            };
        }
        cdt.start();

    }

    public static void showShort(Context ctx, String text) {

        if (toast == null) {
            toast = Toast.makeText(ctx, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
        if (cdt == null) {
            cdt = new CountDownTimer(SHORT_SHOW_TIME, 1000) {

                @Override
                public void onTick(long arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    toast.cancel();
                }
            };
        } else {
            cdt.cancel();
            cdt = new CountDownTimer(SHORT_SHOW_TIME, 1000) {

                @Override
                public void onTick(long arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    toast.cancel();
                }
            };
        }
        cdt.start();

    }


    public static void showFaceShort(Context ctx, String text) {

        if (toast == null) {
            toast = Toast.makeText(ctx, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.setGravity(Gravity.BOTTOM, 0, 40);
        toast.show();

        if (cdt == null) {
            cdt = new CountDownTimer(SHORT_SHOW_TIME, 1000) {

                @Override
                public void onTick(long arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    toast.cancel();
                }
            };
        } else {
            cdt.cancel();
            cdt = new CountDownTimer(SHORT_SHOW_TIME, 1000) {

                @Override
                public void onTick(long arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    toast.cancel();
                }
            };
        }
        cdt.start();

    }
}
