package com.study.android.project3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment4 extends Fragment {
    private  static final  String TAG = "lecture";
    SingerAdapter adapter;
    SQLiteDatabase database;
    Context context;
    LayoutInflater inflater;
    ViewGroup container, rootview;
    TextView tvTday;
    String tDay;
    int rBtnClick = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.inflater = inflater;
        this.container = container;

        rootview = (ViewGroup) inflater.inflate(R.layout.fragment4, container, false);

        context = getContext();
        adapter = new SingerAdapter(context, 3);

        ListView listView1 = rootview.findViewById(R.id.listView1);
        listView1.setAdapter(adapter);
        tvTday = rootview.findViewById(R.id.tvTday);

        RadioGroup radioGroup = rootview.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SingerItem item = (SingerItem)adapter.getItem(position);
                Toast.makeText(context, "selected: " + item.getDay(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("day", item.getDay());
                startActivityForResult(intent, 1);
            }
        });

        selectData();
        tvTday.setText(tDay);
        return rootview;
    }

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener =
            new RadioGroup.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(checkedId == R.id.radioButton1){         rBtnClick = 1;
                    }else if(checkedId == R.id.radioButton2){   rBtnClick = 2;
                    }else if(checkedId == R.id.radioButton3){   rBtnClick = 3;
                    }else{                                      rBtnClick = 4; }
                    Log.d(TAG, "rbtn: " + rBtnClick);
                    adapter = new SingerAdapter(context, 3);

                    ListView listView1 = rootview.findViewById(R.id.listView1);
                    listView1.setAdapter(adapter);
                    selectData();
                    tvTday.setText(tDay);
                }
    };

    public void selectData(){
        String[] sql = {"select day, picture, strftime('%Y-%m-%d','now', '-1 month')||' ~  '||date('now') from mydiary "+
                        "where sday between strftime('%Y%m%d','now', '-1 month') and strftime('%Y%m%d','now') " +
                        "order by sday desc",
                        "select day, picture, strftime('%Y-%m-%d','now', '-2 month')||' ~  '||date('now') from mydiary "+
                        "where sday between strftime('%Y%m%d','now', '-2 month') and strftime('%Y%m%d','now') " +
                        "order by sday desc",
                        "select day, picture, strftime('%Y-%m-%d','now', '-3 month')||' ~  '||date('now') from mydiary "+
                        "where sday between strftime('%Y%m%d','now', '-3 month') and strftime('%Y%m%d','now') " +
                        "order by sday desc",
                        "select day, picture, '처음부터 ~ 오늘까지' from mydiary "+
                        "order by sday desc"};

        try{
            database = context.openOrCreateDatabase("mydiary.sqlite", Activity.MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery(sql[rBtnClick-1], null);

            int count = cursor.getCount();
            Log.d(TAG, "데이터 갯수: " + count);

            int i =0;
            while (i<count){
                cursor.moveToNext();
                String day = cursor.getString(0);
                String picture = cursor.getString(1);
                tDay = cursor.getString(2);

                Log.d(TAG, "# " + day + " : " + picture);
                i++;
                if(picture.equals("null")) continue;
                Log.d(TAG, "# " + picture + " : " + picture.length());

                SingerItem item = new SingerItem(day, picture);
                adapter.addItem(item);
            }
            cursor.close();
            database.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode,data);

        if(requestCode==1 && resultCode == 10){
            adapter = new SingerAdapter(context, 3);

            ListView listView1 = rootview.findViewById(R.id.listView1);
            listView1.setAdapter(adapter);

            selectData();
        }
    }
}