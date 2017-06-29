package msgcopy.com.androiddemo.msgpolling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

import msgcopy.com.androiddemo.MyEvent;
import msgcopy.com.androiddemo.MyEventAsync;
import msgcopy.com.androiddemo.R;

public class EventBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);

        EventBus.getDefault().post(new MyEventAsync("MyEventAsync Message!"));

        EventBus.getDefault().post(new MyEvent("EventBus Message!"));

    }
}
