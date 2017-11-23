package msgcopy.com.androiddemo.singleton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import msgcopy.com.androiddemo.R;


public class SingletonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleton);

        SingletonManager instence = SingletonManager.getInstence(this);

    }
}
