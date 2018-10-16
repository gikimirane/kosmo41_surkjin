package com.study.android.project3;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;

public class AddActivity extends AppCompatActivity {
    private static final  String TAG = "lecture";
    private static final int MAX_IMAGE=10;
    TextView tvDay, etSpecial, etContents;
    SQLiteDatabase database;
    String sDay, filePath1, iSday;
    LinearLayout lineLayout1;
    ImageButton ibtnWeather;
    Dialog weatherDialog;
    ImageView imageView1;
    int weather = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent intent = getIntent();
        sDay = intent.getStringExtra("day");
        iSday = intent.getStringExtra("sday");
        tvDay = findViewById(R.id.tvDay);
        tvDay.setText(sDay);
        ibtnWeather = findViewById(R.id.ibtnWeather);
        etSpecial = findViewById(R.id.etSpecial);
        etContents = findViewById(R.id.etContents);
        lineLayout1 = findViewById(R.id.lineLayout1);
        imageView1 = findViewById(R.id.imageView1);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        ibtnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDialog = new Dialog(AddActivity.this);
                weatherDialog.setContentView(R.layout.select_weather);
                weatherDialog.setTitle("날씨를 선택하세요.");
                final ImageButton[] iBtn = new ImageButton[6];
                int[] r_id = {R.id.iBtn0, R.id.iBtn1, R.id.iBtn2, R.id.iBtn3, R.id.iBtn4, R.id.iBtn5};

                for(int i=0; i<6; i++){
                    iBtn[i] = weatherDialog.findViewById(r_id[i]);
                }

                iBtn[0].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            iBtnclicked(0); }});
                iBtn[1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iBtnclicked(1); }});
                iBtn[2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iBtnclicked(2); }});
                iBtn[3].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iBtnclicked(3); }});
                iBtn[4].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iBtnclicked(4); }});
                iBtn[5].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iBtnclicked(5); }});

                weatherDialog.show();
            }
        });

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPicture(v);
            }
        });
    }

    public void iBtnclicked(int i){
        int[] w_id = {R.drawable.clear, R.drawable.partly, R.drawable.cloudy,
                      R.drawable.rain, R.drawable.thunder, R.drawable.snow};
        ibtnWeather.setImageResource(w_id[weather=i]);
        weatherDialog.dismiss();
    }

    private void createDatabase(){
        try{
            database = openOrCreateDatabase("mydiary.sqlite", Activity.MODE_PRIVATE, null);

            Log.d(TAG, "데이터베이스 만듬: " + "mydiary.sqlite");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createTable(){
        String sql1 = "drop table mydiary ";
        String sql = "create table if not exists mydiary " +
                "(day text primary key, weather integer, special text, contents text, picture text, sday text)";
        try{
            //database.execSQL(sql1);
            database.execSQL(sql);
            Log.d(TAG, "테이블 만듬: " + "mydiary");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insertData() {
        createDatabase();
        createTable();
        String sql = "insert into mydiary " +
                "(day, weather, special, contents, picture, sday) values " +
                "('" + tvDay.getText().toString() + "',"
                + weather + ",'"
                + etSpecial.getText().toString() + "','"
                + etContents.getText().toString() + "','"
                + filePath1 + "','"
                + iSday + "')";
        try{
            database.execSQL(sql);
            database.close();
            Toast.makeText(getApplicationContext(),
                    "입력이 되었습니다.", Toast.LENGTH_SHORT).show();
            this.finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getPicture(View v){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Uri setphotoUri = data.getData();
                showCapturedImage(setphotoUri);
            }
        }
    }

    private void showCapturedImage(Uri imageUri){
        filePath1 = getRealPathFromURI(imageUri);
        ShowCapturedImage showCapturedImage =
                new ShowCapturedImage(filePath1, imageView1);
        showCapturedImage.showImage();
    }

    private String getRealPathFromURI(Uri contentUri){
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri,proj,null,null,null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }
}
