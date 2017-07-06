package msgcopy.com.androiddemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class ThreadPoolExecutorActivity extends AppCompatActivity {

    private static final String TAG = "myThreadPool";
    private TextView mTextView;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_executor);
        mTextView = (TextView) findViewById(R.id.mTv);
        mDialog = new ProgressDialog(this);
        mDialog.setMax(100);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setCancelable(false);

        //3.0版本前并发执行多个任务时，超过138会马上出现Java.util.concurrent.RejectedExecutionException；
        for (int i = 1; i <= 140; i++) {
            //自定义线程池
//            Executor exec = new ThreadPoolExecutor(5, 100, 10,
//                    TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
//            new MyAsyncTask2().executeOnExecutor(exec);

            new MyAsyncTask2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

//        MyAsyncTask myAsyncTask = new MyAsyncTask();
//        myAsyncTask.execute();
//        //将task的状态标记为cancel
//        if (myAsyncTask != null && myAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
        //取消任务
//            myAsyncTask.cancel(true);
//        }

    }

    private class MyAsyncTask2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.e(TAG, Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
            Log.e(TAG, Thread.currentThread().getName() + " onPreExecute ");
        }

        @Override
        protected Void doInBackground(Void... params) {
            // 模拟数据的加载,耗时的任务
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            Log.e(TAG, Thread.currentThread().getName() + " doInBackground ");
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mDialog.setProgress(values[0]);
            Log.e(TAG, Thread.currentThread().getName() + " onProgressUpdate ");
        }

        @Override
        protected void onPostExecute(Void result) {
            // 进行数据加载完成后的UI操作
            mDialog.dismiss();
            mTextView.setText("下载成功");
            Log.e(TAG, Thread.currentThread().getName() + " onPostExecute ");
        }
    }
}
