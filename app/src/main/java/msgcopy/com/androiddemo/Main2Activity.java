package msgcopy.com.androiddemo;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            Toast.makeText(this, "sendData:"+bundle.getString("sendData"), Toast.LENGTH_SHORT).show();
        }

        setContentView(R.layout.activity_main2);

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.close:
                Intent intent1 = new Intent();
                intent1.putExtra("Result","main2send:你好");
                setResult(RESULT_OK,intent1);
                finish();
                break;
            case R.id.broadcast:
                Intent intent = new Intent("broadcast_send_success");
                intent.putExtra("msgContent","广播消息");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                finish();
                break;
        }
    }

}
