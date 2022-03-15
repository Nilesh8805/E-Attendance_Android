package com.eattendances.ditms_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
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

public class parentfinal extends AppCompatActivity {
    private AdView mAdView;
    ListView listViewname,listViewstatus;
    List<Student> parentlist;
    TextView tv;
    String date,rol,clas;
    DatabaseReference databaseReferenceName,databaseReferenceStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_parentfinal);
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







        tv=findViewById(R.id.textView6);
        listViewstatus=findViewById(R.id.lvstaffsingle);

        parentlist =new ArrayList<>();


        Intent intent=getIntent();
        clas=intent.getStringExtra("pclass");
        rol=intent.getStringExtra("proll");
        date=intent.getStringExtra("pdate");

        databaseReferenceName= FirebaseDatabase.getInstance().getReference("students").child(clas).child(rol);

        databaseReferenceStatus= FirebaseDatabase.getInstance().getReference("students").child(clas).child(rol).child(date);

        Toast.makeText(this, ""+clas+rol+date, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {

            databaseReferenceStatus.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int c= (int) snapshot.getChildrenCount();
                    if(c==0){
                        AlertDialog.Builder altd= new AlertDialog.Builder(parentfinal.this);
                        altd.setTitle("Alert!");
                        altd.setMessage("No Data Found for this Day");
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
                    ParentList adapter=new ParentList(parentfinal.this, parentlist);
                    listViewstatus.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        super.onStart();
    }
}