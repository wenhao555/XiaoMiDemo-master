package com.example.asus.xiaomidemo;

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

public class RegistActivity extends AppCompatActivity
{
    private EditText account, password;
    private Button regist_commit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        account = (EditText) findViewById(R.id.account);

        password = (EditText) findViewById(R.id.password);
        regist_commit = (Button) findViewById(R.id.regist_commit);
        regist_commit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = account.getText().toString();
                String pwd = password.getText().toString();

                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .build();


                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd))
                {
                    Toast.makeText(RegistActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User();
                user.setName(name);
                user.setPassword(pwd);
                Gson gson = new Gson();
                String Json = gson.toJson(user);
                RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), Json);
                final Request request = new Request.Builder()
                        .url(Urls.ADD_USER)
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
                        if (Boolean.parseBoolean(response.body().string()))
                        {
                            Message msg = new Message();
                            msg.what = 1;
                            mHandler.sendMessage(msg);
                        } else
                        {
                            Message msg = new Message();
                            msg.what = 2;
                            mHandler.sendMessage(msg);
                        }
                    }
                });

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
                Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                finish();
            } else
            {
                Toast.makeText(RegistActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

}
