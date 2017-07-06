package msgcopy.com.androiddemo.observer;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by liang on 2017/7/3.
 */

public class CreateObserver implements Observer {

    private String observerName;

    @Override
    public void update(Observable observable, Object o) {
        Log.i("update",observerName+"--"+(String)o);
    }

    public String getObserverName() {
        return observerName;
    }

    public void setObserverName(String observerName) {
        this.observerName = observerName;
    }
}
