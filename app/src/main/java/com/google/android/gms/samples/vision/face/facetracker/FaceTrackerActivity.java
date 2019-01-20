/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.vision.face.facetracker;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.samples.vision.face.facetracker.ui.camera.CameraSourcePreview;
import com.google.android.gms.samples.vision.face.facetracker.ui.camera.GraphicOverlay;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Activity for the face tracker app.  This app detects faces with the rear facing camera, and draws
 * overlay graphics to indicate the position, size, and ID of each face.
 */
public final class FaceTrackerActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT="com.google.android.gms.samples.vision.face.facetracker.EXTRA_TEXT";
   private static final String TAG="FaceTrackerActivity";
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionGranted = false;
    private static final String FINLOC = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COLOC = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQ_CODE = 12;
    String KEY_TEXTPSS = "TEXTPSS";
    String KEY_TEXTPSS2 = "TEXTPSS2";
    static final int CUSTOM_DIALOG_ID = 0;
    public static float hp_inFaceTracker = 0,tot=0;static public int tim;static public int index=0;

    private static Button b2,b3,b4;TextView dialog_TextView,dialog_TextView3;
    DatabaseHelper mDatabaseHelper;
    private static final String[] place = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", "101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113", "114", "115", "116", "117", "118", "119", "120", "121", "122", "123", "124", "125", "126", "127", "128", "129", "130", "131", "132", "133"};
    public static double[][] au_m = {
            {35.607107, 139.685200},
            {35.607248, 139.686033},
            {35.607474, 139.686753},
            {35.607666, 139.687335},
            {35.607690, 139.687485},
            {35.607803, 139.687936},
            {35.608013, 139.688494},
            {35.608327, 139.689513},
            {35.608383, 139.689833},
            {35.608460, 139.690225},
            {35.608767, 139.691198},
            {35.609031, 139.692104},
            {35.609207, 139.692814},
            {35.606632, 139.685248},
            {35.606511, 139.686178},
            {35.606563, 139.687091},
            {35.606667, 139.687511},
            {35.606733, 139.687761},
            {35.606856, 139.688255},
            {35.607010, 139.688885},
            {35.607223, 139.689748},
            {35.607220, 139.690451},
            {35.607647, 139.691465},
            {35.607795, 139.692071},
            {35.607996, 139.692913},
            {35.607262, 139.688157},
            {35.607328, 139.688711},
            {35.607888, 139.689603},
            {35.607910, 139.689914},
            {35.607888, 139.690320},
            {35.608064, 139.691374},
            {35.608317, 139.692069},
            {35.606248, 139.685201},
            {35.606132, 139.686134},
            {35.606038, 139.685190},
            {35.605968, 139.686129},
            {35.605883, 139.686641},
            {35.605682, 139.685171},
            {35.605700, 139.685763},
            {35.605175, 139.685167},
            {35.605341, 139.685816},
            {35.605389, 139.686066},
            {35.605544, 139.686618},
            {35.605714, 139.687427},
            {35.605851, 139.687953},
            {35.605963, 139.688507},
            {35.606109, 139.689213},
            {35.606263, 139.689962},
            {35.606461, 139.690915},
            {35.606544, 139.691324},
            {35.606678, 139.692047},
            {35.606834, 139.692820},
            {35.606399, 139.688376},
            {35.606410, 139.689107},
            {35.606506, 139.689905},
            {35.607274, 139.691775},
            {35.607404, 139.692056},
            {35.607092, 139.692062},
            {35.607212, 139.692834},
            {35.604548, 139.685053},
            {35.604542, 139.686010},
            {35.604561, 139.686586},
            {35.604661, 139.687439},
            {35.604683, 139.687801},
            {35.604902, 139.688655},
            {35.605232, 139.689531},
            {35.605550, 139.690301},
            {35.605732, 139.690768},
            {35.605744, 139.691724},
            {35.605525, 139.692742},
            {35.604964, 139.685142},
            {35.604927, 139.686034},
            {35.604949, 139.686516},
            {35.605178, 139.687307},
            {35.605239, 139.687607},
            {35.605657, 139.689381},
            {35.605905, 139.690118},
            {35.606288, 139.691909},
            {35.604026, 139.684948},
            {35.604020, 139.686047},
            {35.603932, 139.686724},
            {35.603942, 139.687157},
            {35.603973, 139.687815},
            {35.604293, 139.684998},
            {35.604267, 139.686022},
            {35.604210, 139.686672},
            {35.604254, 139.687133},
            {35.604966, 139.689564},
            {35.604931, 139.690598},
            {35.604909, 139.691588},
            {35.605404, 139.690712},
            {35.605417, 139.691667},
            {35.604211, 139.689062},
            {35.604314, 139.689639},
            {35.604336, 139.690496},
            {35.604407, 139.691524},
            {35.604443, 139.692181},
            {35.604469, 139.692819},
            {35.603444, 139.684974},
            {35.603474, 139.685484},
            {35.603505, 139.686085},
            {35.603509, 139.686267},
            {35.603431, 139.686782},
            {35.603413, 139.687147},
            {35.603439, 139.687823},
            {35.603470, 139.688504},
            {35.603653, 139.689174},
            {35.603989, 139.686296},
            {35.603535, 139.690489},
            {35.603692, 139.691411},
            {35.602942, 139.685092},
            {35.602973, 139.685468},
            {35.602995, 139.686047},
            {35.602262, 139.685237},
            {35.602323, 139.686052},
            {35.602465, 139.686913},
            {35.602469, 139.687117},
            {35.602532, 139.687823},
            {35.602620, 139.688673},
            {35.602844, 139.689271},
            {35.603182, 139.689955},
            {35.603006, 139.690451},
            {35.603376, 139.691548},
            {35.603581, 139.692331},
            {35.603738, 139.692932},
            {35.602932, 139.687259},
            {35.602974, 139.687825},
            {35.603065, 139.688604},
            {35.605801, 139.691374},
            {35.605973, 139.685816},
            {35.604593, 139.687083},
            {35.606330, 139.691620},
            {35.605080, 139.688518}};
    private String te2="-1";
    private static double[] dista={100,100,100,100,100,100,100,100,100,100,100,100};
    private static double[] err_pos_now=new double[dista.length];
    static private int Vvalue = 0;
    float[] tim1=new float[1000000];
    float[] fac1=new float[1000000];
    float [] fac2=new float[50];

    static public void setVvalue() {
        Vvalue = 1000000;
    }

    static public int getVvalue() {
        return Vvalue;
    }
    TextView tv1;TextView tv2,tv3,tv4,tv10,lat_a,long_a,tv11,tv12;

//    private static final String TAG = "FaceTracker";

    private CameraSource mCameraSource = null;

    private CameraSourcePreview mPreview;
    private GraphicOverlay mGraphicOverlay;

    private static final int RC_HANDLE_GMS = 9001;
    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteref = db.collection("Dat").document("Data1");

    //==============================================================================================
    // Activity Methods
    //==============================================================================================

    /**
     * Initializes the UI and initiates the creation of a face detector.
     */
    @Override
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);
        setContentView(R.layout.main);

        mPreview = (CameraSourcePreview) findViewById(R.id.preview);
        mGraphicOverlay = (GraphicOverlay) findViewById(R.id.faceOverlay);

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
        } else {
            requestCameraPermission();
        }

        tv1 = (TextView)findViewById(R.id.textView2);
        tv2 = (TextView)findViewById(R.id.textView4);
        tv3 = (TextView) findViewById(R.id.textView5);
        tv4 = (TextView) findViewById(R.id.textView6);
        tv10 = (TextView) findViewById(R.id.textView7);
        tv11 = (TextView) findViewById(R.id.textView11);
        tv12 = (TextView) findViewById(R.id.textView12);
        lat_a = (TextView) findViewById(R.id.lat_a);
        long_a = (TextView) findViewById(R.id.long_a);
        b2 = (Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button4);
        mDatabaseHelper = new DatabaseHelper(this);

        getLocationPermission();
        if (mLocationPermissionGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }


        noteref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Toast.makeText(FaceTrackerActivity.this, "loaded fine", Toast.LENGTH_SHORT).show();
                    String latA = documentSnapshot.getString("latitude");
                    String longA = documentSnapshot.getString("longitude");
                    tv11.setText("" + latA);
                    tv12.setText("" + longA);
                } else {
                    Toast.makeText(FaceTrackerActivity.this, "oops", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FaceTrackerActivity.this, "oops", Toast.LENGTH_SHORT).show();
            }
        });

        thread.start();




        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int minimumerrorindexnow=search_nearest(err_pos_now, lat_a, long_a);
                String newEntry=String.valueOf(minimumerrorindexnow);
                AddData(newEntry);
                String depart=place[minimumerrorindexnow];
                Toast.makeText(FaceTrackerActivity.this,"Thank you! your report near place index "+ depart +" has been considered.",Toast.LENGTH_SHORT).show();
                Cursor cursor=mDatabaseHelper.getData();
                while(cursor.moveToNext()) {
                    tv10.setText("" + cursor.getString(1));
                }
                String latAString = lat_a.getText().toString();
                String longAString = long_a.getText().toString();

                noteref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Toast.makeText(FaceTrackerActivity.this, "loaded fine", Toast.LENGTH_SHORT).show();
                            String latA = documentSnapshot.getString("latitude");
                            String longA = documentSnapshot.getString("longitude");
                            tv11.setText("" + latA);tv12.setText("" + longA);
                        } else {
                            Toast.makeText(FaceTrackerActivity.this, "oops", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                String tv11String = tv11.getText().toString();
                String tv12String = tv12.getText().toString();
                String[] parts = tv11String.split(",");
                String[] parts2 = tv12String.split(",");
                String[] partsClone = parts.clone();
                String[] parts2Clone = parts2.clone();

                for (int i = 0; i < parts.length - 1; i++) {
                    parts[i + 1] = partsClone[i];
                    parts2[i+1] = parts2Clone[i];
                }

                parts[0] = latAString;
                parts2[0] = longAString;
                Map<String, Object> note = new HashMap<>();
                String printPart = Arrays.toString(parts).replace("[", "").replace("]", "");
                String printPart2 = Arrays.toString(parts2).replace("[", "").replace("]", "");
                note.put("latitude", printPart);
                note.put("longitude", printPart2);


                noteref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FaceTrackerActivity.this, "okay", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FaceTrackerActivity.this, "not okay", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        b3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(FaceTrackerActivity.this,MapsActivity.class);
                startActivity(intent);
            }

        });
        // tv1.setText(Vvalue);
    }

    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */

    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {

        Dialog dialog1 = null;

        switch(id) {
            case CUSTOM_DIALOG_ID:
                dialog1 = new Dialog(FaceTrackerActivity.this,R.style.Theme_AppCompat);
                dialog1.setContentView(R.layout.dialoglayout);
                dialog1.setTitle("Custom Dialog");

                dialog_TextView = (TextView)dialog1.findViewById(R.id.dialogtext);
                TextView dialog_TextView2 = (TextView)dialog1.findViewById(R.id.dialogtext2);
                dialog_TextView3 = (TextView)dialog1.findViewById(R.id.dialogtext3);
                dialog_TextView2.setText("We found a suspicious face behind");
                Button dialog_OK = (Button)dialog1.findViewById(R.id.dialog_ok);
                Button callPolice = (Button)dialog1.findViewById(R.id.callPolice);
                dialog_OK.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        String latAString = lat_a.getText().toString();
                        String longAString = long_a.getText().toString();
                        noteref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    Toast.makeText(FaceTrackerActivity.this, "loaded fine", Toast.LENGTH_SHORT).show();
                                    String latA = documentSnapshot.getString("latitude");
                                    String longA = documentSnapshot.getString("longitude");
                                    tv11.setText("" + latA);tv12.setText("" + longA);
                                } else {
                                    Toast.makeText(FaceTrackerActivity.this, "oops", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        String tv11String = tv11.getText().toString();
                        String tv12String = tv12.getText().toString();
                        String[] parts = tv11String.split(",");
                        String[] parts2 = tv12String.split(",");
                        String[] partsClone = parts.clone();
                        String[] parts2Clone = parts2.clone();

                        for (int i = 0; i < parts.length - 1; i++) {
                            parts[i + 1] = partsClone[i];
                            parts2[i+1] = parts2Clone[i];
                        }

                        parts[0] = latAString;
                        parts2[0] = longAString;
                        Map<String, Object> note = new HashMap<>();
                        String printPart = Arrays.toString(parts).replace("[", "").replace("]", "");
                        String printPart2 = Arrays.toString(parts2).replace("[", "").replace("]", "");
                        note.put("latitude", printPart);
                        note.put("longitude", printPart2);


                        noteref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(FaceTrackerActivity.this, "okay", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FaceTrackerActivity.this, "not okay", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Intent intent = new Intent(FaceTrackerActivity.this,MapsActivity.class);
                        startActivity(intent);
                        }});

                Button dialog_Cancel = (Button)dialog1.findViewById(R.id.dialog_cancel);
                dialog_Cancel.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        dismissDialog(CUSTOM_DIALOG_ID);
                    }});

                callPolice.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        String number = "12345678";
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" +number));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        } else {
                            Toast.makeText(FaceTrackerActivity.this,"Can't resolve app for ACTION_DIAL Intent.",Toast.LENGTH_SHORT).show();
                        }
                    }});

                dialog1.show();
                Window window = dialog1.getWindow();
                window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                break;


        }

        return dialog1;
    }


    Thread thread = new Thread() {

        @Override
        public void run() {
            try {
                while (!thread.isInterrupted()) {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hp_inFaceTracker = FaceGraphic.hp;
                            tim=tim+1;
                            if(hp_inFaceTracker<=0.001){hp_inFaceTracker=10000;fac1[tim]=hp_inFaceTracker;}
                            else{hp_inFaceTracker=hp_inFaceTracker*100;fac1[tim]=hp_inFaceTracker;}
                            String te= hp_inFaceTracker + "";

                            tv2.setText("Time : "+ tim);
                            tv1.setText("Face value" +te);
                           if(tim>5){
                               for(int i=0;i<5;i++) {tot = tot+fac1[tim - i];}
                               tv4.setText(tot+"");
                               if(tot<1000){tv3.setText("DANGER!!!!");index=1;
                                   int minimumerrorindexnow=search_nearest(err_pos_now, lat_a, long_a);
//                                   String newEntry=String.valueOf(minimumerrorindexnow);
//                                   AddData(newEntry);
//                                   Cursor cursor=mDatabaseHelper.getData();
//                                   while(cursor.moveToNext()) {
//                                       tv10.setText("" + cursor.getString(1));
//                                   }


//                                   openDialog();
//                                   Intent intent = new Intent(FaceTrackerActivity.this,MapsActivity.class);
//                                   startActivity(intent);
                                   // Get instance of Vibrator from current Context
                                   Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                   v.vibrate(400);
                                   Bundle bundle = new Bundle();
                                   bundle.putString(KEY_TEXTPSS, lat_a.getText().toString());
                                   bundle.putString(KEY_TEXTPSS2, long_a.getText().toString());
                                   showDialog(CUSTOM_DIALOG_ID, bundle);
                               tim=0;
                               thread.interrupt();
                               }
                               else{tv3.setText("seems okay");}
                               tot=0;
                           }
                           else{tv3.setText("seems okay");}

                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        }
    };


    @Override
    protected void onPrepareDialog(int id, Dialog dialog1, Bundle bundle) {
// TODO Auto-generated method stub
        super.onPrepareDialog(id, dialog1, bundle);

        switch(id) {
            case CUSTOM_DIALOG_ID:
                dialog_TextView.setText("" + bundle.getString(KEY_TEXTPSS));
                dialog_TextView3.setText("" + bundle.getString(KEY_TEXTPSS2));
                break;
        }
    }

    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);
        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


    private void getDeviceLocation() {

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentlocation = (Location) task.getResult();
                            LatLng curloc=new LatLng(currentlocation.getLatitude(),currentlocation.getLongitude());
                            double curloc_lat=curloc.latitude;double curloc_lng=curloc.longitude;
                              lat_a.setText(curloc_lat+"");long_a.setText(curloc_lng+"");
                        } else
                        {Toast.makeText(FaceTrackerActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();}
                    }
                });
            }
        }catch (SecurityException e){Log.e(TAG,"srdytfughj");};
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINLOC) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COLOC) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQ_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQ_CODE);
        }
    }

    private int search_nearest(double[] err_pos, TextView lat_a,TextView long_a){
        double posnow_lat=Double.parseDouble(lat_a.getText().toString());
        double posnow_lng=Double.parseDouble(long_a.getText().toString());
        for(int i=0;i<dista.length;i++){
            double err_lat=posnow_lat-au_m[i][0];
            double err_lng=posnow_lng-au_m[i][1];
            double er_sq=err_lat*err_lat+err_lng*err_lng;
            err_pos[i] = java.lang.Math.sqrt(er_sq);
        }
        double[] err_pos2=err_pos.clone();
        Arrays.sort(err_pos2);double error_minimum=err_pos2[0];

        int minimumerrorindex=-1;

        for(int i=0;i<dista.length;i++){
            if(err_pos[i]==error_minimum){minimumerrorindex=i;}
        }
        return minimumerrorindex;
    }

    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        Snackbar.make(mGraphicOverlay, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show();
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the barcode detector to detect small barcodes
     * at long distances.
     */
    private void createCameraSource() {

        Context context = getApplicationContext();
        FaceDetector detector = new FaceDetector.Builder(context)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        detector.setProcessor(
                new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory())
                        .build());

        if (!detector.isOperational()) {
            // Note: The first time that an app using face API is installed on a device, GMS will
            // download a native library to the device in order to do detection.  Usually this
            // completes before the app is run for the first time.  But if that download has not yet
            // completed, then the above call will not detect any faces.
            //
            // isOperational() can be used to check if the required native library is currently
            // available.  The detector will automatically become operational once the library
            // download completes on device.
            Log.w(TAG, "Face detector dependencies are not yet available.");
        }

        mCameraSource = new CameraSource.Builder(context, detector)
                .setRequestedPreviewSize(640, 480)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(30.0f)
                .build();
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();

        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mPreview.stop();
    }

    /**
     * Releases the resources associated with the camera source, the associated detector, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraSource != null) {
            mCameraSource.release();
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            createCameraSource();
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Face Tracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }

    //==============================================================================================
    // Camera Source Preview
    //==============================================================================================

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    //==============================================================================================
    // Graphic Face Tracker
    //==============================================================================================

    /**
     * Factory for creating a face tracker to be associated with a new face.  The multiprocessor
     * uses this factory to create face trackers as needed -- one for each individual.
     */
    private class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {
        @Override
        public Tracker<Face> create(Face face) {
            return new GraphicFaceTracker(mGraphicOverlay);
        }
    }

    /**
     * Face tracker for each detected individual. This maintains a face graphic within the app's
     * associated face overlay.
     */
    private class GraphicFaceTracker extends Tracker<Face> {
        private GraphicOverlay mOverlay;
        private FaceGraphic mFaceGraphic;

        GraphicFaceTracker(GraphicOverlay overlay) {
            mOverlay = overlay;
            mFaceGraphic = new FaceGraphic(overlay);
        }

        /**
         * Start tracking the detected face instance within the face overlay.
         */
        @Override
        public void onNewItem(int faceId, Face item) {
            mFaceGraphic.setId(faceId);
        }

        /**
         * Update the position/characteristics of the face within the overlay.
         */
        @Override
        public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
            mOverlay.add(mFaceGraphic);
            mFaceGraphic.updateFace(face);
        }

        /**
         * Hide the graphic when the corresponding face was not detected.  This can happen for
         * intermediate frames temporarily (e.g., if the face was momentarily blocked from
         * view).
         */
        @Override
        public void onMissing(FaceDetector.Detections<Face> detectionResults) {
            mOverlay.remove(mFaceGraphic);
        }

        /**
         * Called when the face is assumed to be gone for good. Remove the graphic annotation from
         * the overlay.
         */
        @Override
        public void onDone() {
            mOverlay.remove(mFaceGraphic);
        }
    }
}
