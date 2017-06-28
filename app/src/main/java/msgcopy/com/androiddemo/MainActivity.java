package msgcopy.com.androiddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import msgcopy.com.androiddemo.msgpolling.MsgPollingActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.updateUI:
                startActivity(new Intent(MainActivity.this,UpdateUIActivity.class));
                break;
            case R.id.byValue:
                startActivity(new Intent(MainActivity.this,ByValueActivity.class));
                break;
            case R.id.limb:
                startActivity(new Intent(MainActivity.this,LimbActivity.class));
                break;
            case R.id.permission:
                startActivity(new Intent(MainActivity.this,PermissionActivity.class));
                break;
            case R.id.snackbar:
                startActivity(new Intent(MainActivity.this,SnackbarActivity.class));
                break;
            case R.id.listView:
                startActivity(new Intent(MainActivity.this,CustomRefreshListActivity.class));
                break;
            case R.id.polling:
                startActivity(new Intent(MainActivity.this,MsgPollingActivity.class));
                break;
        }
    }
}
