package msgcopy.com.androiddemo.msgpolling;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import msgcopy.com.androiddemo.MyApplication;
import msgcopy.com.androiddemo.R;

/**
 * Created by liang on 2017/6/28.
 */

public class TimerManager {

    public static final int TIME_PERIOD = 5 * 1000;
    private Timer timer;
    private TimerTask timerTask;

    private boolean isRunning = false;

    private static class SingletonLoader{
        private static final TimerManager mInstance=new TimerManager();
    }

    public static TimerManager getInstance(){
        return SingletonLoader.mInstance;
    }

    public boolean isRunning(){
        return this.isRunning;
    }

    public void startPolling(){
        if (!isRunning()){
            isRunning = true;
            if (timer ==null){
                timer = new Timer();
            }
            if (timerTask ==null){
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        showNotification();
                        System.out.println("timer: New message!");
                    }
                };
            }
            timer.schedule(timerTask,0,TIME_PERIOD);
        }
    }

    public void stopPolling(){
        if (isRunning){
            isRunning = false;
            if (timerTask!=null){
                timerTask.cancel();
                timerTask = null;
            }
            if (timer!=null){
                timer.cancel();
                timer = null;
            }
        }
    }

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

}
