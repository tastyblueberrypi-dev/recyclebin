package com.aainc.recyclebin.broadcastreceiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.aainc.recyclebin.services.ManagerService;

public class RebootBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = RebootBroadcastReceiver.class.getSimpleName();
    private static final String WAKE_LOCK_TAG = RebootBroadcastReceiver.class.getName();
    boolean showlog = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        new RebootBroadcastReceiver.RestartThread(context).start();
    }

    class RestartThread extends Thread {

        private Context mContext;

        public RestartThread(Context context) {
            super();
            this.mContext = context;
        }

        public void run() {
            PowerManager.WakeLock wLock = null;
            try {

                PowerManager powerManager = (PowerManager) this.mContext.getSystemService(Context.POWER_SERVICE);
                wLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK |
                        PowerManager.ACQUIRE_CAUSES_WAKEUP, WAKE_LOCK_TAG);
                wLock.acquire();

                if (showlog)
                    System.out.println("Restart broadcast receiver is triggered");

                ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
                for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {

                    if (ManagerService.class.getName().equals(serviceInfo.service.getClassName())) {
                        if (showlog)
                            System.out.println("Manager Service is running.");
                    }
                }

                Intent startManagerService = new Intent(mContext, ManagerService.class);
                mContext.startService(startManagerService);


            } catch (Exception ex) {

            } finally {
                if (wLock != null) {
                    wLock.release();
                }
            }
        }
    }
}
