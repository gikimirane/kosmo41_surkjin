package com.study.android.project3;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private  static final  String TAG = "lecture";

    ViewPager viewPager;
    TabLayout tabLayout;
    SQLiteDatabase database;
    String pwd = "0000";
    int pass = 0;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = getApplicationContext().openOrCreateDatabase("mydiary.sqlite", Activity.MODE_PRIVATE, null);

        createTable();
        checkPasswd();

        viewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabMenu);

        PagerAdapter adapter = new PageAdapter(
                getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        backPressCloseHandler = new BackPressCloseHandler(this);
        backPressCloseHandler.onBackPressed();

    }

    @Override
    public void onBackPressed(){
        backPressCloseHandler.onBackPressed();
    }

    private void createTable(){
        try{
            String sql1 = "create table if not exists password (pwdno integer primary key, pwd text)";
            database.execSQL(sql1);
            String sql2 = "insert into password (pwdno, pwd) values (1, '"+pwd+"')";
            database.execSQL(sql2);
        } catch (SQLException e){
            String sql = "select pwd from password where pwdno=1";
            Cursor cursor = database.rawQuery(sql, null);
            if(cursor.getCount() > 0){
                cursor.moveToNext();
                pwd = cursor.getString(0);
                if(!pwd.equals("0000")) pass = 1;
                cursor.close();
            }
        } catch (Exception e){
        e.printStackTrace();
        } finally {
            database.close();
        }
    }

    public void checkPasswd(){
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        editText.setHint(pass==0 ? "비밀번호(최초:0000)" : "비밀번호");
        editText.setGravity(Gravity.CENTER);

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setView(editText);

        builder.setIcon(R.drawable.password)
               .setTitle("비밀번호를 입력하세요.")
               .setCancelable(false)
               .setPositiveButton("들어가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        if(editText.getText().toString().trim().equals(pwd)){
                        Toast.makeText(getApplicationContext(), "로그인 성공",
                                Toast.LENGTH_SHORT).show();
                        dialog.cancel();}
                        else{
                            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.",
                                    Toast.LENGTH_SHORT).show();
                            checkPasswd();
                        }
                    }
                })
                .setNegativeButton("나가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }
}
