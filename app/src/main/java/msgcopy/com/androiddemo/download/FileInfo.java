package msgcopy.com.androiddemo.download;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/21.
 */

public class FileInfo implements Serializable {

    public  int id;
    public String url;
    public String fileName;
    public int finished;
    public int length;

    public FileInfo(int id, String url, String fileName, int finished, int length) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.finished = finished;
        this.length = length;
    }
}
