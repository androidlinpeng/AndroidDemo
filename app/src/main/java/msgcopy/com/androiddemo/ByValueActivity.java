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

public class ByValueActivity extends AppCompatActivity implements ServiceConnection {

    private static final String TAG = "ByValueActivity";

    private TextView mTv;

    public final static int REQUEST_REG = 1;

    private ByvalueReceiver receiver;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mTv.setText("Service:" + msg.obj.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_value);

        mTv = (TextView) findViewById(R.id.mTv);

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
                Log.i(TAG,"onServiceConnected"+data);
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

        Log.i("onStart","----");
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
        Intent intent = new Intent(ByValueActivity.this, Main2Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString("sendData", "mainsend:哈喽");
        intent.putExtras(bundle);
        // 请求码的值随便设置，但必须>=0
        startActivityForResult(intent, REQUEST_REG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindService(this);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.receiver);
    }
}






