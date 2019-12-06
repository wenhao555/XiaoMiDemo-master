package com.example.asus.xiaomidemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity
{
    private EditText account, password;
    private Button login_commit, regist;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        login_commit = (Button) findViewById(R.id.login_commit);
        regist = (Button) findViewById(R.id.regist);
        regist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this, RegistActivity.class));
            }
        });
        login_commit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String userName = account.getText().toString();
                String pwd = password.getText().toString();


                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd))
                {
                    Toast.makeText(LoginActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                } else
                {
                    requestData(userName, pwd);
                }

            }
        });
    }

    private void requestData(String userName, String pwd)
    {//请求数据
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        final User user = new User();
        user.setName(userName);
        user.setPassword(pwd);
        Gson gson = new Gson();
        String Json = gson.toJson(user);
        final RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), Json);
        final Request request = new Request.Builder()
                .url(Urls.GET_USER)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.e("error", "connectFail");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = response.body().string();
                mHandler.sendMessage(msg);//得到数据之后刷新
            }
        });
    }

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == 1)
            {
                JsonObject jsonObject = new JsonParser().parse(msg.obj.toString()).getAsJsonObject();
                Gson gson = new Gson();
                User user1 = gson.fromJson(jsonObject, User.class);
                PrefUtils.setString(LoginActivity.this, "id", user1.getUserId() + "");//保存用户id
                PrefUtils.setString(LoginActivity.this, "name", user1.getName());//保存用户姓名
                PrefUtils.setString(LoginActivity.this, "password", user1.getPassword());//保存用户密码
                startActivity(new Intent(LoginActivity.this, MainActivity.class));//跳转界面
                finish();//关闭当前界面
            }
        }
    };
}
