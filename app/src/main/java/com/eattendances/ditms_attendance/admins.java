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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

public class admins extends AppCompatActivity {

    private InterstitialAd mInterstitialAd, mInterstitialAd2;

    private AdView mAdView;
    Button ne,de;
    AutoCompleteTextView e;
    EditText adminn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_admins);
        ne=findViewById(R.id.button9);
        de=findViewById(R.id.button10);
        e=findViewById(R.id.adminclass);




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












        String stdname[]=getResources().getStringArray(R.array.class_Name);
        ArrayAdapter<String> adp=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,stdname);
        e.setAdapter(adp);
        e.setThreshold(0);

        adminn=findViewById(R.id.editTextTextPersonName6);

        ne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = e.getText().toString();
                String check = adminn.getText().toString();
                if (t.isEmpty() == false) {
                    if (check.equals("12345")) {
                        Intent n = new Intent(admins.this, new_entry.class);
                        n.putExtra("cccc", t);
                        startActivity(n);
                    } else {
                        Toast.makeText(admins.this, "Enter Valid Admin code", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(admins.this, "Enter class Name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check = adminn.getText().toString();
                if (check.equals("12345")) {
                    String t = e.getText().toString();
                    Intent n1 = new Intent(admins.this, delete_data.class);
                    n1.putExtra("cc",t);
                    startActivity(n1);
                } else {
                    Toast.makeText(admins.this, "Enter Valid Admin code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        adminn.setText("");
        super.onStart();








        if (mInterstitialAd != null) {
            mInterstitialAd.show(admins.this);

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    {

                    }
                }
            });
        }






    }
}