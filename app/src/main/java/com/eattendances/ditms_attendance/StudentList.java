package com.eattendances.ditms_attendance;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class StudentList extends ArrayAdapter<Student> {
    private Activity context;
    private List<Student> studentList;
    public StudentList(Activity context, List<Student> studentList){
        super(context,R.layout.list_layout, studentList);
        this.context=context;
        this.studentList=studentList;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.list_layout,null,true);

        TextView textViewname= (TextView) listViewItem.findViewById(R.id.textView25);
        TextView textViewroll= (TextView) listViewItem.findViewById(R.id.textView24);
        TextView textViewstatus= (TextView) listViewItem.findViewById(R.id.textView26);

        Student student=studentList.get(position);

        textViewroll.setText(student.getStudentName());
        textViewname.setText(student.getStudentRoll());
        textViewstatus.setText(student.getStudentStatus());


        return listViewItem;


    }
}
