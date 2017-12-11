package com.example.savvycom.sqlite_trongphuong_savvycom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.savvycom.sqlite_trongphuong_savvycom.adapter.CustomAdapter;
import com.example.savvycom.sqlite_trongphuong_savvycom.data.DBManager;
import com.example.savvycom.sqlite_trongphuong_savvycom.model.Student;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtAddress;
    private EditText edtEmail;
    private EditText edtPhoneNumber;
    private Button btnSave;
    private ListView lvStudent;
    private CustomAdapter customAdapter;
    private List<Student> studentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DBManager dbManager = new DBManager(this);
        initWidget();
        studentList = dbManager.getAllStudent();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = createStudent();
                if (student != null) {
                    dbManager.addStudent(student);
                }
                studentList.clear();
                studentList.addAll(dbManager.getAllStudent());
                setAdapter();
            }
        });
    }

    private Student createStudent() {
        String name = edtName.getText().toString();
        String address = String.valueOf(edtAddress.getText());
        String phoneNumber = edtPhoneNumber.getText() + "";
        String email = edtEmail.getText().toString();

        Student student = new Student(name, address, phoneNumber, email);
        return student;
    }

    private void initWidget() {
        edtAddress = findViewById(R.id.edt_address);
        edtName = findViewById(R.id.edt_name);
        edtPhoneNumber = findViewById(R.id.edt_number);
        edtEmail = findViewById(R.id.edt_email);
        btnSave = findViewById(R.id.btn_save);
        lvStudent = findViewById(R.id.lv_student);
    }

    private void setAdapter() {
        if (customAdapter == null) {
            customAdapter = new CustomAdapter(this, R.layout.item_listview_student, studentList);
            lvStudent.setAdapter(customAdapter);
        } else {
            customAdapter.notifyDataSetChanged();
            lvStudent.setSelection(customAdapter.getCount() - 1);
        }
    }
}
