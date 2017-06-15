package msgcopy.com.androiddemo;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by liang on 2017/4/6.
 */

public class MyService extends Service {

    private int count = 0;

    private static final String TAG = "MyService";

    public interface MyCallback {

        void onDataChange(String data);
    }

    public MyCallback myCallback;

    public void setCallbask(MyCallback myCallback) {
        this.myCallback = myCallback;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
        //接收通过意图传来的值
        Bundle bundle = intent.getBundleExtra("bundle");
        if (null != bundle) {
            String msg = bundle.getString("msgService");
            Toast.makeText(this, "msgService:" + msg, Toast.LENGTH_SHORT).show();
        }

        return new Binder();
    }

    //onBind()将返回给客户端一个IBind接口实例，IBind允许客户端回调服务的方法，
    // 比如得到Service的实例、运行状态或其他操作
    public class Binder extends android.os.Binder {

        public MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate");

        //创建一个线程，每秒计数器加一，并在赋值给回调
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                    count++;
                    myCallback.onDataChange("count:"+count);
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand");
        //START_STICKY：如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。随后系统会尝试重新创建service，由于服务状态为开始状态，所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。如果在此期间没有任何启动命令被传递到service，那么参数Intent将为null。
        //START_NOT_STICKY：“非粘性的”。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统不会自动重启该服务
        //START_REDELIVER_INTENT：重传Intent。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统会自动重启该服务，并将Intent的值传入。
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG,"onStart");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }
}








