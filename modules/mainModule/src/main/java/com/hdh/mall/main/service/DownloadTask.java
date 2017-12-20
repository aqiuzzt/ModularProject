package com.hdh.mall.main.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.BuildConfig;
import com.hdh.android.mail.base.Constant;
import com.hdh.common.util.app.AppUtil;
import com.hdh.mall.main.R;
import com.hdh.widget.kprogresshud.KProgressHUD;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by albert on 17/7/1.
 */

public class DownloadTask extends AsyncTask<String, Integer, File> {

    private Context mContext;
    private KProgressHUD mProgress;
    private int fileMaxSize;
    private boolean cancelUpdate;

    public DownloadTask(Context ctx) {
        this.mContext = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgress = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
                .setLabel(mContext.getResources().getString(R.string.update_downloading_tips))
                .setCancellable(false)
                .setMaxProgress(100)
                .setSize(150, 100)
                .show();
    }

    @Override
    protected File doInBackground(String... params) {

        if (params.length == 0) {

            return null;
        }

        File apkFile = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            // 判断SD卡是否存在，并且是否具有读写权限
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                // 获得存储卡的路径
                URL url = new URL(params[0]);
                // 创建连接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                // 获取文件大小
                fileMaxSize = conn.getContentLength();
                // 创建输入流
                is = conn.getInputStream();

                apkFile = new File(AppUtil.getDownloadDir(mContext), "duobeibao.apk");
                fos = new FileOutputStream(apkFile);
                // 缓存
                byte buf[] = new byte[4096];
                int count = -1;
                // 写入到文件中
                int downloadSize = 0;
                while ((count = is.read(buf)) != -1) {

                    if (cancelUpdate) {
                        break;
                    }
                    downloadSize += count;
                    // 计算进度条位置
                    publishProgress(downloadSize);
                    // 写入文件
                    fos.write(buf, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (cancelUpdate) {

            if (null != apkFile && apkFile.exists()) {
                apkFile.delete();
            }
            return null;
        }
        return apkFile;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        int downloadSize = values[0];
        int progress = (int) (downloadSize * 100 / fileMaxSize);
        mProgress.setProgress(progress);
    }

    @Override
    protected void onPostExecute(File file) {

        super.onPostExecute(file);

        mProgress.dismiss();

        if (null != file && file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            mContext.startActivity(intent);
            BaseApplication.getSecondLevelCacheKit().put(Constant.UPDATE_TIME, System.currentTimeMillis() + "");
        }
        ((Activity) mContext).finish();
    }
}
