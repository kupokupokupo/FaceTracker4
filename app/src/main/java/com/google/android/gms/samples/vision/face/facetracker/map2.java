package com.google.android.gms.samples.vision.face.facetracker;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class map2 extends AppCompatActivity {

    private static EditText pas;
    private static Button button,see,mp;
    private static final String FINLOC = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COLOC = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String TAG="map2";
    private static int ERROR_DIALOG_REQUEST=9001;
    private Boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQ_CODE = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

        pas = (EditText)findViewById(R.id.pas);
//        button = (Button)findViewById(R.id.button);
        see = (Button) findViewById(R.id.see);
        mp = (Button) findViewById(R.id.button8);
        getLocationPermission();

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                validate(pas.getText().toString());
//            }
//        });

        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(map2.this,FaceTrackerActivity.class);
                startActivity(intent);
            }
        });


        if(isserviceokay());
        mp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(map2.this,MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void validate(String pass) {
        if (pas.getText().toString().equals("project")){
            Toast.makeText(map2.this,"Username and password is correct",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(map2.this,MapsActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(map2.this,"Oops! Please enter 'project'....",
                    Toast.LENGTH_SHORT).show();
        }


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

    public boolean isserviceokay(){
        Log.d(TAG,"cek google version");
        int av=GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(map2.this);

        if(av== ConnectionResult.SUCCESS){
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(av)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(map2.this,av,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
        }
        return false;

    }

}
