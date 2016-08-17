package com.cretin.www.autorefreshlistview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cretin.www.autorefreshlistview.view.AutoRefreshListView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private AutoRefreshListView listView;
    private int count = 20;
    private ListViewAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (count == 50) {
                listView.noMoreData();
            } else {
                count += 10;
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (AutoRefreshListView) findViewById(R.id.listview);
        adapter = new ListViewAdapter();
        listView.setAdapter(adapter);
        listView.setCallBack(new AutoRefreshListView.RefreshCallBack() {
            @Override
            public void onRefresh() {
                Log.e("HHHHHHH", "加载数据啦");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1);
                        listView.loadComplete();
                    }
                }, 2000);
            }
        });
    }

    class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View views = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_listview, null);
            return views;
        }
    }
}
