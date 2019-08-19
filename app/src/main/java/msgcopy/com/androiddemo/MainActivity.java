package msgcopy.com.androiddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import msgcopy.com.androiddemo.network.DownLoadActivity;
import msgcopy.com.androiddemo.observer.ObserverModeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.snackbar:
                startActivity(new Intent(MainActivity.this, SnackbarActivity.class));
                break;
            case R.id.observer:
                startActivity(new Intent(MainActivity.this, ObserverModeActivity.class));
                break;
            case R.id.threadpool:
                startActivity(new Intent(MainActivity.this, ThreadPoolExecutorActivity.class));
                break;
            case R.id.download:
                startActivity(new Intent(MainActivity.this, DownLoadActivity.class));
                break;
            case R.id.button7:
                startActivity(new Intent(MainActivity.this, AnimatorActivity.class));
                break;
        }
    }
}
