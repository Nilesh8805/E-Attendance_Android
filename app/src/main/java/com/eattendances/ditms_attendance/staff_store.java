package com.eattendances.ditms_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class staff_store extends AppCompatActivity {
    private InterstitialAd mInterstitialAd, mInterstitialAd2;
    private AdView mAdView;
    Button at,fat,singleday;
    AutoCompleteTextView cl;
    int i,count1,count2,count3;
    DatabaseReference setstatus1,setstatus2,setstatus3,statusclass1,statusclass2,statusclass3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_store);
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





        AdRequest adRequest1 = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-8585966944422416/1958689861", adRequest1,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });


        AdRequest adRequest2 = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-8585966944422416/2280740608", adRequest2,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd2 = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd2 = null;
                    }
                });












        at=findViewById(R.id.button5);
        cl=findViewById(R.id.staffclass);
        singleday=findViewById(R.id.button6);
        fat=findViewById(R.id.button7);
        String stdname[]=getResources().getStringArray(R.array.class_Name);
        ArrayAdapter<String> adp=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,stdname);
        cl.setAdapter(adp);
        cl.setThreshold(0);

           //spinner.getSelectedItem().toString();

        fat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(staff_store.this);

                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            {
                                String te = cl.getText().toString();
                                if (!TextUtils.isEmpty(te) == false) {
                                    Toast.makeText(staff_store.this, "Enter Class", Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent inc = new Intent(staff_store.this, finalyearpercent.class);
                                    inc.putExtra("cc", te);
                                    startActivity(inc);
                                }

                            }
                        }
                    });
                } else {
                    String te = cl.getText().toString();
                    if (!TextUtils.isEmpty(te) == false) {
                        Toast.makeText(staff_store.this, "Enter Class", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent inc = new Intent(staff_store.this, finalyearpercent.class);
                        inc.putExtra("cc", te);
                        startActivity(inc);
                    }
                }
            }
        });

        singleday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd2 != null) {
                    mInterstitialAd2.show(staff_store.this);

                    mInterstitialAd2.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            {
                                String te=cl.getText().toString();
                                if(!TextUtils.isEmpty(te)==false){
                                    Toast.makeText(staff_store.this, "Enter Class", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Intent i = new Intent(staff_store.this, staffsingleday.class);
                                    i.putExtra("cc", te);
                                    startActivity(i);
                                }

                            }
                        }
                    });
                } else {
                    String te=cl.getText().toString();
                    if(!TextUtils.isEmpty(te)==false){
                        Toast.makeText(staff_store.this, "Enter Class", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent i = new Intent(staff_store.this, staffsingleday.class);
                        i.putExtra("cc", te);
                        startActivity(i);
                    }
                }
            }
        });

        at.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String te=cl.getText().toString();
                if(!TextUtils.isEmpty(te)==false){
                    Toast.makeText(staff_store.this, "Enter Class", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i = new Intent(staff_store.this, take_atandance_here.class);
                    i.putExtra("cc", te);
                    i.putExtra("check","no");
                    startActivity(i);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

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
            setstatus1.setValue("Click");
        }
        for(i=1;i<=count2;i++) {
            setstatus2 = FirebaseDatabase.getInstance().getReference("students").child("BCS SY").child(String.valueOf(i)).child("studentStatus");
            setstatus2.setValue("Click");
        }
        for(i=1;i<=count3;i++){
            setstatus3 = FirebaseDatabase.getInstance().getReference("students").child("BCS TY").child(String.valueOf(i)).child("studentStatus");
            setstatus3.setValue("Click");
        }
    }
}