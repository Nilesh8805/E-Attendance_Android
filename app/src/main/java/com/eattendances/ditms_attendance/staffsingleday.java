package com.eattendances.ditms_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class staffsingleday extends AppCompatActivity {
    private AdView mAdView;
    EditText e1;
    ListView listViewStudents;
    List<Student> studentListlist;
    DatabaseReference databasestudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staffsingleday);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);





        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError);
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });








        e1=findViewById(R.id.editTextTextPersonName2);
        listViewStudents=findViewById(R.id.staffsingleList);
        studentListlist= new ArrayList<>();

        Calendar calendar=Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        e1.setText(currentDate);

        Intent intent=getIntent();
        String ag=intent.getStringExtra("cc");
        Toast.makeText(this, ""+ag, Toast.LENGTH_SHORT).show();
        databasestudent= FirebaseDatabase.getInstance().getReference("students").child(ag);

        listViewStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Student student=studentListlist.get(i);
                String rolll =student.getStudentName();
                String name = student.getStudentRoll();
                String date=e1.getText().toString();

                if(date.isEmpty()){
                    Toast.makeText(staffsingleday.this, "Enter date", Toast.LENGTH_SHORT).show();
                }
                else{
                Intent intent1=new Intent(staffsingleday.this,browse1.class);
                intent1.putExtra("roll",rolll);
                intent1.putExtra("name",name);
                intent1.putExtra("class",ag);
                intent1.putExtra("date",date);
                startActivity(intent1);
                }
            }
        });


    }

    @Override
    protected void onStart() {
        databasestudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int c= (int) snapshot.getChildrenCount();
                if(c==0){
                    AlertDialog.Builder altd= new AlertDialog.Builder(staffsingleday.this);
                    altd.setTitle("Alert!");
                    altd.setMessage("This Class is Empty ");
                    altd.setCancelable(true);
                    altd.setIcon(R.drawable.alert);
                    altd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog d=altd.create();
                    d.show();
                }

                studentListlist.clear();

                for(DataSnapshot studentsnapshot : snapshot.getChildren()){
                    //DatabaseReference dj=studentsnapshot.getRef();
                    //dj.child("studentStatus").setValue("Click");
                    Student student=studentsnapshot.getValue(Student.class);
                    studentListlist.add(student);
                }

                StudentList adapter=new StudentList(staffsingleday.this, studentListlist);
                listViewStudents.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        super.onStart();
    }
}