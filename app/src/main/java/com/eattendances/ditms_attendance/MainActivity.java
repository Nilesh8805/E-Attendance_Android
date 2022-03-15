package com.eattendances.ditms_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

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
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd, mInterstitialAd2;

    private AdView mAdView;
    Button b1,b2,b12;
    DatabaseReference databaseReference,dset;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
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

        InterstitialAd.load(this,"ca-app-pub-8585966944422416/2717237766", adRequest1,
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













        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button2);
        //b12=findViewById(R.id.button12);
/*
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<32;i++) {

                    databaseReference = FirebaseDatabase.getInstance().getReference("day").child(String.valueOf(i)).child("studentName");
                    dset = FirebaseDatabase.getInstance().getReference("day").child(String.valueOf(i)).child("studentRoll");
                    databaseReference.setValue("Date");
                    dset.setValue(String.valueOf(i));
                }
            }
        });
*/

    }

    public void stff(View view) {
        Intent i=new Intent(MainActivity.this,staff_theme.class);
        startActivity(i);
    }

    public void prnt(View view) {
        Intent it=new Intent(MainActivity.this,parents_theme.class);
        startActivity(it);

    }

    public void adm(View view) {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(MainActivity.this);

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    {
                        Intent ad = new Intent(MainActivity.this, admins.class);
                        startActivity(ad);
                    }
                }
            });
        }
        else{
            Intent ad = new Intent(MainActivity.this, admins.class);
            startActivity(ad);
        }
    }
}