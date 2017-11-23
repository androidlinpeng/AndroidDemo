package msgcopy.com.androiddemo.download;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public interface ThreadDao {

    void insertThread(ThreadInfo threadInfo);

    void deleteThread(String url, String thread_id);

    void updateThread(String url, String thread_id, int finished);

    List<ThreadInfo> queryThread(String url);

    boolean isExists(String url, int thread_id);

}
