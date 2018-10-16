package com.study.android.project3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class SingerAdapter extends BaseAdapter {

    Context context;
    int adapterCode;
    ArrayList<SingerItem> items = new ArrayList<>();

    public SingerAdapter(Context context, int adapterCode) {
        this.context = context;
        this.adapterCode = adapterCode;
    }

    public void addItem(SingerItem item) {
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (adapterCode == 1) return singerItemView(position, convertView);
        else if (adapterCode == 2) return singerItemAllView(position, convertView);
        else return singerItemImageView(position, convertView);
    }

    private View singerItemView(int position, View convertView) {
        SingerItemView view = null;

        if (convertView == null)
            view = new SingerItemView(context);
        else view = (SingerItemView) convertView;

        SingerItem item = items.get(position);
        view.setDay(item.getDay());
        view.setWeather(item.getWeather());
        view.setContents(item.getContents());
        view.setImage(item.getResId());

        return view;
    }

    private View singerItemAllView(int position, View convertView) {
        SingerItemAllView view;

        if (convertView == null) {
            view = new SingerItemAllView(context);
        } else {
            view = (SingerItemAllView) convertView;
        }
        SingerItem item = items.get(position);
        view.setDay(item.getDay());
        view.setWeather(item.getWeather());
        view.setSpecial(item.getSpecial());
        view.setContents(item.getContents());
        view.setImage(item.getPicture());
        return view;
    }

    private View singerItemImageView(int position, View convertView) {
        SingerItemImageView view = null;

        if(convertView == null)
        {
            view = new SingerItemImageView(context);
        }else{
            view = (SingerItemImageView) convertView;
        }
        SingerItem item = items.get(position);
        view.setDay(item.getDay());
        view.setImage(item.getPicture());

        return view;
    }
}
