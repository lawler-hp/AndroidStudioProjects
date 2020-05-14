package com.example.servicetest;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class MyService extends Service {

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {
        public void startDownload(){
            Log.d("MyService", "startDownload执行了");
        }
        public int getProgress(){
            Log.d("MyService", "getProgress执行了");
            return 0;
        }
    }
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "onCreate执行了");
        NotificationCompat.Builder builder = null;
        String channelID = "1";
        String channelName = "channel_name";
        //第三个参数importance 它使用 NotificationManager 类中的其中一个常量。该参数确定出现任何属于该渠道的通知时如何打断用户，但您还必须使用 setPriority() 设置优先级以支持 Android 7.1 和更低版本（如上所示）。
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        //NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManager manager = getSystemService(NotificationManager.class);
        //创建通知渠道
        manager.createNotificationChannel(channel);

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = new NotificationCompat.Builder(this, channelID)
                .setContentTitle("系统提示")
                .setContentText("系统提示。。。。。。。。。。。。。。")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1,notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStartCommand执行了");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy执行了");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
