package msgcopy.com.androiddemo.msgpolling;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.UUID;

import msgcopy.com.androiddemo.MyApplication;
import msgcopy.com.androiddemo.R;

/**
 * Created by liang on 2017/6/28.
 */

public class PollingService extends Service {

    public static final String ACTION = "PollingService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart(Intent intent, int startId) {
        new PollingThread().start();
    }


    //弹出Notification
    private void showNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplication.getInstance());
        builder.setTicker("1111111");
        builder.setContentTitle("22222222");
        builder.setContentText("33333333");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);

        Intent intent = new Intent(this, MsgPollingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getInstance(), UUID.randomUUID().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) MyApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());

    }

    public class PollingThread extends Thread {
        @Override
        public void run() {
            showNotification();
            System.out.println("New message!");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
