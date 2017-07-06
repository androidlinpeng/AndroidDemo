package msgcopy.com.androiddemo.observer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import msgcopy.com.androiddemo.R;

public class ObserverModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer_mode);

        CreateSubject subject = new CreateSubject();

        CreateObserver observer1 = new CreateObserver();
        observer1.setObserverName("小米");

        CreateObserver observer2 = new CreateObserver();
        observer2.setObserverName("华为");

        subject.addObserver(observer1);
        subject.addObserver(observer2);

        subject.notifyObservers("手机商家");

    }
}
