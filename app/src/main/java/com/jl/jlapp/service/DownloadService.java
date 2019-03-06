package com.jl.jlapp.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.google.gson.Gson;
import com.jl.jlapp.R;
import com.jl.jlapp.mvp.activity.ScreenActivity;
import com.jl.jlapp.mvp.activity.SettingsActivity;
import com.jl.jlapp.mvp.base.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class DownloadService extends IntentService {

    private static final String EXTRA_VERSION = "version";

    private RemoteViews contentView;
    private NotificationManagerCompat notificationManagerCompat;
    private NotificationCompat.Builder notificationCompatBuilder;
    private PendingIntent pendingIntent;
    private int notification_id = 0;

    public static void startService(Context context, String apkUrl) {
        Log.d("aaaaaaaasetting","startService");
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra("appUrl", apkUrl);
        context.startService(intent);
    }

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String apkUrl = intent.getStringExtra("appUrl");
            File fTarget;
            if (SDCardUtils.isSDCardEnable()) {
                FileUtils.createOrExistsDir(SDCardUtils.getSDCardPath() + "jlfood");
                fTarget = new File(SDCardUtils.getSDCardPath() + "jlfood", "jlfood.apk");
            } else {
                fTarget = new File(getCacheDir(), "jlfood.apk");
            }
            FileUtils.createFileByDeleteOldFile(fTarget);

            createNotification();

            boolean err = false;
            try {
                download(fTarget, apkUrl);
            } catch (IOException e) {
                e.printStackTrace();
                err = true;
            } finally {
                //下载失败
                if (err) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(MyApplication.getInstance().getApplicationContext(), "下载新版本失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                   /* contentView.setTextViewText(R.id.notificationTitle, getString(R.string.err_download_failed));

                    notificationCompatBuilder
                            .setSmallIcon(R.drawable.icon)
                            .setAutoCancel(true)
                            .setContentTitle("jlfood.apk")
                            .setContentInfo(getString(R.string.err_download_failed));
                    notificationManagerCompat.notify(notification_id, notificationCompatBuilder.build());*/
                    //关闭下载框
                    notificationManagerCompat.cancel(notification_id);
                }
                //下载成功，自动安装
                else {
                    Uri uri;
                    Intent intent1 = new Intent(Intent.ACTION_VIEW);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //如果是7.0以上的系统，要使用FileProvider的方式构建Uri
                        uri = FileProvider.getUriForFile(this, "com.jl.jlapp.fileprovider", fTarget);//在AndroidManifest中的android:authorities值
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent1.setDataAndType(uri, "application/vnd.android.package-archive");
                    } else {
                        intent1.setDataAndType(Uri.fromFile(fTarget), "application/vnd.android.package-archive");
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    startActivity(intent1);
                    //关闭下载框
                    notificationManagerCompat.cancel(notification_id);
                }

            }
        }
    }

    private void createNotification() {
        contentView = new RemoteViews(getPackageName(), R.layout.layout_notification_download);
        contentView.setTextViewText(R.id.notificationTitle, getString(R.string.txt_downloading));
        contentView.setTextViewText(R.id.notificationPercent, "0%");
        contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);

        Intent updateIntent = new Intent(this, ScreenActivity.class);
        updateIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);

        notificationCompatBuilder = new NotificationCompat.Builder(this);
        notificationCompatBuilder
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setTicker(getString(R.string.txt_downloading))
                .setContent(contentView)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setWhen(System.currentTimeMillis());

        notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notification_id, notificationCompatBuilder.build());
    }

    private void download(File fileName, String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(1000 * 30);
        connection.connect();

        int statusCode = connection.getResponseCode();
        if (statusCode != HttpURLConnection.HTTP_OK) {
            throw new IOException(connection.getResponseMessage());
        }
        int length = connection.getContentLength();

        InputStream inputStream = connection.getInputStream();
        OutputStream outputStream = new FileOutputStream(fileName, false);
        byte[] buf = new byte[2048];
        int readed, progress;
        long downloaded = 0;
        long lastPublish = System.currentTimeMillis();

        while ((readed = inputStream.read(buf)) != -1) {
            outputStream.write(buf, 0, readed);
            downloaded += readed;
            progress = (int) (100 * downloaded / length);
            if (System.currentTimeMillis() - lastPublish > 500) {
                contentView.setTextViewText(R.id.notificationPercent, progress + "%");
                contentView.setProgressBar(R.id.notificationProgress, 100, progress, false);
                notificationManagerCompat.notify(notification_id, notificationCompatBuilder.build());
                lastPublish = System.currentTimeMillis();
            }
        }

        contentView.setTextViewText(R.id.notificationPercent, 100 + "%");
        contentView.setProgressBar(R.id.notificationProgress, 100, 100, false);
        notificationManagerCompat.notify(notification_id, notificationCompatBuilder.build());

        outputStream.flush();
        outputStream.close();

        inputStream.close();
        connection.disconnect();
    }
}
