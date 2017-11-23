package msgcopy.com.androiddemo.download;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class DownloadTask {

    private static final String TAG = "DownloadTask";

    private Context mContext;
    //要下载的文件信息，
    private FileInfo fileInfo;
    private ThreadDao dao;
    public boolean isPause = false;
    //完成的进度
    private int finished;

    public DownloadTask(Context mContext, FileInfo fileInfo) {
        Log.d(TAG, "DownloadTask: ");
        this.mContext = mContext;
        this.fileInfo = fileInfo;
        dao = new ThreadDaoImpl(mContext);
    }

    public void download() {
        //每次下载任务前，根据url查询线程信息
        List<ThreadInfo> threadInfos = dao.queryThread(fileInfo.url);
        ThreadInfo threadInfo;
        if (threadInfos.size() == 0) {//第一次，创建文件信息
            Log.d(TAG, "download: 1 ");
            threadInfo = new ThreadInfo(0, 0, fileInfo.length, fileInfo.url, 0);
        } else {
            Log.d(TAG, "download: 2 ");
            //以后从集合中取出文件信息
            threadInfo = threadInfos.get(0);
        }
        new DownloadThread(threadInfo).start();
    }

    class DownloadThread extends Thread {

        private ThreadInfo threadinfo;
        private RandomAccessFile raf;
        private HttpURLConnection conn;

        public DownloadThread(ThreadInfo threadInfo) {
            this.threadinfo = threadInfo;
        }

        @Override
        public void run() {
            super.run();
            //第一次数据库中不存在信息，向数据库写入信息
            if (!dao.isExists(threadinfo.url, threadinfo.id)) {
                dao.insertThread(threadinfo);
            }
            //设置下载位置
            try {
                URL url = new URL(threadinfo.url);//下载链接
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(4000);
                //当前下载位置等于起始位置加上已经下载的进度
                int start = threadinfo.start + threadinfo.finished;
                //下载的范围为起始位置到文件长度，因为是单线程下载
                //设置setRequestProperty后返回码是206
                Log.d(TAG, "run: bytes = " + start + " " + threadinfo.end);
                conn.setRequestProperty("Range", "bytes=" + start + "-" + threadinfo.end);
                File file = new File(DownloadService.DOWNLOAD_PATH, fileInfo.fileName);
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);//指定从某个位置起
                Intent intent = new Intent(DownloadService.ACTION_UPDATE);
                finished += threadinfo.finished;//更新完成的进度
                //开始下载
                Log.d(TAG, "run: conn.getResponseCode() "+conn.getResponseCode());
                if (conn.getResponseCode() == 206) {
                    //读取数据
                    int len = -1;
                    long time = System.currentTimeMillis();
                    InputStream stream = conn.getInputStream();
                    byte[] buffer = new byte[1024 << 2];//每次赌徒多少个字节
                    while ((len = stream.read(buffer)) != -1) {
                        //写入文件
                        finished += len;
                        Log.d(TAG, "run: finished "+finished);
                        raf.write(buffer, 0, len);
                        if (System.currentTimeMillis() - time > 200) {
                            time = System.currentTimeMillis();
                            //通知Activity更新进度条
                            intent.putExtra("finished", finished);
                            intent.putExtra("length", threadinfo.end);
                            mContext.sendBroadcast(intent);
                        }
                        //下载暂停，保存进度到数据库
                        if (isPause) {
                            //将当前的信息保存到数据库
                            dao.updateThread(threadinfo.url, threadinfo.id + "", finished);
                            return;
                        }
                    }
                    intent.putExtra("finished", threadinfo.end);
                    intent.putExtra("length", threadinfo.end);
                    mContext.sendBroadcast(intent);
                    //下载完成删除删除下载信息
                    dao.deleteThread(threadinfo.url, threadinfo.id + "");
                    DownloadService.isStarted = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                dao.updateThread(threadinfo.url, threadinfo.id + "", finished);
                DownloadService.isStarted = false;
            } finally {
                {
                    conn.disconnect();
                    try {
                        raf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}