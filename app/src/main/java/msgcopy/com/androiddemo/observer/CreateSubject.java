package msgcopy.com.androiddemo.observer;

import java.util.Observable;

/**
 * Created by liang on 2017/7/3.
 */

public class CreateSubject extends Observable {

    private String content ;

    private static CreateSubject mInstence = new CreateSubject();

    public static CreateSubject getInstence(){
        return mInstence;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        //用java提供的Observer模式是，这句话不可少
        this.setChanged();
        //直接把该类传过去
//        this.notifyObservers(this);
        //直接把content传过去
        this.notifyObservers(content);
    }
}
