package com.study.android.project3;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.CalendarView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Fragment1 extends Fragment {
    private  static final  String TAG = "lecture";
    CalendarView calendarView;
    int yy, mm, dd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootview =
                (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

        calendarView = rootview.findViewById(R.id.calendarView);
        final Calendar calendar = Calendar.getInstance();
        yy = calendar.get(Calendar.YEAR);
        mm = calendar.get(Calendar.MONTH);
        dd = calendar.get(Calendar.DAY_OF_MONTH);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int y, int m, int d) {
                yy = y; mm = m; dd = d;
                addData(yy, mm, dd);
               // Toast.makeText(getContext(), "Selected Date:\n" + "Day = " + d+ "\n" + "Month = " + m + "\n" + "Year = " + y, Toast.LENGTH_LONG).show();
            }
        });

        Button button = rootview.findViewById(R.id.btnAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData(yy, mm, dd);
            }
        });
        return rootview;
    }

    public void addData(int yy, int mm, int dd){
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.KOREAN);
        String weekDay = dayFormat.format(new Date(yy,mm,dd-1));
        String addDay = yy + "년 " + (mm+1) + "월 "+ dd +"일 (" +  weekDay.replace("요일",")");
        String sday = String.format("%d%02d%02d", yy, mm+1, dd);

        SQLiteDatabase database = getContext().openOrCreateDatabase("mydiary.sqlite", Activity.MODE_PRIVATE, null);
        String sql = "select day from mydiary where day = '"+addDay+"'";
        try{
            Cursor cursor = database.rawQuery(sql, null);
            int count = cursor.getCount();
            if(count == 1){
                Intent intent = new Intent(getContext(), UpdateActivity.class);
                intent.putExtra("day", addDay);
                startActivity(intent);
            }else{
                Intent intent = new Intent(getContext(), AddActivity.class);
                intent.putExtra("day", addDay);
                intent.putExtra("sday", sday);
                startActivity(intent);
            }
            cursor.close();
        }catch (SQLException e){
            Intent intent = new Intent(getContext(), AddActivity.class);
            intent.putExtra("day", addDay);
            intent.putExtra("sday", sday);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
