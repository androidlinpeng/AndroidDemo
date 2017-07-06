package msgcopy.com.androiddemo.network;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import msgcopy.com.androiddemo.MyApplication;
import msgcopy.com.androiddemo.R;

public class DownLoadActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DownLoadActivity";
    private String CloudMusic = "http://s1.music.126.net/download/android/CloudMusic_3.4.1.133604_official.apk";
    private String jinritoutiao = "http://gdown.baidu.com/data/wisegame/55dc62995fe9ba82/jinritoutiao_448.apk";
    private String pathZip = "http://cloud9.kaoke.me/smedia/app/sda1/pubtmp/sys/2015/01/zhuanti_12.zip";
    private String pathBitmap = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
    private ProgressDialog mDialog;

    // 模板文件夹路径
    private static String TEMPLATE_DIR = Environment.getExternalStorageDirectory() + File.separator + "Download" + File.separator + "Template" + File.separator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);

        mDialog = new ProgressDialog(this);
        mDialog.setMax(100);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setCancelable(false);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button3:
                Uri uri = Uri.parse(jinritoutiao);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle("下载");
                request.setDescription("今日头条正在下载");
                request.setDestinationInExternalPublicDir("/Download/", "jinritoutiao.apk");
                request.setMimeType(MimeTypeMap.getFileExtensionFromUrl(jinritoutiao));
                request.allowScanningByMediaScanner();
                //设置在什么网络情况下进行下载
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                request.setAllowedOverRoaming(false);// 不允许漫游
                DownloadManager downloadManager = (DownloadManager) MyApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE);
                long id = downloadManager.enqueue(request);
                break;
            case R.id.button4:
                new MyAsyncTask(TASK_PROGRESS).execute();
                break;
            case R.id.button5:
                new MyAsyncTask(TASK_ZIP).execute();
                break;
            case R.id.button6:
                new MyAsyncTask(TASK_BITMAP).execute();
                break;
        }
    }

    private static final int TASK_PROGRESS = 1;
    private static final int TASK_ZIP = 2;
    private static final int TASK_BITMAP = 3;

    public class MyAsyncTask extends AsyncTask<Object, Integer, Void> {

        private int task = -1;

        public MyAsyncTask(int task) {
            this.task = task;
        }

        @Override
        protected void onPreExecute() {
            mDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Object... objects) {
            HttpURLConnection conn = null;
            InputStream is = null;
            OutputStream os = null;
            ZipInputStream zis = null;
            switch (task) {
                case TASK_PROGRESS:
                    try {
                        URL url = new URL(CloudMusic);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setConnectTimeout(5 * 1000);
                        conn.setReadTimeout(60 * 1000);
                        conn.setRequestMethod("GET");
                        is = conn.getInputStream();
                        File file = getFilePath("CloudMusic.apk");
                        os = new FileOutputStream(file);
                        byte[] bs = new byte[1024];
                        int file_length = conn.getContentLength();
                        int total_length = 0;
                        int value = 0;
                        int len;
                        while ((len = is.read(bs)) != -1) {
                            os.write(bs, 0, len);
                            total_length += len;
                            value = (int) ((total_length / (float) file_length) * 100);
                            publishProgress(value);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (null != is) {
                                is.close();
                            }
                            if (null != os) {
                                os.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case TASK_ZIP:
                    try {
                        URL url = new URL(pathZip);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(15 * 1000);
                        conn.setReadTimeout(60 * 1000);
                        conn.setUseCaches(false);
                        is = conn.getInputStream();
                        File filezip = getFilePath("h5code.zip");
                        os = new FileOutputStream(filezip);
                        byte[] bs = new byte[1024];
                        int file_length = conn.getContentLength();
                        int total_length = 0;
                        int value = 0;
                        int len;
                        while ((len = is.read(bs)) != -1) {
                            os.write(bs, 0, len);
                            total_length += len;
                            value = (int) ((total_length / (float) file_length) * 100);
                            publishProgress(value);
                        }

                        //解压文件
                        zis = null;
                        ZipEntry ze = null;
                        byte buf[] = new byte[1024];
                        zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(filezip)));
                        while ((ze = zis.getNextEntry()) != null) {
                            File file = new File(TEMPLATE_DIR + ze.getName());
                            if (ze.isDirectory()) {
                                if (!file.mkdirs()) {
                                }
                            } else {
                                File parent = file.getParentFile();
                                if (!parent.exists() && !parent.mkdirs()) {
                                }
                                if (!file.exists()) {
                                    file.createNewFile();
                                } else {
                                    file.delete();
                                    file.createNewFile();
                                }
                                FileOutputStream fileOut = new FileOutputStream(file);
                                for (int i = zis.read(buf); i > 0; i = zis.read(buf)) {
                                    fileOut.write(buf, 0, i);
                                }
                                fileOut.close();
                                zis.closeEntry();
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (null != is) {
                                is.close();
                            }
                            if (null != os) {
                                os.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case TASK_BITMAP:
                    try {
                        URL url = new URL(pathBitmap);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setConnectTimeout(5 * 1000);
                        conn.setReadTimeout(60 * 1000);
                        conn.setRequestMethod("GET");
                        is = conn.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                        Bitmap bitmap = BitmapFactory.decodeStream(bis);
                        File file = getFilePath("baidu.png");
                        FileOutputStream fileos = new FileOutputStream(file);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileos);

                        bitmap.recycle();
                        fileos.flush();
                        fileos.close();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (null != is) {
                                is.close();
                            }
                            if (null != os) {
                                os.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mDialog.setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mDialog.dismiss();
            switch (task) {
                case TASK_PROGRESS:

                    break;
                case TASK_ZIP:

                    break;
                case TASK_BITMAP:

                    break;
            }
            super.onPostExecute(aVoid);
        }
    }


    public File getFilePath(String name) {
        File file = new File(Environment.getExternalStorageDirectory() + "/Download/" + name);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
