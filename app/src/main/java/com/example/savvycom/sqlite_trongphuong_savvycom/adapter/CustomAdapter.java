package com.example.savvycom.sqlite_trongphuong_savvycom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.savvycom.sqlite_trongphuong_savvycom.R;
import com.example.savvycom.sqlite_trongphuong_savvycom.model.Student;

import java.util.List;

/**
 * Created by savvycom on 12/11/2017.
 */

public class CustomAdapter extends ArrayAdapter<Student> {

    private Context context;
    private int resource;
    private List<Student> listStudent;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listStudent = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.item_listview_student, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvID = convertView.findViewById(R.id.tv_id);
            viewHolder.tvName = convertView.findViewById(R.id.tv_name);
            viewHolder.tvPhoneNumber = convertView.findViewById(R.id.tv_phone_number);
            viewHolder.tvAddress = convertView.findViewById(R.id.tv_address);
            viewHolder.tvEmail = convertView.findViewById(R.id.tv_email);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Student student = listStudent.get(position);
        viewHolder.tvID.setText(String.valueOf(student.getmID()));
        viewHolder.tvName.setText(student.getmName());
        viewHolder.tvAddress.setText(student.getmAddress());
        viewHolder.tvPhoneNumber.setText(student.getmPhoneNumber());
        viewHolder.tvEmail.setText(student.getmEmail());

        return convertView;
    }

    public class ViewHolder {
        TextView tvID, tvName, tvAddress, tvPhoneNumber, tvEmail;
    }
}
