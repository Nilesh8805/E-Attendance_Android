package com.eattendances.ditms_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
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

public class staffsinglefinal extends AppCompatActivity {
    private AdView mAdView;
    ListView listView;
    List<Student> parentlist;
    DatabaseReference databaseReferenceName,databaseReferenceStatus;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staffsinglefinal);
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








        listView=findViewById(R.id.lvstaffsingle);
        textView=findViewById(R.id.textViewone);

        parentlist =new ArrayList<>();

        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        String clas=intent.getStringExtra("class");
        String rol=intent.getStringExtra("roll");

        String day=intent.getStringExtra("day");
        String month=intent.getStringExtra("month");
        String year=intent.getStringExtra("year");
        int y= Integer.parseInt(year);
        int m= Integer.parseInt(month);
        y=y-2000;
        if(m<7){
            y=y+1;
        }
        String year1= String.valueOf(y);
        String date=month+"/"+day+"/"+year1;
        String add= name+"'s Atandance";
        textView.setText(add);

        databaseReferenceName= FirebaseDatabase.getInstance().getReference("students").child(clas).child(rol);
        databaseReferenceStatus= FirebaseDatabase.getInstance().getReference("students").child(clas).child(rol).child(date);

        Toast.makeText(this, "MM/DD/YY :- "+date, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Roll No. :- "+rol, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        databaseReferenceStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int c= (int) snapshot.getChildrenCount();
                if(c==0){
                    AlertDialog.Builder altd= new AlertDialog.Builder(staffsinglefinal.this);
                    altd.setTitle("Alert!");
                    altd.setMessage("No Data found for this Day ");
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

                parentlist.clear();
                for(DataSnapshot studentsnapshot : snapshot.getChildren()){
                    Student student=studentsnapshot.getValue(Student.class);
                    parentlist.add(student);
                }
                ParentList adapter=new ParentList(staffsinglefinal.this, parentlist);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        super.onStart();
    }
}