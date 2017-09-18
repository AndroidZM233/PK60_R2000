package com.speedata.pk60r2000;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.speedata.libuhf.R2K;
import com.zm.utilslib.utils.LogToFileUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import speedata.com.blelib.base.BaseBleApplication;
import speedata.com.blelib.bean.PK20Data;
import speedata.com.blelib.service.BluetoothLeService;
import speedata.com.blelib.utils.DataManageUtils;
import speedata.com.blelib.utils.SharedXmlUtil;

import static speedata.com.blelib.service.BluetoothLeService.ACTION_DATA_AVAILABLE;
import static speedata.com.blelib.service.BluetoothLeService.ACTION_GATT_CONNECTED;
import static speedata.com.blelib.service.BluetoothLeService.ACTION_GATT_DISCONNECTED;

/**
 * Created by 张明_ on 2017/7/10.
 */

public class MyApp extends BaseBleApplication {

    private static MyApp m_application; // 单例
    public ArrayList<Activity> aList = new ArrayList<>();
    public static String address = "";
    public static String name = "";
    public R2K r2K = null;

    @Override
    public void onCreate() {
        super.onCreate();
        m_application = this;
        r2K = new R2K(this);
        r2K.setRFConnectMode(1);
        LogToFileUtils.isDebug = true;
        LogToFileUtils.init(this);
    }


    public static MyApp getInstance() {
        return m_application;
    }


    /**
     * TODO(将所有已创建的Activity加入aList集合中)
     */
    public void addActivity(Activity activity) {

        if (!aList.contains(activity)) {
            aList.add(activity);
        }
    }

    /**
     * TODO(将aList集合中已存在的Activity移除)
     */
    public void deleteActivity(Activity activity) {
        if (compare(activity)) {
            aList.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    private boolean compare(Activity ch) {
        boolean flag = false;
        if (aList.contains(ch))
            flag = true;
        return flag;
    }

    public void getDeviceName(BluetoothDevice device) {
        address = device.getAddress();
        name = device.getName();
        bindServiceAndRegisterReceiver(device);
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
    }

    private IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_GATT_CONNECTED);
        intentFilter.addAction(ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    // Handles various events fired by the Service.处理由服务触发的各种事件。
    // ACTION_GATT_CONNECTED: connected to a GATT server.连接到GATT服务器
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.与GATT服务器断开连接
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.发现了GATT服务
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    //从设备接收数据。这可能是阅读的结果或通知操作。
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (ACTION_GATT_CONNECTED.equals(action)) {
                Toast.makeText(MyApp.this, "连接成功", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new MsgEvent("ServiceConnectedStatus", true));
            } else if (ACTION_GATT_DISCONNECTED.equals(action)) {
                EventBus.getDefault().post(new MsgEvent("ServiceConnectedStatus", false));
                address = null;
                name = null;
                unregisterReceiver(mGattUpdateReceiver);
                Toast.makeText(MyApp.this, "断开连接", Toast.LENGTH_SHORT).show();
            } else if (ACTION_DATA_AVAILABLE.equals(action)) {
                String data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
                if (TextUtils.isEmpty(data)) {
                    byte[] byteArrayExtra = intent.getByteArrayExtra(BluetoothLeService.NOTIFICATION_DATA);
                    Log.d("ZM", "MyApp: "+ DataManageUtils.bytesToHexString(byteArrayExtra));
                    r2K.getLinkage().pushRemoteRFIDData(byteArrayExtra);
                    LogToFileUtils.write("pushRemoteRFIDData: "+DataManageUtils.bytesToHexString(byteArrayExtra));
                }
            }
        }
    };


}
