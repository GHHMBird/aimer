package com.buyint.mergerbot.Utility;

import android.app.DownloadManager;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DownLoadUtil {

    private static final int HANDLE_DOWNLOAD = 0x001;
    private OnProgressListener onProgressListener;
    private DownloadChangeObserver downloadObserver;
    private ScheduledExecutorService scheduledExecutorService;
    private Context context;
    private DownloadManager manager;
    private long downloadId;
    public String path;

    DownLoadUtil(Context context) {
        this.context = context;
    }

    void downLoadFile(Context context, String text, String url) {
        if (url.contains(".apk")) {
            path = Environment.getDataDirectory() + "/download_buyint_apk/";
            downloadObserver = new DownloadChangeObserver();
            registerContentObserver();
        } else {
            path = Environment.getExternalStorageDirectory() + "/download/";
            Toast.makeText(context, text + "开始下载", Toast.LENGTH_SHORT).show();
        }
        manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //设置在什么网络情况下进行下载，不设置就默认都能下载
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏标题
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle("下载");
        request.setDescription(text + "正在下载");

        request.setAllowedOverRoaming(false);
        request.setVisibleInDownloadsUi(true);
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, path);
        downloadId = manager.enqueue(request);
    }

    private Handler downLoadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (onProgressListener != null && HANDLE_DOWNLOAD == msg.what) {
                //被除数可以为0，除数必须大于0
                if (msg.arg1 >= 0 && msg.arg2 > 0) {
                    if (msg.arg1 / (float) msg.arg2 == 1) {
                        unregisterContentObserver();
                    }
                    onProgressListener.onProgress(msg.arg1 / (float) msg.arg2, downloadId);
                }
            }
        }
    };

    private Runnable progressRunnable = this::updateProgress;

    /**
     * 发送Handler消息更新进度和状态
     */
    private void updateProgress() {
        int[] bytesAndStatus = getBytesAndStatus(downloadId);
        downLoadHandler.sendMessage(downLoadHandler.obtainMessage(HANDLE_DOWNLOAD, bytesAndStatus[0], bytesAndStatus[1], bytesAndStatus[2]));
    }


    /**
     * 通过query查询下载状态，包括已下载数据大小，总大小，下载状态
     *
     * @param downloadId 下载id
     * @return int[]
     */
    private int[] getBytesAndStatus(long downloadId) {
        int[] bytesAndStatus = new int[]{
                -1, -1, 0
        };
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = null;
        try {
            cursor = manager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                //已经下载文件大小
                bytesAndStatus[0] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                //下载文件的总大小
                bytesAndStatus[1] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                //下载状态
                bytesAndStatus[2] = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return bytesAndStatus;
    }


    /**
     * 监听下载进度
     */
    private class DownloadChangeObserver extends ContentObserver {

        DownloadChangeObserver() {
            super(downLoadHandler);
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        }

        /**
         * 当所监听的Uri发生改变时，就会回调此方法
         *
         * @param selfChange 此值意义不大, 一般情况下该回调值false
         */
        @Override
        public void onChange(boolean selfChange) {
            scheduledExecutorService.scheduleAtFixedRate(progressRunnable, 0, 300, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 注册ContentObserver
     */
    private void registerContentObserver() {
        /* observer download change */
        if (downloadObserver != null) {
            context.getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), false, downloadObserver);
        }
    }

    /**
     * 注销ContentObserver
     */
    private void unregisterContentObserver() {
        if (downloadObserver != null) {
            context.getContentResolver().unregisterContentObserver(downloadObserver);
        }
    }

    public interface OnProgressListener {
        /**
         * 下载进度
         * @param fraction   已下载/总大小
         * @param downloadId 下载id
         */
        void onProgress(float fraction, long downloadId);
    }

    /**
     * 对外开发的方法
     *
     * @param onProgressListener 回调
     */
    void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }

}
