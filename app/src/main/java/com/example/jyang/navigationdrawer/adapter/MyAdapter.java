package com.example.jyang.navigationdrawer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jyang.navigationdrawer.R;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends BaseAdapter {

    private  Context context;
    private int layout;
    private List<String> values;

    public MyAdapter(Context context, int layout, List<String> values){
        this.context = context;
        this.layout = layout;
        this.values = values;
    }
    @Override
    public int getCount() {
        return this.values.size();
    }

    @Override
    public Object getItem(int position) {
        return this.values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.listview_item, null);

        String currentName = values.get(position);

        TextView textView = (TextView) v.findViewById(R.id.text1);
        textView.setText(currentName);
        return v;
    }
}
