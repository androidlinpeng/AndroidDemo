package msgcopy.com.androiddemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 *
 * handler.sendMessage();
 * handler.post();
 * 在activity中可以 runOnUiThread();
 * 在子view中可以 view.post()
 *
 */

public class UpdateUIActivity extends AppCompatActivity {

    private TextView mTv;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    mTv.setText(msg.getData().getString("Result"));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ui);

        mTv = (TextView)findViewById(R.id.textView);

        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTv.post(new Runnable() {
                    @Override
                    public void run() {
                        mTv.setText("view更新UI");
                    }
                });
            }
        });

        upDateUI();

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mTv.setText("更新了Handler.Post");
            }
        });

    }

    private void upDateUI(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                //通知更新UI
                //Message msg = new Message();
                //我们可以使用构造方法来创建Message，但出于节省内存资源的考量，我们应该使用Message.obtain()从消息池中获得空消息对象
                Message msg = mHandler.obtainMessage();
                msg.what = 1;
                Bundle bundle = new Bundle();//消息数据实体
                bundle.putString("Result", "sendMsg");
                msg.setData(bundle);
//                mHandler.sendMessage(msg);
                //如果使用的是Message.obtain()我们可以直接
                msg.sendToTarget();
            }
        }.start();


        //
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTv.setText("更新了");
            }
        });

    }
}
