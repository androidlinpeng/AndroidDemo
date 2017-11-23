package msgcopy.com.androiddemo.download;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/11/21.
 */

public class DownloadService extends Service {

    private static final String TAG = "DownloanService";
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/aadownloads/";
    public static final int MSG_INIT_THREAD = 0;
    private DownloadTask mTask = null;
    public static boolean isStarted = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_INIT_THREAD:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    Log.d(TAG, "length: " + fileInfo.length);
                    //将文件信息传给下载任务
                    mTask = new DownloadTask(DownloadService.this, fileInfo);
                    mTask.download();//启动任务下载
                    break;
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        if (ACTION_START.equals(intent.getAction())) {
            //获取文件信息
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileinfo");
            if (!isStarted) {
                new initThread(fileInfo).start();
                isStarted = true;
            }
        } else if (ACTION_STOP.equals(intent.getAction())) {
            if (mTask != null) {
                mTask.isPause = true;
                isStarted = false;
            }
        }

        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class initThread extends Thread {
        private FileInfo fileInfo;
        private RandomAccessFile raf;
        private HttpURLConnection conn;

        public initThread(FileInfo fileInfo) {
            this.fileInfo = fileInfo;
        }

        @Override
        public void run() {
            super.run();
            try {
                URL url = new URL(fileInfo.url);//www.imooc.com/mobile/imooc.apk
                conn = (HttpURLConnection) url.openConnection();//打开链接
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);
                int len = -1;
                if (conn.getResponseCode() == 200) {
                    len = conn.getContentLength();//获取服务器文件长度
                }
                if (len < 0) {
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    dir.mkdir();//目录不存在创建目录
                }
                File file = new File(dir, fileInfo.fileName);
                //在指定路径下创建一个个服务器文件大小一样的文件
                raf = new RandomAccessFile(file, "rwd");
//                raf.setLength(len);//设置临时文件的长度为服务器文件长度
                fileInfo.length = len;//设置文件信息
                handler.obtainMessage(MSG_INIT_THREAD, fileInfo).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null)
                    conn.disconnect();
                try {
                    if (raf != null)
                        raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}