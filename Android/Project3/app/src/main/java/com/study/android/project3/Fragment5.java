package com.study.android.project3;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Fragment5 extends Fragment {
    private  static final  String TAG = "lecture";
    ViewGroup rootview;

    AlarmManager am;
    Intent intent;
    PendingIntent receiver;
    TextView tvAlarm;
    Button btnAlarmSet, btnAlarmOn, btnAlarmOff;
    String alarmTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = (ViewGroup) inflater.inflate(R.layout.fragment5, container, false);

        Button button = rootview.findViewById(R.id.btnSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePswd();
            }
        });

        am = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);

        intent = new Intent(getContext(), AlarmReceiver.class);
        receiver = PendingIntent.getBroadcast(getContext(),
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        tvAlarm = rootview.findViewById(R.id.tvAlarm);
        btnAlarmSet = rootview.findViewById(R.id.btnAlarmSet);
        btnAlarmOn = rootview.findViewById(R.id.btnAlarmOn);
        btnAlarmOff = rootview.findViewById(R.id.btnAlarmOff);

        btnAlarmSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar C = Calendar.getInstance(Locale.KOREA);
                int mHour = C.get(Calendar.HOUR_OF_DAY);
                int mMin = C.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        AlertDialog.THEME_HOLO_LIGHT,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                btnAlarmSet.setText(String.format("%02d : %02d", hourOfDay,minute));
                            }
                        }, mHour, mMin, false);
                timePickerDialog.show();
        }
        });

        btnAlarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAlarm("on");
                try {
                    Date sTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(alarmTime + ":00");
                    am.setInexactRepeating(AlarmManager.RTC_WAKEUP, sTime.getTime(), 24 * 60 * 60 * 1000, receiver);
                } catch(ParseException e){
                    e.printStackTrace();
                }
            }
        });

        btnAlarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAlarm("off");
                am.cancel(receiver);
            }
        });

        createTable();
        return rootview;
    }

    void updatePswd(){

        TextView etPswd = rootview.findViewById(R.id.etPswd);
        TextView etPswdchk = rootview.findViewById(R.id.etPswdchk);
        if(etPswd.getText().toString().length() < 4 || !etPswd.getText().toString().equals(etPswdchk.getText().toString())){
            Toast.makeText(getContext(), "비밀번호가 일치하지 않습니다.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase database = getContext().openOrCreateDatabase("mydiary.sqlite", Activity.MODE_PRIVATE, null);
        try{
            String sql = "update password set pwd = '" + etPswd.getText().toString() + "'";
            database.execSQL(sql);
            database.close();
            Toast.makeText(getContext(), "변경이 되었습니다.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void updateAlarm(String onoff){

        SQLiteDatabase database = getContext().openOrCreateDatabase("mydiary.sqlite", Activity.MODE_PRIVATE, null);
        try{
            String sql = "update alarm set onoff = '" + onoff + "', time = '" + btnAlarmSet.getText().toString() + "'";
            database.execSQL(sql);
            database.close();
            tvAlarm.setText (onoff.equals("on") ? "알람 설정 중" : "알람 해제");
            alarmTime = alarmTime.substring(0,10) + " " + btnAlarmSet.getText().toString().replace(" ", "");
            Toast.makeText(getContext(), "변경이 되었습니다.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createTable(){
        SQLiteDatabase database = getContext().openOrCreateDatabase("mydiary.sqlite", Activity.MODE_PRIVATE, null);
        try{
            String sql1 = "create table if not exists alarm (seq integer primary key, onoff text, time text)";
            database.execSQL(sql1);
            String sql2 = "insert into alarm (seq, onoff, time) values (1, 'off', '00 : 00')";
            database.execSQL(sql2);
        } catch (SQLException e){
            String sql = "select onoff, time, date('now') from alarm";
            Cursor cursor = database.rawQuery(sql, null);
            if(cursor.getCount() > 0){
                cursor.moveToNext();
                String onOff = cursor.getString(0);
                String time = cursor.getString(1);
                alarmTime = cursor.getString(2);
                cursor.close();
                tvAlarm.setText (onOff.equals("on") ? "알람 설정 중" : "알람 해제");
                btnAlarmSet.setText(time);
                Log.d(TAG, "alarm: " + alarmTime + " " + onOff);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            database.close();
        }
    }
}
