package com.speedata.pk60r2000;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by 张明_ on 2017/9/15.
 * Email 741183142@qq.com
 */

public class BaseActivity extends Activity {

    public void openAct(Context packageContext, Class<?> cls){
        Intent intent=new Intent(packageContext,cls);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                openAct(this, DeviceScanActivity.class);
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }


    /**
     * 打开新的Fragment
     * @param fragment
     */
    public void openFragment(Fragment fragment,int containerViewId) {
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(containerViewId, fragment);
        transaction.addToBackStack(null);
        // 提交事物
        transaction.commit();
    }
    /**
     * 关闭Fragment
     */
    public void closeFragment() {
        FragmentManager fm = getFragmentManager();
        fm.popBackStack();
    }
}
