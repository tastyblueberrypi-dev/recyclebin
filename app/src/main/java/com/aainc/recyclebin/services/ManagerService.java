package com.aainc.recyclebin.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.aainc.recyclebin.R;
import com.aainc.recyclebin.broadcastreceiver.SensorRestarterBroadcastReceiver;
import com.aainc.recyclebin.storage.FileSystemHandler;
import com.aainc.recyclebin.ui.MainActivity;

public class ManagerService extends Service {

    private static final String TAG = ManagerService.class.getSimpleName();
    private FileSystemHandler fileSystemHandler;
    private static final int NOTIFICATION_ID = 1337;
    private static final String CHANNEL_ID = "com.aainc.recyclebin";
    private boolean showlog = false;

    @Override
    public void onCreate() {
        if (showlog) System.out.println(TAG + " onCreate");

        startForegroundServiceCompat();
    }

    private void startForegroundServiceCompat() {
        createNotificationChannel();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.putExtra("notification", "hide");

        int pendingFlags = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                ? PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE
                : PendingIntent.FLAG_UPDATE_CURRENT;

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, pendingFlags);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Recycle Bin is Running")
                .setContentText("Monitoring files in the background")
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setContentIntent(pendingIntent)
                .build();

        // Start foreground service (Android 14+ requires type declared in manifest)
        startForeground(NOTIFICATION_ID, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null && manager.getNotificationChannel(CHANNEL_ID) == null) {
                NotificationChannel channel = new NotificationChannel(
                        CHANNEL_ID,
                        "Recycle Bin Service",
                        NotificationManager.IMPORTANCE_LOW
                );
                channel.setLightColor(Color.BLUE);
                channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                manager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (showlog) System.out.println(TAG + " onStartCommand");

        if (fileSystemHandler == null) {
            FileSystemHandler.initializeFileSystemHandler(getApplicationContext());
            fileSystemHandler = FileSystemHandler.getInstance();
            fileSystemHandler.startlisten();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (showlog) System.out.println(TAG + " onDestroy");
        super.onDestroy();

        // Optional: restart service if killed
        Intent broadcastIntent = new Intent(this, SensorRestarterBroadcastReceiver.class);
        // sendBroadcast(broadcastIntent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        if (showlog) System.out.println(TAG + " onTaskRemoved");
        super.onTaskRemoved(rootIntent);

        // Optional: restart service if task removed
        Intent broadcastIntent = new Intent(this, SensorRestarterBroadcastReceiver.class);
        // sendBroadcast(broadcastIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // Not a bound service
    }
}
