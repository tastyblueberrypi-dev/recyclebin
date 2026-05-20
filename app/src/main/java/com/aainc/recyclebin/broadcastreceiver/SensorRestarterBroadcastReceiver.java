package com.aainc.recyclebin.broadcastreceiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aainc.recyclebin.services.ManagerService;

public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println(SensorRestarterBroadcastReceiver.class.getSimpleName() + "Destroyed ManagerServiced was catched by SensorRestarterBroadcastReceiver");

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (ManagerService.class.getName().equals(serviceInfo.service.getClassName())) {
                return;
            }
        }

        context.startService(new Intent(context, ManagerService.class));
    }
}