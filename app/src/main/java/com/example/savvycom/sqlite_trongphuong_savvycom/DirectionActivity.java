package com.example.savvycom.sqlite_trongphuong_savvycom;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DirectionActivity extends AppCompatActivity {
    Button mExternalStorage;
    Button mSQLite;
    private static final String TAG = "Direction_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        initView();
        onClickEvent();
    }

    private void onClickEvent() {
        mExternalStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DirectionActivity.this, ExternalStorageActivity.class));
            }
        });

        mSQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DirectionActivity.this, SQLiteActivity.class));
            }
        });
    }

    private void initView() {
        mExternalStorage = findViewById(R.id.btn_external_storage);
        mSQLite = findViewById(R.id.btn_sqlite);
    }
}
