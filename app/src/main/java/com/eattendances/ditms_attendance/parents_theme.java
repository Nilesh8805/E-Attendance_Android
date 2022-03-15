package com.eattendances.ditms_attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.util.Calendar;

public class parents_theme extends AppCompatActivity {

    private InterstitialAd mInterstitialAd, mInterstitialAd2;

    private AdView mAdView;
    AutoCompleteTextView clas;
    EditText roll, date;
    Button getinfo, bdate;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_parents_theme);

        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



/*

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest1);
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
                mAdView.loadAd(adRequest1);
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




        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-8585966944422416/8310128180",adRequest,
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

        InterstitialAd.load(this,"ca-app-pub-8585966944422416/8197274086", adRequest2,
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





*/



        bdate = findViewById(R.id.brwsdate2);
        roll = findViewById(R.id.rollll);
        clas = findViewById(R.id.parentclass);
        date = findViewById(R.id.dateeee);
        getinfo = findViewById(R.id.infooo);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());
        date.setText(currentDate);

        String stdname[] = getResources().getStringArray(R.array.class_Name);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stdname);
        clas.setAdapter(adp);
        clas.setThreshold(0);


        getinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mInterstitialAd2 != null) {
                    mInterstitialAd2.show(parents_theme.this);

                    mInterstitialAd2.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            {
                                String r = roll.getText().toString();
                                String c = clas.getText().toString();
                                String d = date.getText().toString();

                                if (r.isEmpty() == false && c.isEmpty() == false && d.isEmpty() == false) {


                                    Intent in = new Intent(parents_theme.this, parentfinal.class);
                                    in.putExtra("pdate", d);
                                    in.putExtra("pclass", c);
                                    in.putExtra("proll", r);
                                    startActivity(in);
                                } else {
                                    Toast.makeText(parents_theme.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }

                else {

                    String r = roll.getText().toString();
                    String c = clas.getText().toString();
                    String d = date.getText().toString();

                    if (r.isEmpty() == false && c.isEmpty() == false && d.isEmpty() == false) {


                        Intent in = new Intent(parents_theme.this, parentfinal.class);
                        in.putExtra("pdate", d);
                        in.putExtra("pclass", c);
                        in.putExtra("proll", r);
                        startActivity(in);
                    } else {
                        Toast.makeText(parents_theme.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String r = roll.getText().toString();
                String c = clas.getText().toString();
                String name = "Your Child";
                if (r.isEmpty() == false && c.isEmpty() == false) {
                    Intent in = new Intent(parents_theme.this, browse1.class);
                    in.putExtra("class", c);
                    in.putExtra("roll", r);
                    in.putExtra("name", name);
                    startActivity(in);
                } else {
                    Toast.makeText(parents_theme.this, "Enter Roll No. & Class Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void yinfoparants(View view) {

/*
        if (mInterstitialAd != null) {
            mInterstitialAd.show(parents_theme.this);

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    {
                        String r = roll.getText().toString();
                        String c = clas.getText().toString();
                        String name = "Roll No. :- "+r;
                        if (r.isEmpty() == false && c.isEmpty() == false) {
                            Intent ii = new Intent(parents_theme.this, monthview.class);
                            ii.putExtra("clas", c);
                            ii.putExtra("roll", r);
                            ii.putExtra("name", name);
                            startActivity(ii);

                        } else {
                            Toast.makeText(parents_theme.this, "Enter Roll No. & Class Fields", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        }


 */
       // else {

            String r = roll.getText().toString();
            String c = clas.getText().toString();
            String name = "Roll No. :- "+r;
            if (r.isEmpty() == false && c.isEmpty() == false) {
                Intent ii = new Intent(parents_theme.this, monthview.class);
                ii.putExtra("clas", c);
                ii.putExtra("roll", r);
                ii.putExtra("name", name);
                startActivity(ii);

            } else {
                Toast.makeText(parents_theme.this, "Enter Roll No. & Class Fields", Toast.LENGTH_SHORT).show();
            }

     //   }




    }
}