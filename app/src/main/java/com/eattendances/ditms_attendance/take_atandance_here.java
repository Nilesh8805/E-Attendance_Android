package com.eattendances.ditms_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class take_atandance_here extends AppCompatActivity {
    private InterstitialAd mInterstitialAd, mInterstitialAd2;
    private AdView mAdView;
    ListView listViewStudents;
    //Spinner spinner;

    int r[]=new int[100];
    int gos[]=new int[100];
    int m=0;
    int samples;
    double fp;
    AutoCompleteTextView lect;
    EditText date,clsd;
    List<Student> studentListlist;
    DatabaseReference databasestudent,adddataatend,setdatas,yinfo;
    DataSnapshot ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_atandance_here);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);








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

        InterstitialAd.load(this,"ca-app-pub-8585966944422416/3294100060", adRequest1,
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













        listViewStudents = findViewById(R.id.listviewww);
        studentListlist = new ArrayList<>();
        //clsd=findViewById(R.id.clasentry);
        //spinner=findViewById(R.id.clsname);
        date = findViewById(R.id.editTextTextPersonName3);
        lect = findViewById(R.id.atandlecture);
        String stdname[] = getResources().getStringArray(R.array.lecture);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stdname);
        lect.setAdapter(adp);
        lect.setThreshold(0);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());
        date.setText(currentDate);

        Intent intent = getIntent();
        String ag = intent.getStringExtra("cc");
        String check = intent.getStringExtra("check");


        Toast.makeText(this, "" + ag, Toast.LENGTH_SHORT).show();
        databasestudent = FirebaseDatabase.getInstance().getReference("students").child(ag);


        listViewStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (isNetworkConnect())
                {
                        Student student = studentListlist.get(i);
                    String rolll = student.getStudentName();
                    String uid = student.getStudentId();
                    String temp = "present";


                    //Toast.makeText(take_atandance_here.this, ""+nam+""+date.getText().toString(), Toast.LENGTH_SHORT).show();
                    if (lect.getText().toString().isEmpty() == false && date.getText().toString().isEmpty() == false) {

                        String p = "Present";
                        String a = "A";
                        String le = lect.getText().toString();
                        if (r[i] == 0) {

                            adddataatend = FirebaseDatabase.getInstance().getReference("students").child(ag).child(rolll).child(date.getText().toString()).child(lect.getText().toString());
                            Student student1 = new Student(le, p);
                            adddataatend.setValue(student1);

                            setdatas = FirebaseDatabase.getInstance().getReference("students").child(ag).child(rolll).child("studentStatus");
                            setdatas.setValue("Present");


                            r[i] = 1;

                        } else if (r[i] == 1) {
                            adddataatend = FirebaseDatabase.getInstance().getReference("students").child(ag).child(rolll).child(date.getText().toString()).child(lect.getText().toString());
                            Student student1 = new Student(le, a);
                            adddataatend.setValue(student1);


                            setdatas = FirebaseDatabase.getInstance().getReference("students").child(ag).child(rolll).child("studentStatus");
                            setdatas.setValue("A");
                            r[i] = 0;
                        }

                    } else {
                        Toast.makeText(take_atandance_here.this, "Enter Valit Date and Lecture", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    AlertDialog.Builder altd= new AlertDialog.Builder(take_atandance_here.this);
                    altd.setTitle("No Network !");
                    altd.setMessage("Make sure you are in network place ");
                    altd.setCancelable(true);
                    altd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog d=altd.create();
                    d.show();
                }
            }
        });

    }



    private boolean isNetworkConnect(){
        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return  cm.getActiveNetworkInfo() !=null;
    }


    @Override
    protected void onStart() {
        databasestudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int c = (int) snapshot.getChildrenCount();
                if (c == 0) {
                    AlertDialog.Builder altd = new AlertDialog.Builder(take_atandance_here.this);
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
                    AlertDialog d = altd.create();
                    d.show();
                }

                studentListlist.clear();
                for (DataSnapshot studentsnapshot : snapshot.getChildren()) {
                    Student student = studentsnapshot.getValue(Student.class);
                    studentListlist.add(student);

                }

                StudentList adapter = new StudentList(take_atandance_here.this, studentListlist);
                listViewStudents.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mInterstitialAd != null) {
            mInterstitialAd.show(take_atandance_here.this);

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