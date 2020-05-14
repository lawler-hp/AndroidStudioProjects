package com.example.servicebestpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

    };
    public static File FilesDir = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        String TAG = "文件路径";
        File sdCard = Environment.getExternalStorageDirectory();
        Log.i(TAG,"sdCard="+sdCard);     //外部存储目录（即SD卡）sdCard=/storage/emulated/0
        File directory_pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Log.i(TAG, "directory_pictures="+directory_pictures);    //外部存储目录（即SD卡）directory_pictures=/storage/emulated/0/Pictures
        File filesDir = getFilesDir();
        Log.i(TAG,"file_dir="+filesDir);     //这个是APP存储的目录（内部存储） file_dir=/data/user/0/com.example.servicebestpractice/files
        File externalFilesDir = getExternalFilesDir(null);
        Log.i(TAG, "externalFileDir = "+externalFilesDir);  // externalFileDir = /storage/emulated/0/Android/data/com.example.servicebestpractice/files
        File Card = Environment.getDataDirectory();
        Log.i(TAG,"Card="+Card);    //Card=/data*/

        Button startDownload = (Button) findViewById(R.id.start_download);
        Button pauseDownload = (Button) findViewById(R.id.pause_download);
        Button cancelDownload = (Button) findViewById(R.id.cancel_download);
        startDownload.setOnClickListener(this);
        pauseDownload.setOnClickListener(this);
        cancelDownload.setOnClickListener(this);
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent); // 启动服务
        bindService(intent, connection, BIND_AUTO_CREATE); // 绑定服务
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,}, 1);
        }
        FilesDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
    }

    @Override
    public void onClick(View v) {
        if (downloadBinder == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.start_download:
                String url = "https://static.zzzmh.cn/bz/download/12az/62485fc91c0d.jpg";
                downloadBinder.startDownload(url);
                break;
            case R.id.pause_download:
                downloadBinder.pauseDownload();
                break;
            case R.id.cancel_download:
                downloadBinder.cancelDownload();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                for (int i=0;i<grantResults.length;i++){
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,permissions[i]+"权限未授权",Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                }
                break;
            default:
        }
    }

    /**
     * 活动销毁，取消绑定。
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    /* 检查外部存储是否可用于读写 */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Log.d("文件", "外部存储可用于读写 ");
            return true;
        }
        return false;
    }

    /*检查外部存储是否可读 */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
