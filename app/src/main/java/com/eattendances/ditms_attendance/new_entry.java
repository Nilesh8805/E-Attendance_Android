package com.eattendances.ditms_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

public class new_entry extends AppCompatActivity {
    private AdView mAdView;
    EditText sname,sroll;
    Button b;
    Spinner cls;
    ListView listalreadyexist;
    List<Student> studentListlist;
    DatabaseReference databasestudent;
    DatabaseReference deletdatass,databasestudentexist,setstatus1,setstatus2,setstatus3,statusclass1,statusclass2,statusclass3;
    int c,i,count1,count2,count3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new_entry);
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








        studentListlist= new ArrayList<>();
        sname=findViewById(R.id.editTextTextPersonName12);
        sroll=findViewById(R.id.editTextTextPersonName14);
        cls=findViewById(R.id.newenteryclass);
        b=findViewById(R.id.button11);
        listalreadyexist=findViewById(R.id.listofalreadyexist);
        Intent q=getIntent();
        String d=q.getStringExtra("cccc");   //cls.getSelectedItem().toString();


        databasestudent= FirebaseDatabase.getInstance().getReference("students").child(d);
        databasestudentexist= FirebaseDatabase.getInstance().getReference("students").child(d);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkConnect()) {
                    addStudent();
                    sname.setText("");
                } else {
                    AlertDialog.Builder altd = new AlertDialog.Builder(new_entry.this);
                    altd.setTitle("No Network !");
                    altd.setMessage("Make sure you are in network place ");
                    altd.setCancelable(true);
                    altd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog d = altd.create();
                    d.show();
                }
            }
        });
    }



    private boolean isNetworkConnect(){
        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return  cm.getActiveNetworkInfo() !=null;
    }

    private void addStudent(){
        String name=sname.getText().toString();
        String roll=sroll.getText().toString();
        String clss=cls.getSelectedItem().toString();
        String status="Click";
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(roll) && name.length()>2){
            String id=databasestudent.push().getKey();
            Student student=new Student(id,roll,name,clss,status);
            databasestudent.child(roll).setValue(student);
            Toast.makeText(this, "Student Registered Successfully", Toast.LENGTH_SHORT).show();
            //
            databasestudent.child(roll).child("yearinfo").child("datas").setValue("1");
            databasestudent.child(roll).child("yearinfo").child("finals").setValue("1");
        }
        else{
            Toast.makeText(this, "Enter fields", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        databasestudentexist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                c= (int) snapshot.getChildrenCount();

                sroll.setText(""+(c+1));
                if(c==0){
                    AlertDialog.Builder altd= new AlertDialog.Builder(new_entry.this);
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
                    //dj.child("studentStatus").setValue("Click")
                    Student student=studentsnapshot.getValue(Student.class);
                    studentListlist.add(student);
                }

                StudentList adapter=new StudentList(new_entry.this, studentListlist);
                listalreadyexist.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



            statusclass1 = FirebaseDatabase.getInstance().getReference("students").child("BCS FY");
            statusclass1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    count1 = (int) snapshot.getChildrenCount();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            statusclass2 = FirebaseDatabase.getInstance().getReference("students").child("BCS SY");
            statusclass2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    count2 = (int) snapshot.getChildrenCount();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            statusclass3 = FirebaseDatabase.getInstance().getReference("students").child("BCS TY");
            statusclass3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    count3 = (int) snapshot.getChildrenCount();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            for(i=1;i<=count1;i++) {
                setstatus1 = FirebaseDatabase.getInstance().getReference("students").child("BCS FY").child(String.valueOf(i)).child("studentStatus");
                setstatus1.setValue("");
            }
            for(i=1;i<=count2;i++) {
                setstatus2 = FirebaseDatabase.getInstance().getReference("students").child("BCS SY").child(String.valueOf(i)).child("studentStatus");
                setstatus2.setValue("");
            }
            for(i=1;i<=count3;i++){
                setstatus3 = FirebaseDatabase.getInstance().getReference("students").child("BCS TY").child(String.valueOf(i)).child("studentStatus");
                setstatus3.setValue("");
            }

    }
}