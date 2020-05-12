package com.example.notificationtest;

import android.app.NotificationManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AlertDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_details);
        //取消通知的第二种方法
        NotificationManager manager = getSystemService(NotificationManager.class);
        //创建通知渠道
        manager.cancel(1);
    }
}
