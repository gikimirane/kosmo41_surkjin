package com.study.android.project3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

public class SingerItemImageView extends LinearLayout {

    TextView textView1;
    ImageView imageView1;

    public SingerItemImageView(Context context) {
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item_image_view, this, true);

        textView1 = findViewById(R.id.textView1);
        imageView1 = findViewById(R.id.imageView1);
    }

    public void setDay(String day){
        textView1.setText(day);
    }

    public void setImage(String filepath){
        Log.d("lecture",  filepath);
        if(filepath.equals("null")) return;
        ShowCapturedImage showCapturedImage =
                new ShowCapturedImage(filepath, imageView1);
        showCapturedImage.showImage();
    }
}
