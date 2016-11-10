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
import android.widget.TextView;

import com.cretin.www.autorefreshlistview.view.AutoRefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private AutoRefreshListView listView;
    private ListViewAdapter adapter;
    private List<String> list;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (list.size() >= 15) {
                listView.noMoreData();
            } else {
                SimpleDateFormat time = new SimpleDateFormat("HH:mm:sss");
                list.add(time.format(new Date(System.currentTimeMillis())));
                adapter.notifyDataSetChanged();
                listView.loadComplete();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (AutoRefreshListView) findViewById(R.id.listview);
        list = new ArrayList<>();
        //造假数据
        for (int i = 0; i < 5; i++) {
            SimpleDateFormat time = new SimpleDateFormat("HH:mm:sss");
            list.add(time.format(new Date(System.currentTimeMillis())));
        }
        adapter = new ListViewAdapter();
        listView.setAdapter(adapter);
        listView.setCallBack(new AutoRefreshListView.RefreshCallBack() {
            @Override
            public void onRefreshing() {
                Log.e("HHHHHHH", "加载数据啦");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1);
                    }
                }, 500);
            }
        });
    }

    class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View views = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_listview, null);
            TextView tv = (TextView) views.findViewById(R.id.tv_content);
            tv.setText("我是Cretin\n一个可爱的小男孩 "+list.get(i));
            return views;
        }
    }
}
