package com.speedata.pk60r2000;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.speedata.libuhf.R2K;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import speedata.com.blelib.utils.DataManageUtils;


public class MainActivity extends BaseActivity implements
        View.OnClickListener {
    private TextView device_name;
    private TextView device_address;
    private ToggleButton btn_serviceStatus;
    private LinearLayout ll;
    private Button btnInv;
    private Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initUi();

    }

    private void initUi() {
        device_name = (TextView) findViewById(R.id.device_name);
        device_address = (TextView) findViewById(R.id.device_address);
        btn_serviceStatus = (ToggleButton) findViewById(R.id.btn_serviceStatus);
        btn_serviceStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleOnCheckedChangeListener(isChecked);
            }
        });

        ll = (LinearLayout) findViewById(R.id.ll);
        btnInv = findViewById(R.id.btn_inv);
        btnInv.setOnClickListener(this);
        btnTest = findViewById(R.id.btn_test);
        btnTest.setOnClickListener(this);
    }

    public void toggleOnCheckedChangeListener(boolean isCheck) {
        if (isCheck) {
            MyApp.getInstance().connect();
        } else {
            MyApp.getInstance().disconnect();
            EventBus.getDefault().post(new MsgEvent("ServiceConnectedStatus", false));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMain(MsgEvent msgEvent) {
        String type = msgEvent.getType();
        Object msg = msgEvent.getMsg();
        if ("ServiceConnectedStatus".equals(type)) {
            boolean result = (boolean) msg;
            btn_serviceStatus.setChecked(result);
            if (result) {
                ll.setVisibility(View.VISIBLE);
                device_address.setText("Address：" + MyApp.address);
                device_name.setText("Name：" + MyApp.name);
            } else {
                ll.setVisibility(View.GONE);
            }
        } else if ("Notification".equals(type)) {
            Toast.makeText(MainActivity.this, (String) msg, Toast.LENGTH_SHORT).show();
        } else if ("Save6Data".equals(type)) {
            Toast.makeText(MainActivity.this, (String) msg, Toast.LENGTH_SHORT).show();
        } else if ("Save6DataSuccess".equals(type)) {
            MyApp.getInstance().writeCharacteristic6("AA0A020100000000000000000000000000000200");
            Toast.makeText(MainActivity.this, (String) msg, Toast.LENGTH_SHORT).show();
        }

    }

    private long mkeyTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.ACTION_DOWN:
                if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                    mkeyTime = System.currentTimeMillis();
                    Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_inv:
                MyApp.getInstance().writeCharacteristic6("ff10000000ee");
                SystemClock.sleep(300);
                MyApp.getInstance().writeCharacteristic6("ff0c000000ee");
                SearchTagDialog searchTag = new SearchTagDialog(this,
                        MyApp.getInstance().r2K, "r2k");
                searchTag.setTitle(R.string.Item_Choose);
                searchTag.show();
                break;
            case R.id.btn_test:
                break;
        }
    }


}
