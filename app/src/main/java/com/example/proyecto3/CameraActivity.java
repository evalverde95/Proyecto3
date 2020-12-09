package com.example.proyecto3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;
import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class CameraActivity extends AppCompatActivity {
    private static final int IMAGE_CAPTURE_CODE = 1001;
    Button other_img;
    Button save_img;
    ImageView image;
    Uri image_uri;
    private static final int PERMISSION_CODE = 1000;
    String currentImagePath = null;
    ContentValues values = null;
    Intent cameraIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        image = findViewById(R.id.show_image);
        other_img = findViewById(R.id.other_image);
        save_img = findViewById(R.id.save_image);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ){
                //No permission
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission,PERMISSION_CODE);
            }
            else{
                // Permission granted
                openCam();
            }
        }
        else{
            // System OS < Marsh
            openCam();
        }
        save_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CameraActivity.this, MainActivity.class));
            }
        });

        other_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentResolver().delete(image_uri,null,null);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                            || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ){
                        //No permission
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,PERMISSION_CODE);
                    }
                    else{
                        // Permission granted
                        openCam();
                    }
                }
                else{
                    // System OS < Marsh
                    openCam();
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch(requestCode){
            case PERMISSION_CODE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCam();
                }
            }
        }
    }

    private void openCam(){
        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        image_uri = getContentResolver(). insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        // Camera intent
        cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            image.setImageURI(image_uri);
        }
    }



}