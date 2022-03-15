package com.eattendances.ditms_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

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
import java.util.Calendar;
import java.util.List;

public class browse1 extends AppCompatActivity {
    private AdView mAdView;
    ListView listView;
    EditText e1,e2;

    List<Student> monthlist;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse1);
        listView=findViewById(R.id.listbrowse1);
        e1=findViewById(R.id.eyear);
        e2=findViewById(R.id.eyear2);

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







        Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH);
        month=month+1;
        int year=calendar.get(Calendar.YEAR);
        //Toast.makeText(this, ""+month, Toast.LENGTH_SHORT).show();

        if(month>=6){
            e1.setText(""+year);
            e2.setText(""+(year+1));
        }
        else{
            e2.setText(""+year);
            e1.setText(""+(year-1));
        }



        monthlist =new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("month");



        Intent intent=getIntent();
        String roll=intent.getStringExtra("roll");
        String name=intent.getStringExtra("name");
        String clas=intent.getStringExtra("class");




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent ii=new Intent(browse1.this,browse2.class);
                int o=i+1;
                String month= String.valueOf(o);
                String year= e1.getText().toString();
                String year2= e1.getText().toString();

                ii.putExtra("year",year);
                ii.putExtra("year2",year2);
                ii.putExtra("month",month);
                ii.putExtra("roll",roll);
                ii.putExtra("class",clas);
                ii.putExtra("name",name);

                startActivity(ii);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                monthlist.clear();
                for(DataSnapshot studentsnapshot : snapshot.getChildren()){
                    Student student=studentsnapshot.getValue(Student.class);
                    monthlist.add(student);
                }
                //ParentList adapter=new ParentList(browse1.this, monthlist);
                StudentList adapter=new StudentList(browse1.this, monthlist);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
