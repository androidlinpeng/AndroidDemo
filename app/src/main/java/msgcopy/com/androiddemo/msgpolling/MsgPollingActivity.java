package msgcopy.com.androiddemo.msgpolling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import msgcopy.com.androiddemo.R;

public class MsgPollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_polling);

        //service+AlarmManager+Thread
//        PollingManager.startPollingService(this, 5, PollingService.class, PollingService.ACTION);

        startService(new Intent(this,MsgService.class));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        PollingManager.stopPollingService(this,PollingService.class,PollingService.ACTION);

        stopService(new Intent(this,MsgService.class));
    }
}
