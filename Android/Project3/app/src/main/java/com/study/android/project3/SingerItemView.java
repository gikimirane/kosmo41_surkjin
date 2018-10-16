package com.study.android.project3;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingerItemView extends LinearLayout {

    TextView textView1;
    TextView textView2;
    ImageView imageView1;
    ImageButton ibtnWeather;

    public SingerItemView(Context context) {
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item_view, this, true);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        imageView1 = findViewById(R.id.imageView1);
        ibtnWeather = findViewById(R.id.ibtnWeather);
        ibtnWeather.setFocusable(false);
    }

    public void setDay(String day){
        textView1.setText(day);
    }

    public void setWeather(int weather){
        int[] w_id = {R.drawable.clear, R.drawable.partly, R.drawable.cloudy,
                R.drawable.rain, R.drawable.thunder, R.drawable.snow};
        ibtnWeather.setImageResource(w_id[weather]);
    }

    public void setContents(String contents){
        textView2.setText(contents.substring(0, contents.length()>20 ? 20:contents.length()));
    }

    public void setImage(int imgNum){
        imageView1.setImageResource(imgNum);
    }
}
