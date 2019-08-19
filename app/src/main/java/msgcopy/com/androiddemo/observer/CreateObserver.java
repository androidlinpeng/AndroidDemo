//package msgcopy.com.androiddemo.observer;
//
//import android.util.Log;
//
//import java.util.Observable;
//import java.util.Observer;
//
///**
// * Created by liang on 2017/7/3.
// */
//
//public class CreateObserver implements Observer {
//
//    private String observerName;
//
//    @Override
//    public void update(Observable observable, Object o) {
//        //第一种推的方式
//        Log.i("update",observerName+"：消息来了--"+o);
//        //第二种辣的模式
//        Log.i("update",observerName+"：新消息来了--"+((CreateSubject)observable).getContent());
//
//    }
//
//    public String getObserverName() {
//        return observerName;
//    }
//
//    public void setObserverName(String observerName) {
//        this.observerName = observerName;
//    }
//}
