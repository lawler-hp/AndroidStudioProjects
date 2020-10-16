package com.example.sharedpreferencestest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取SharedPreferences对象
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String account = pref.getString("账号", "");
        String password = pref.getString("密码", "");
        String sex = pref.getString("性别", "男");

        TextView show_text = (TextView)findViewById(R.id.show_content);
        show_text.setText("你提交的信息如下：\n 账号："+account+"\n"+"密码："+password+"\n"+"性别："+sex+"\n");
    }
}
