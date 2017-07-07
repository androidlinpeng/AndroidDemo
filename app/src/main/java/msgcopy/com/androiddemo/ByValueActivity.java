package msgcopy.com.androiddemo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import msgcopy.com.androiddemo.msgpolling.EventBusActivity;

public class ByValueActivity extends AppCompatActivity implements ServiceConnection {

    private static final String TAG = "ByValueActivity";

    private TextView mTv;

    public final static int REQUEST_REG = 1;

    private ByvalueReceiver receiver;

    private MyHandler mHandler;

    //    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            mTv.setText("Service:" + msg.obj.toString());
//        }
//    };
    private static class MyHandler extends Handler {

        //防止内存泄露 弱引用
        private final WeakReference<ByValueActivity> mActivity;

        public MyHandler(ByValueActivity activity) {
            this.mActivity = new WeakReference<ByValueActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mActivity.get().mTv.setText("Service:" + msg.obj.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_value);

        mTv = (TextView) findViewById(R.id.mTv);

        EventBus.getDefault().register(this);

        mHandler = new MyHandler(this);

        receiver = new ByvalueReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("broadcast_send_success");
        LocalBroadcastManager.getInstance(ByValueActivity.this).registerReceiver(receiver, filter);

    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        MyService.Binder binder = (MyService.Binder) iBinder;
        MyService myService = binder.getService();
        myService.setCallbask(new MyService.MyCallback() {
            @Override
            public void onDataChange(String data) {
                Log.i(TAG, "onServiceConnected" + data);
                Message msg = new Message();
                msg.obj = data;
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    /*
     *
     * Service传值方法
     * 1、使用意图
     * 2、使用onBind回调传值
     * 3、使用广播传值
     *
     */
    public void startService(View v) {

        Log.i("onStart", "----");
        Intent intentService = new Intent(ByValueActivity.this, MyService.class);
        Bundle bundle = new Bundle();
        bundle.putString("msgService", "服务接受到消息");
        intentService.putExtra("bundle", bundle);
        bindService(intentService, this, BIND_AUTO_CREATE);

//        Intent intentService = new Intent(ByValueActivity.this, MyService.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("msgService", "服务接受到消息");
//        intentService.putExtra("bundle", bundle);
//        startService(intentService);

    }

    /**
     * NAIN UI主线程
     * BACKGROUND 后台线程
     * POSTING 和发布者处在同一个线程
     * ASYNC 异步线程
     *
     * @param myEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MyEvent myEvent) {
        mTv.setText("onEventMainThread:" + myEvent.getMsg());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventPostThread(MyEvent myEvent) {
        mTv.setText("onEventPostThread:" + myEvent.getMsg());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventBackgroundThread(MyEvent myEvent) {
        mTv.setText("onEventBackgroundThread:" + myEvent.getMsg());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventAsync(MyEventAsync myEventAsync) {
        mTv.setText("onEventAsync:" + myEventAsync.getMsg());
        Log.i(TAG, "onEventAsync:" + myEventAsync.getMsg());
    }

    @Subscribe
    public void onEvent(MyEvent myEvent) {
        mTv.setText("onEvent:" + myEvent.getMsg());
        Log.i(TAG, "onEvent:" + myEvent.getMsg());
    }


    public class ByvalueReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                String action = intent.getAction();
                if (action.equals("broadcast_send_success")) {
                    final String msg = intent.getStringExtra("msgContent");
                    if (msg != null) {
                        mTv.setText("Broadcast:" + msg);
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_REG:
                    String result = data.getStringExtra("Result");
                    mTv.setText("Result:" + result);
                    break;
            }
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                Intent intent = new Intent(ByValueActivity.this, Main2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("sendData", "mainsend:哈喽");
                intent.putExtras(bundle);
                // 请求码的值随便设置，但必须>=0
                startActivityForResult(intent, REQUEST_REG);
                break;
            case R.id.eventBus:
                startActivity(new Intent(this, EventBusActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindService(this);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.receiver);

        EventBus.getDefault().unregister(this);
    }
}






