package msgcopy.com.androiddemo.msgpolling;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.UUID;

import msgcopy.com.androiddemo.MyApplication;
import msgcopy.com.androiddemo.R;

/**
 * Created by liang on 2017/6/28.
 */

public class MsgService extends Service {

    private final static String TAG = "MainService";

    private Handler handler ;

    @Override
    public void onCreate() {
        super.onCreate();

        //handler消息轮询
        handler = new Handler();
        handler.post(runnable);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable,5000);
            showNotification();
            System.out.println("handler: New message!");
        }
    };

    //弹出Notification
    private void showNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplication.getInstance());
        builder.setTicker("1111111");
        builder.setContentTitle("22222222");
        builder.setContentText("33333333");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);

        Intent intent = new Intent(MyApplication.getInstance(), MsgPollingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getInstance(), UUID.randomUUID().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) MyApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //定时器消息轮询
//        TimerManager.getInstance().startPolling();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

//        TimerManager.getInstance().stopPolling();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
