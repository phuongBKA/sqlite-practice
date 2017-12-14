package com.example.savvycom.sqlite_trongphuong_savvycom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.savvycom.sqlite_trongphuong_savvycom.adapter.CustomAdapter;
import com.example.savvycom.sqlite_trongphuong_savvycom.data.DBManager;
import com.example.savvycom.sqlite_trongphuong_savvycom.model.Student;

import java.util.List;

public class SQLiteActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtAddress;
    private EditText edtEmail;
    private EditText edtPhoneNumber;
    private EditText edtId;
    private Button btnUpdate;
    private Button btnSave;
    private ListView lvStudent;
    private CustomAdapter customAdapter;
    private List<Student> studentList;
    private DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DBManager(this);
        initWidget();
        studentList = dbManager.getAllStudent();

        onClickEvent();


    }

    private void onClickEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = createStudent();
                if (student != null) {
                    dbManager.addStudent(student);
                }
                updateListStudent();
                setAdapter();
            }
        });

        lvStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Student student = studentList.get(position);
                edtId.setText(String.valueOf(student.getmID()));
                edtName.setText(student.getmName());
                edtAddress.setText(student.getmAddress());
                edtEmail.setText(student.getmEmail());
                edtPhoneNumber.setText(student.getmPhoneNumber());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = new Student();
                student.setmID(Integer.parseInt(String.valueOf(edtId.getText())));
                student.setmName(edtName.getText() + "");
                student.setmAddress(edtAddress.getText() + "");
                student.setmEmail(edtEmail.getText() + "");
                student.setmPhoneNumber(edtPhoneNumber.getText() + "");
                int result = dbManager.updateStudent(student);
                if (result > 0) {
                    updateListStudent();
                } else {
                }
            }
        });

        lvStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Student student = studentList.get(position);
                int result = dbManager.deleteStudent(student.getmID());
                if (result > 0) {
                    Toast.makeText(SQLiteActivity.this, "Delete Successfully", Toast.LENGTH_SHORT).show();
                    updateListStudent();
                } else {
                    Toast.makeText(SQLiteActivity.this, "Delete fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateListStudent() {
        studentList.clear();
        studentList.addAll(dbManager.getAllStudent());
        if (customAdapter != null) {
            customAdapter.notifyDataSetChanged();
        }
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
        edtId = findViewById(R.id.edt_id);
        btnUpdate = findViewById(R.id.btn_update);
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
