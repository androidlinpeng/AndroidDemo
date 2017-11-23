package msgcopy.com.androiddemo.download;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/21.
 */

public class ThreadInfo implements Serializable {

    public int id;
    public int start;
    public int end;
    public String url;
    public int finished;

    public ThreadInfo() {
    }

    public ThreadInfo(int id, int start, int end, String url, int finished) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.url = url;
        this.finished = finished;
    }
}
