package com.eattendances.ditms_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Calendar;

public class monthview extends AppCompatActivity {
    private AdView mAdView;
    ListView listView;
    int pre,tot,abs;
    int y1,y2;
    float percen,percent;
    TextView p,t,prcnt,a,n;
    DatabaseReference datamonth;
    int[]mo={6,7,8,9,10,11,12,1,2,3,4,5};
    int[]ye={0,0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthview);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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







        p = findViewById(R.id.textView20);
        t = findViewById(R.id.textView28);
        prcnt = findViewById(R.id.textView36);
        a = findViewById(R.id.textView30);
        n = findViewById(R.id.textView29);

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int cyear = calendar.get(Calendar.YEAR);
        month = month + 1;
        if(month < 6){
            ye[1]=(cyear-2000);
            ye[0]=((cyear-1)-2000);
        }
        else{
            ye[1]=((cyear+1)-2000);
            ye[0]=(cyear-2000);
        }

        Toast.makeText(this, "Academic Year = 20" + ye[0] +"-"+ye[1], Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        String clas = intent.getStringExtra("clas");
        String roll = intent.getStringExtra("roll");
        String name = intent.getStringExtra("name");


        String name1 = "Student Name :- " + name+"\n                Class :- "+clas;
        n.setText(name1);


        for (int k = 0; k < 2; k++) {
            int year = ye[k];
            //Toast.makeText(this, ""+year, Toast.LENGTH_SHORT).show();
            for (int j = 0; j < 12; j++) {
                //int year= ye[k];

                for (int i = 1; i <= 31; i++) {
                    String yy = String.valueOf(year);
                    String ii = String.valueOf(i);
                    String jj = String.valueOf(mo[j]);
                    String pa = "students/" + clas + "/" + roll + "/" + jj + "/" + ii + "/" + yy;
                    datamonth = FirebaseDatabase.getInstance().getReference(pa);
                    datamonth.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int c = (int) snapshot.getChildrenCount();
                            tot = tot + c;
                            for (DataSnapshot studentsnapshot : snapshot.getChildren()) {
                                Student student = studentsnapshot.getValue(Student.class);
                                String sts = student.getStatus();

                                if (sts.equals("Present")) {
                                    pre = pre + 1;
                                } else {
                                    abs = abs + 1;
                                }
                            }

                            percen = pre * 100;
                            percent = percen / tot;
                            String pre1 = String.valueOf(pre);
                            String abs1 = String.valueOf(abs);
                            String tot1 = String.valueOf(tot);
                            String prcnt1 = String.valueOf(percent);
                            p.setText(pre1);
                            a.setText(abs1);
                            t.setText(tot1);
                            prcnt.setText(prcnt1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        }
    }





    @Override
    protected void onStart() {
        super.onStart();

    }
}