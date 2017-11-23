package msgcopy.com.androiddemo.download;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class ThreadDaoImpl implements ThreadDao {

    DBHelper dbHelper;

    public ThreadDaoImpl(Context context) {
        dbHelper = new DBHelper(context);
    }

    @Override
    public void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("thread_id", threadInfo.id);
        values.put("url", threadInfo.url);
        values.put("start", threadInfo.start);
        values.put("end", threadInfo.end);
        values.put("finished", threadInfo.finished);
        db.insert("thread_info", null, values);
        db.close();
    }

    @Override
    public void deleteThread(String url, String thread_id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int count = db.delete("thread_info", "url = ? and thread_id = ?", new String[]{url, thread_id});
        db.close();
    }

    @Override
    public void updateThread(String url, String thread_id, int finished) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("finished", finished);
        db.update("thread_info", values, "url = ? and thread_id = ?", new String[]{url, thread_id});
    }

    @Override
    public List<ThreadInfo> queryThread(String url) {
        List<ThreadInfo> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("thread_info", null, "url = ?",
                new String[]{url}, null, null, null);
        while (cursor.moveToNext()) {
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.id = cursor.getInt(cursor.getColumnIndex("thread_id"));
            threadInfo.url = cursor.getString(cursor.getColumnIndex("url"));
            threadInfo.start = cursor.getInt(cursor.getColumnIndex("start"));
            threadInfo.end = cursor.getInt(cursor.getColumnIndex("end"));
            threadInfo.finished = cursor.getInt(cursor.getColumnIndex("finished"));
            list.add(threadInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean isExists(String url, int thread_id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("thread_info", null, "url = ? and thread_id = ?",
                new String[]{url, thread_id + ""}, null, null, null);
        boolean exists = cursor.moveToNext();
        cursor.close();
        db.close();
        return exists;
    }
}