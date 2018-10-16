package com.study.android.project3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class ShowCapturedImage {

    ImageView imageView;
    String filePath;

    public ShowCapturedImage(String filePath, ImageView imageView){
        this.filePath = filePath;
        this.imageView = imageView;
    }

    public void showImage(){
        ExifInterface exif = null;
        try{
            exif = new ExifInterface(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        Bitmap rotatedBitmap = rotate(bitmap, exifDegree);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(rotatedBitmap,800,800,false);
        bitmap.recycle();

        imageView.setImageBitmap(scaledBitmap);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imageView.setImageResource(R.drawable.ic_menu_gallery);
                filePath = null;
                return false;
            }
        });
    }

    private int exifOrientationToDegrees(int exifOrientation){
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        } else if (exifOrientation==ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        } else if (exifOrientation==ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap src, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);

        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(),matrix,true);
    }
}
