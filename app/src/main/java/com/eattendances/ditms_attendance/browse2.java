package com.eattendances.ditms_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.List;

public class browse2 extends AppCompatActivity {
    private AdView mAdView;
    ListView listView;
    List<Student> daylist;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse2);

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







        listView=findViewById(R.id.listbrowse2);
        daylist =new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("day");

        Intent intent=getIntent();
        String roll=intent.getStringExtra("roll");
        String name=intent.getStringExtra("name");
        String clas=intent.getStringExtra("class");
        String year=intent.getStringExtra("year");
        String year2=intent.getStringExtra("year2");
        String month=intent.getStringExtra("month");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent ii=new Intent(browse2.this,staffsinglefinal.class);
                int o=i+1;
                String days= String.valueOf(o);

                ii.putExtra("day",days);
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
                daylist.clear();
                for(DataSnapshot studentsnapshot : snapshot.getChildren()){
                    Student student=studentsnapshot.getValue(Student.class);
                    daylist.add(student);
                }
                //ParentList adapter=new ParentList(browse2.this, daylist);
                StudentList adapter=new StudentList(browse2.this, daylist);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}