package msgcopy.com.androiddemo.singleton;

import android.content.Context;

/**
 * Created by liang on 2017/7/7.
 */

public class SingletonManager {

    private static SingletonManager mInstance;
    private Context context;

    //饿汉式
    private static class SingletonLoager{
        private static final SingletonManager instance = new SingletonManager();
    }

    public static SingletonManager getInstence(Context context){


        //防止内存泄露
        context = context.getApplicationContext();

        //懒汉式线程安全
//        if (mInstance == null){
//            synchronized (SingletonManager.class) {
//                mInstance = new SingletonManager();
//            }
//        }
//        return mInstance;

        return SingletonLoager.instance;
    }

}
