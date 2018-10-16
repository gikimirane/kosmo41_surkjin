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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class UpdateActivity extends AppCompatActivity {
    private  static final  String TAG = "lecture";
    TextView tvDay, etSpecial, etContents;
    ImageButton imageButton1;
    SQLiteDatabase database;
    String filePath, sDay;
    LinearLayout lineLayout1;
    ImageView imageView1;
    Dialog weatherDialog;
    int weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        Intent intent = getIntent();
        sDay = intent.getStringExtra("day");
        tvDay = findViewById(R.id.tvDay);
        imageButton1 = findViewById(R.id.imageButton1);
        etSpecial = findViewById(R.id.etSpecial);
        etContents = findViewById(R.id.etContents);
        imageView1 = findViewById(R.id.imageView1);
        createDatabase();
        selectData(sDay);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDialog = new Dialog(UpdateActivity.this);
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
                saveData();
            }
        });

        Button btnDel = findViewById(R.id.btnDel);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delData();
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPicture(v);
            }
        });

        imageView1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imageView1.setImageResource(R.drawable.ic_menu_gallery);
                filePath = null;
                return false;
            }
        });
    }

    public void iBtnclicked(int i){
        int[] w_id = {R.drawable.clear, R.drawable.partly, R.drawable.cloudy,
                R.drawable.rain, R.drawable.thunder, R.drawable.snow};
        imageButton1.setImageResource(w_id[weather=i]);
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

    private void selectData(String sDay){
        int[] w_id = {R.drawable.clear, R.drawable.partly, R.drawable.cloudy,
                R.drawable.rain, R.drawable.thunder, R.drawable.snow};

        String sql = "select day, weather, special, contents, picture  from mydiary " +
                     "where day = '"+sDay+"'";
        try{
            Cursor cursor = database.rawQuery(sql, null);

            int count = cursor.getCount();

            if(count == 1){
                cursor.moveToNext();
                String day = cursor.getString(0);
                weather = cursor.getInt(1);
                String special = cursor.getString(2);
                String contents = cursor.getString(3);
                filePath = cursor.getString(4);

                Log.d(TAG, "# " + day + " : " + day + " : " + contents);

                tvDay.setText(day);
                imageButton1.setImageResource(w_id[weather]);
                etSpecial.setText(special);
                etContents.setText(contents);
                ShowCapturedImage showCapturedImage =
                        new ShowCapturedImage(filePath, imageView1);
                showCapturedImage.showImage();
                cursor.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveData() {

        String sql = "update mydiary set " +
                "weather = " + weather + "," +
                "special = '" + etSpecial.getText().toString() + "'," +
                "contents = '" + etContents.getText().toString() + "'," +
                "picture = '" + filePath + "' " +
                "where day = '" + tvDay.getText().toString() + "'";
        try{
            database.execSQL(sql);
            Toast.makeText(getApplicationContext(),
                    "수정이 되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            setResult(10, intent);
            this.finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delData() {
        tvDay = findViewById(R.id.tvDay);

        String sql = "delete from mydiary " +
                     "where day = '" + tvDay.getText().toString() + "'";
        try{
            database.execSQL(sql);
            Toast.makeText(getApplicationContext(),
                    "삭제가 되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            setResult(10, intent);
            this.finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getPicture(View v){
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
        filePath = getRealPathFromURI(imageUri);
        ShowCapturedImage showCapturedImage =
                new ShowCapturedImage(filePath, imageView1);
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
