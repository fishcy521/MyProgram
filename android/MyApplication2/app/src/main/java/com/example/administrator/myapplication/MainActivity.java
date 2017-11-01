package com.example.administrator.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */

public class MainActivity extends AppCompatActivity {

    private ListView lv_1;
    private Context context;
    private List<String> list = new ArrayList<>();

    private BaseAdapter ba = new BaseAdapter() {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater lif = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = lif.inflate(R.layout.item_layout,null);

            TextView itme_tv_1 = (TextView) view.findViewById(R.id.item_tv_1);
            String itemValue = getItem(position);
            itme_tv_1.setText(itemValue);
            return view;
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.main_layout);

        lv_1 = (ListView) findViewById(R.id.lv_1);

        lv_1.setAdapter(ba);
        list = getData(35);

        list = getData(14);

    }

    private List<String> getData(int end) {

        List<String> lstResult = new ArrayList<>();
        for (int i = 0; i < end; i++) {
            lstResult.add("我是第"+i);
        }
        return lstResult;
    }
}
