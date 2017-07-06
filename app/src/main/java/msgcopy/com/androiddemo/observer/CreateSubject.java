package msgcopy.com.androiddemo.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by liang on 2017/7/3.
 */

public class CreateSubject extends Observable {

    private List<Observer> observers = new ArrayList<Observer>();

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        this.observers.add(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
        this.observers.remove(o);
    }

    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
        for (Observer observer : observers) {
            setChanged();
            notifyObservers(arg);
        }
    }
}
