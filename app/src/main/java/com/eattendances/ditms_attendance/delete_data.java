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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;

public class delete_data extends AppCompatActivity {
    private AdView mAdView;
EditText e1;
Button b;
    List<Student> studentListlist;
    ListView listViewStudents;
AutoCompleteTextView e2;
DatabaseReference deletdatass,databasestudent;


    @Override
    protected void onStart() {
        databasestudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int c= (int) snapshot.getChildrenCount();
                if(c==0){
                    AlertDialog.Builder altd= new AlertDialog.Builder(delete_data.this);
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

                StudentList adapter=new StudentList(delete_data.this, studentListlist);
                listViewStudents.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        super.onStart();
    }

    private boolean isNetworkConnect(){
        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return  cm.getActiveNetworkInfo() !=null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_delete_data);
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







        e1=findViewById(R.id.editTextTextPersonName4);
        e2=findViewById(R.id.deleteclass);
        b=findViewById(R.id.button4);
        studentListlist= new ArrayList<>();
        listViewStudents=findViewById(R.id.listdelete);
        String stdname[]=getResources().getStringArray(R.array.class_Name);


        ArrayAdapter<String> adp=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,stdname);
        e2.setAdapter(adp);
        e2.setThreshold(0);

        Intent intent=getIntent();
        String ag=intent.getStringExtra("cc");
        Toast.makeText(this, ""+ag, Toast.LENGTH_SHORT).show();
        e2.setText(ag);
        databasestudent= FirebaseDatabase.getInstance().getReference("students").child(ag);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkConnect()) {
                    String all = e1.getText().toString();
                    if (all.equals("0000")) {
                        deletdatass = FirebaseDatabase.getInstance().getReference("students").child(e2.getText().toString());
                        deletdatass.removeValue();
                    } else {
                        deletdatass = FirebaseDatabase.getInstance().getReference("students").child(e2.getText().toString()).child(e1.getText().toString());
                        deletdatass.removeValue();
                    }
                } else {
                    AlertDialog.Builder altd = new AlertDialog.Builder(delete_data.this);
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

        listViewStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isNetworkConnect()){
                    Student student = studentListlist.get(i);
                    String rolll = student.getStudentName();
                    Toast.makeText(delete_data.this, ""+i, Toast.LENGTH_SHORT).show();
                    deletdatass= FirebaseDatabase.getInstance().getReference("students").child(e2.getText().toString()).child(rolll);
                    deletdatass.removeValue();
                } else {
                    AlertDialog.Builder altd = new AlertDialog.Builder(delete_data.this);
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
}