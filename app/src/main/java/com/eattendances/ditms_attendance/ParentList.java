package com.eattendances.ditms_attendance;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ParentList extends ArrayAdapter<Student> {

    private Activity context;
    private List<Student> parentList;

    public ParentList(Activity context, List<Student> parentList){
        super(context,R.layout.list_layout,parentList);
        this.context=context;
        this.parentList=parentList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        View listvitem= inflater.inflate(R.layout.list_layout,null,true);

        TextView textViewlecture=(TextView) listvitem.findViewById(R.id.textView25);
        TextView textViewstatus=(TextView) listvitem.findViewById(R.id.textView26);

        Student student= parentList.get(position);

        textViewlecture.setText(student.getLecture());
        textViewstatus.setText(student.getStatus());

        return listvitem;
    }
}
