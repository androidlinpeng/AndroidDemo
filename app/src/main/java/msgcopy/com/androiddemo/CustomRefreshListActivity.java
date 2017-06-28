package msgcopy.com.androiddemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomRefreshListActivity extends AppCompatActivity implements CustomRefreshListView.OnRefreshListener {

    private CustomRefreshListView cr_listView;
    private List<String> datas = new ArrayList<>();

    private SimpleBaseAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_view);

        for (int i = 0; i < 20; i++) {
            datas.add("数据" + i);
        }

        cr_listView = ((CustomRefreshListView) findViewById(R.id.cr_listView));

        mAdapter = new SimpleBaseAdapter(datas, this);

        cr_listView.setAdapter(mAdapter);

        cr_listView.setOnRefreshListener(this);
    }

    @Override
    public void onPullRefresh() {
        cr_listView.postDelayed(new Runnable() {
            @Override
            public void run() {
                datas.clear();
                for (int i = 0; i < 20; i++) {
                    datas.add("刷新新数据" + i);
                }
                mAdapter.notifyDataSetChanged();
                cr_listView.completeRefresh();
            }
        }, 1000);

    }

    @Override
    public void onLoadingMore() {
        cr_listView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int count = mAdapter.getCount();
                for (int i = count; i < count + 10; i++) {
                    datas.add("添加新数据" + i);
                }
                mAdapter.notifyDataSetChanged();
                cr_listView.completeRefresh();
            }
        }, 1000);
    }
}

