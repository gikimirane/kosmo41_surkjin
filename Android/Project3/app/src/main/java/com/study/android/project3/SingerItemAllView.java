package com.study.android.project3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

public class SingerItemAllView extends LinearLayout {

    TextView textView1, textView2, textView3;
    ImageView imageView1;
    ImageButton imageButton1;

    public SingerItemAllView(Context context) {
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item_all_view, this, true);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        imageButton1 = findViewById(R.id.imageButton1);
        imageView1 = findViewById(R.id.imageView1);
        imageButton1.setFocusable(false);
    }

    public void setDay(String day){
        textView1.setText(day);
    }

    public void setWeather(int weather) {
        int[] w_id = {R.drawable.clear, R.drawable.partly, R.drawable.cloudy,
                R.drawable.rain, R.drawable.thunder, R.drawable.snow};
        imageButton1.setImageResource(w_id[weather]);}

    public void setSpecial(String special) { textView2.setText(special);}

    public void setContents(String contents){ textView3.setText(contents); }

    public void setImage(String filepath){
        if(filepath.equals("null")){
            imageView1.setImageBitmap(null);
            return;
        }
        ShowCapturedImage showCapturedImage =
                new ShowCapturedImage(filepath, imageView1);
        showCapturedImage.showImage();
    }
}
