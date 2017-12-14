package com.example.savvycom.sqlite_trongphuong_savvycom;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ExternalStorageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSaveData, btnReadData;
    private TextView tvData;
    private final String fileName = "savvycom";
    private final String content = "content file test savvycom";
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);
        btnReadData = findViewById(R.id.btn_read_data);
        btnSaveData = findViewById(R.id.btn_save_data);
        tvData = findViewById(R.id.tv_data);
        btnSaveData.setOnClickListener(this);
        btnReadData.setOnClickListener(this);

        checkAndRequestPermissions();
    }

    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        List<String> listPermissionNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(permission);
            }
        }
        if (!listPermissionNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]), 1);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_read_data:
                readData();
                break;
            case R.id.btn_save_data:
                saveData();
                break;
            default:

                break;
        }
    }

    public void saveData() {
        if (isExternalStorageReadable()) {
            FileOutputStream fileOutputStream = null;
            File file;
            try {
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(content.getBytes());
                Toast.makeText(this, "Path: " + Environment.getExternalStorageDirectory().getAbsolutePath(), Toast.LENGTH_SHORT).show();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Can't save file", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void readData() {
        BufferedReader bf = null;
        File file = null;
        try {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
            bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = bf.readLine()) != null) {
                buffer.append(line);
            }
            tvData.setText(buffer.toString());
            Log.d(TAG, "readData: " + buffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
