package com.example.sharedpreferencestest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 使用SharedPreferences存储文件
 * 1.获取SharedPreferences对象
 * 2.调用SharedPreferences对象的edit()方法来获取一个SharedPreferences.Editor对象。
 * 3.向SharedPreferences.Editor对象中添加数据，比如添加一个布尔型数据就使用putBoolean()方法，添加一个字符串则使用putString()方法，以此类推。
 * 4.调用apply( )方法将添加的数据提交，从而完成数据存储操作。
 */


public class LoginActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private EditText accountEdit;

    private EditText passwordEdit;

    private Button login;

    private CheckBox rememberPass;

    private CheckBox autoLogin;

    RadioGroup sex_group;

    //默认男性被选中
    private String sex_str = "男性";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取SharedPreferences对象
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        sex_group = (RadioGroup)findViewById(R.id.sex_group);
        sex_group.setOnCheckedChangeListener(this);

/*        boolean isRemember = pref.getBoolean("remember_password", false);
        boolean isAuto = pref.getBoolean("auto_login", false);

        if (isRemember) {
            // 将账号和密码都设置到文本框中
            String account = pref.getString("账号", "");
            String password = pref.getString("密码", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
            if (isAuto){
                autoLogin.setChecked(true);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 500);//500后执行Runnable中的run方法

            }
        }*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                    editor = pref.edit();
                        //向SharedPreferences.Editor对象中添加数据
                        editor.putBoolean("remember_password", true);
                        editor.putString("账号", account);
                        editor.putString("密码", password);
                        editor.putString("性别", sex_str);
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //根据用户选择来改变sex_str的值
        sex_str = checkedId == R.id.man_but?"男性":"女性";
    }
}
