package msgcopy.com.androiddemo.observer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import msgcopy.com.androiddemo.R;

public class ObserverModeActivity extends AppCompatActivity implements Observer, View.OnClickListener {

    private static final String TAG = "ObserverModeActivity";
    private CreateSubject subject;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer_mode);
        content = (TextView) findViewById(R.id.content);
        subject = CreateSubject.getInstence();
        //订阅
        subject.addObserver(this);

    }

    @Override
    public void update(Observable observable, Object o) {
        content.setText("" + o);
        //第一种推的方式
        Log.i(TAG, "update：消息来了--" + o);
        //第二种拉的模式
        Log.i(TAG, "update：新消息来了--" + ((CreateSubject) observable).getContent());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update:
                subject.setContent("手机商家");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消订阅
        subject.deleteObserver(this);
    }
}
