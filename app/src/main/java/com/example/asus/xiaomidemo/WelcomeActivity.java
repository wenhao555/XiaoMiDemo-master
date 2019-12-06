package com.example.asus.xiaomidemo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class WelcomeActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (PrefUtils.getString(WelcomeActivity.this, "name", "").equals("") || PrefUtils.getString(WelcomeActivity.this, "password", "").equals(""))
                {//判断是否存储用户名和密码有的时候直接登录没有的时候输入之后才能登录
                    Log.e("error", "no session");
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                } else
                {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }
                finish();
            }
        }, 2000);
    }
}
