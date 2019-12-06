package com.example.asus.xiaomidemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


/**
 * 基础Activity类可以不用看
 * Created by liumeng on 2016/8/8.
 */
public abstract class BaseActivity extends AppCompatActivity implements SetActivityTitleListener
{
    protected final String TAG = getClass().getName();

    protected Activity context;
    protected CustomClickListener clickListener;

    /**
     * @return activity界面布局xml文件资源id
     */
    protected abstract int returnLayoutRes();

    /**
     * @return 菜单xml文件资源id
     */
    protected abstract int returnMenuResource();

    /**
     * 初始化数据
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 初始化界面组件
     */
    protected abstract void initView();

    /**
     * 设置Menu，获取各个MenuItem实例
     *
     * @param menu
     */
    protected abstract void setupMenu(Menu menu);

    /**
     * 点击事件监听
     *
     * @param view
     */
    protected abstract void onViewClickListener(View view);

    /**
     * 点击toolbar返回按钮/返回键事件监听
     */
    protected abstract void onBackClickOpt();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = this;


        if (returnLayoutRes() > 0)
        {
            setContentView(returnLayoutRes());
        }


        clickListener = new CustomClickListener(TAG)
        {
            @Override
            public void clickOpt(View v)
            {
                onViewClickListener(v);
            }
        };

        initData(savedInstanceState);
        initView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }

    /**
     * 按键监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 不管是否设置了按钮资源文件总是返回true，这样才可以在 onOptionsItemSelected 方法中监听返回按钮点击事件
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (returnMenuResource() > 0)
        {
            getMenuInflater().inflate(returnMenuResource(), menu);
            setupMenu(menu);
            return super.onCreateOptionsMenu(menu);
        }
        return true;
    }

    @Override
    public void onBackPressed()
    {
        onBackClickOpt();
    }

    /**
     * 点击toolbar栏按钮事件监听
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // 点击toolbar返回按钮
        if (item.getItemId() == android.R.id.home)
        {
            onBackClickOpt();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 设置显示toolbar返回按钮
     */
    /**
     * 为toolbar设置title内容
     *
     * @param title 要设置的内容，类型可以为string或int
     */
    protected void setActivityTitle(Object title)
    {
        if (title instanceof String)
        {
            setTitle((CharSequence) title);
        } else if (title instanceof Integer)
        {
            setTitle((Integer) title);
        }
    }

    @Override
    public void setActivityTitleFromFragment(Object title)
    {
        setActivityTitle(title);
    }


    /**
     * 显示loading对话框
     */
    protected void showLoadingDialog()
    {
    }

    /**
     * 隐藏loading对话框
     */
    protected void dismissLoadingDialog()
    {
    }


}
