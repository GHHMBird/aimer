package com.buyint.mergerbot.Utility;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class DownloadReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        switch (intent.getAction()) {
            case DownloadManager.ACTION_DOWNLOAD_COMPLETE:
                DownloadManager.Query query = new DownloadManager.Query();
                //在广播中取出下载任务的id
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                query.setFilterById(id);
                Cursor c = manager.query(query);
                if (c.moveToFirst()) {
                    if (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {

                        String filename;
                        //获取文件下载路径
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                            filename = Uri.parse(c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))).getPath();

                            Intent intent1 = new Intent(Intent.ACTION_VIEW);
                            intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Uri data = FileProvider.getUriForFile(context, "com.buyint.mergerbot.fileProvider", new File(filename));

                            List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent1, PackageManager.MATCH_DEFAULT_ONLY);
                            for (ResolveInfo res : resolveInfos) {
                                String packageName = res.activityInfo.packageName;
                                context.grantUriPermission(packageName, data, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            }

                            intent1.setDataAndType(data, "application/vnd.android.package-archive");
                            context.startActivity(intent1);

                        } else {
                            filename = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));

                            //如果文件名不为空，说明已经存在了，拿到文件名想干嘛都好
                            if (filename != null) {
                                if (filename.contains("download_buyint_apk")) {
                                    Intent activityIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(filename));
                                    Uri uri = Uri.fromFile(new File(filename));

                                    activityIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                                    activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(activityIntent);
                                }
                            }
                        }

                    }
                }
                break;
            case DownloadManager.ACTION_NOTIFICATION_CLICKED:
                long[] ids = intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
                //点击通知栏取消下载
                manager.remove(ids);
                Toast.makeText(context, "已经取消下载", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
