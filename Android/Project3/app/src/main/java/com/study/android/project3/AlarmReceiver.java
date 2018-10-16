package com.study.android.project3;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    private  static final  String TAG = "lecture";
    SQLiteDatabase database;

    @Override
    public void onReceive(Context context, Intent intent) {
        int count=0;
        String sql = "select day from mydiary where sday = strftime('%Y%m%d','now')";
        try{
            database = context.openOrCreateDatabase("mydiary.sqlite", Activity.MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery(sql, null);
            count = cursor.getCount();
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d(TAG, "지정한 시간의 알람입니다.");
        if(count > 0) return;

        String channelId = "channel";
        String channelName = "Channel Name";

        NotificationManager notifManager
                = (NotificationManager) context.getSystemService  (Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notifManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, channelId);
        Intent notificationIntent = new Intent(context,MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        int requestID = (int) System.currentTimeMillis();
        PendingIntent pendingIntent
                = PendingIntent.getActivity(context, requestID
                , notificationIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle("오늘 작성한 내용이 없습니다.")
                .setContentText("여기를 눌러 앱을 실행시켜 작성하세요.")
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.diary_small)
                .setContentIntent(pendingIntent);

        notifManager.notify(0, builder.build());
    }
}
