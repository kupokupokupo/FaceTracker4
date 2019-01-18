package com.google.android.gms.samples.vision.face.facetracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    DatabaseHelper mDatabaseHelper;
    //    private static final String[]place_hint=new String[]{"Shibuya","Shinjuku","Ikebukuro","Tokyo","Ookayama","Akihabara",
//            "Tsunashima","Jiyugaoka","Midorigaoka","Meguro","Okusawa Hospital","Senzokuike","Kita-senzoku"};
//    private static final String[]place=new String[]{"Shibuya","Shinjuku","Ikebukuro","Tokyo","Ookayama","Akihabara",
//            "Tsunashima","Jiyugaoka","Midorigaoka","Meguro","Okusawa Hospital","Senzokuike","Wktk. Kinder.","Kita-senzoku"};
    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String[] place_hint = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", "101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113", "114", "115", "116", "117", "118", "119", "120", "121", "122", "123", "124", "125", "126", "127", "128", "129", "130", "131", "132", "133"};
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

    private static final int[] placePolice = new int[]{1, 2};
    public static double[][] au_mPolice = {{35.607107, 139.685200},
            {35.607248, 139.686033}};

    private static int[][] valu = {{14, 2, 0, 0},
            {1, 3, 15, 0},
            {2, 4, 16, 0},
            {3, 5, 17, 0},
            {4, 6, 18, 0},
            {5, 7, 26, 0},
            {6, 8, 27, 0},
            {7, 28, 9, 0},
            {8, 10, 29, 0},
            {9, 11, 30, 0},
            {12, 10, 0, 0},
            {11, 13, 32, 0},
            {12, 25, 32, 0},
            {1, 15, 33, 0},
            {2, 14, 16, 34},
            {3, 15, 17, 44},
            {4, 16, 18, 45},
            {5, 17, 19, 0},
            {26, 18, 20, 53},
            {27, 19, 21, 54},
            {28, 20, 22, 55},
            {30, 21, 23, 55},
            {31, 22, 24, 56},
            {32, 23, 25, 57},
            {13, 24, 59, 0},
            {6, 27, 19, 0},
            {7, 26, 20, 0},
            {8, 29, 21, 0},
            {9, 28, 30, 0},
            {10, 22, 29, 0},
            {11, 23, 32, 0},
            {12, 13, 31, 24},
            {14, 34, 35, 0},
            {15, 33, 36, 0},
            {33, 38, 130, 0},
            {34, 37, 42, 130},
            {36, 43, 0, 0},
            {35, 39, 40, 0},
            {38, 41, 130, 0},
            {38, 41, 71, 0},
            {39, 40, 42, 0},
            {36, 41, 43, 72},
            {37, 42, 44, 73},
            {16, 43, 45, 75},
            {17, 44, 46, 133},
            {53, 45, 47, 133},
            {46, 54, 47, 133},
            {47, 49, 55, 77},
            {22, 48, 50, 0},
            {49, 51, 56, 68},
            {50, 52, 58, 78},
            {51, 59, 70, 0},
            {19, 46, 54, 0},
            {20, 47, 53, 55},
            {21, 22, 48, 54},
            {23, 50, 57, 0},
            {24, 56, 58, 0},
            {51, 57, 59, 0},
            {25, 52, 58, 0},
            {61, 71, 84, 0},
            {60, 62, 72, 85},
            {61, 73, 86, 131},
            {64, 74, 131, 0},
            {63, 65, 75, 83},
            {64, 66, 133, 93},
            {65, 67, 76, 88},
            {66, 68, 77, 0},
            {50, 67, 91, 129},
            {70, 78, 92, 129},
            {52, 69, 98, 0},
            {40, 60, 72, 0},
            {42, 61, 71, 73},
            {43, 62, 72, 0},
            {63, 75, 0, 0},
            {44, 64, 74, 0},
            {47, 66, 77, 0},
            {48, 67, 76, 0},
            {51, 69, 129, 0},
            {80, 84, 99, 0},
            {85, 101, 108, 0},
            {82, 86, 103, 108},
            {81, 83, 87, 104},
            {64, 82, 105, 0},
            {60, 79, 85, 0},
            {61, 80, 84, 0},
            {62, 81, 87, 0},
            {82, 86, 131, 0},
            {66, 89, 94, 0},
            {88, 90, 91, 95},
            {89, 92, 96, 0},
            {68, 89, 92, 0},
            {69, 90, 91, 0},
            {65, 94, 107, 0},
            {88, 93, 95, 121},
            {89, 94, 96, 109},
            {90, 95, 97, 110},
            {96, 98, 124, 0},
            {70, 97, 125, 0},
            {79, 100, 111, 0},
            {99, 101, 112, 0},
            {80, 100, 102, 113},
            {108, 101, 103, 0},
            {81, 102, 104, 116},
            {82, 103, 105, 126},
            {83, 104, 106, 127},
            {105, 107, 128, 0},
            {92, 106, 120, 0},
            {80, 81, 102, 0},
            {95, 110, 0, 0},
            {96, 109, 0, 0},
            {99, 112, 114, 0},
            {100, 111, 113, 0},
            {101, 112, 115, 0},
            {111, 115, 0, 0},
            {113, 114, 116, 0},
            {103, 115, 117, 0},
            {116, 118, 126, 0},
            {117, 119, 127, 0},
            {118, 120, 128, 0},
            {107, 119, 121, 0},
            {94, 120, 122, 0},
            {109, 121, 123, 0},
            {110, 122, 124, 0},
            {97, 123, 125, 0},
            {98, 124, 0, 0},
            {104, 117, 127, 0},
            {105, 118, 126, 128},
            {106, 119, 127, 0},
            {68, 69, 132, 0},
            {35, 36, 39, 0},
            {62, 63, 87, 0},
            {78, 129, 0, 0},
            {45, 46, 65, 0}
    };

    private static double[][] valu2 = new double[place.length][4];

    private static double[] crm_rt = new double[place.length];
    private static double[] crowdedness = {0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01};
    private static double[] illum = {0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01};
    private static double[] dista = new double[place.length];

    private static double[] err_pos = new double[dista.length];
    private static double[] err_pos_now = new double[dista.length];
    private static int[][] path_val = new int[place.length][place.length];
    private static boolean[][] filter = new boolean[place.length][place.length];
    private static int[][] path_valMod = new int[place.length][place.length];
    private static boolean[][] filterMod = new boolean[place.length][place.length];

    private static double[] errPosPolice = new double[2];
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteref = db.collection("Dat").document("Data1");


    @Override
    public void onMapReady(GoogleMap googleMap) {
//        Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        if (mLocationPermissionGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            drawHeatmapWithoutSave();
            init();
        }
    }

    private GoogleMap mMap;

    private static final String TAG = "MapsActivity";
    private static final String FINLOC = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COLOC = Manifest.permission.ACCESS_COARSE_LOCATION;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQ_CODE = 12;
    private static final float DEFAULT_ZOOM = 15f;

    private EditText mSearch;
    private ImageView mGps;
    private Button res, polb, sear, alt;
    private TextView lat_a, long_a, tv70, tv71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        AutoCompleteTextView editText = (AutoCompleteTextView) findViewById(R.id.sea);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, place_hint);
        editText.setAdapter(adapter);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mSearch = (EditText) findViewById(R.id.sea);
        mGps = (ImageView) findViewById(R.id.geps);
        res = (Button) findViewById(R.id.rese);
        polb = (Button) findViewById(R.id.polbor);
        alt = (Button) findViewById(R.id.altr);
        sear = (Button) findViewById(R.id.norm);
        lat_a = (TextView) findViewById(R.id.lat_a);
        long_a = (TextView) findViewById(R.id.long_a);
        tv70 = (TextView) findViewById(R.id.tv70);
        tv71 = (TextView) findViewById(R.id.tv71);

         Intent intent = getIntent();
        String te = intent.getStringExtra(FaceTrackerActivity.EXTRA_TEXT);
        Toast.makeText(MapsActivity.this, "ini te" + te, Toast.LENGTH_SHORT).show();

//            int index00=Integer.parseInt(index0);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        getLocationPermission();
        init();
    }

    private void init() {
        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                    geoLocate();
                }
                return false;
            }
        });

        noteref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Toast.makeText(MapsActivity.this, "loaded fine", Toast.LENGTH_SHORT).show();
                    String latA = documentSnapshot.getString("latitude");
                    String longA = documentSnapshot.getString("longitude");
                    tv70.setText("" + latA);
                    tv71.setText("" + longA);
                } else {
                    Toast.makeText(MapsActivity.this, "oops", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MapsActivity.this, "oops", Toast.LENGTH_SHORT).show();
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });

        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                sear.setEnabled(true);
                polb.setEnabled(true);
                alt.setEnabled(true);

            }
        });

//                        mProvider = new HeatmapTileProvider.Builder().data(
//                        mLists.get(getString(R.string.police_stations)).getData()).build();
//                mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));

        polb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geoLocate2();
            }
        });

        sear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geoLocate();
            }
        });

        alt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geoLocate3();
            }
        });


//        drawHeatmap(tv70,tv71);
//                try {
//            mLists.put(getString(R.string.police_stations), new DataSet(readItems(R.raw.police),
//                    getString(R.string.police_stations_url)));
//        } catch (JSONException e) {
//            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
//        }
//
//
//        if (mProvider == null) {
//            mProvider = new HeatmapTileProvider.Builder().data(
//                    mLists.get(getString(R.string.police_stations)).getData()).build();
//            mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
//            // Render links
//        } else {
////            mProvider.setData(mLists.get(dataset).getData());
//            mOverlay.clearTileCache();}
//
//
//        // Check if need to instantiate (avoid setData etc twice)
//


    }


//    private void drawHeatmap(TextView tv70,TextView tv71){
//        String tv70String = tv70.getText().toString();
//        String tv71String = tv71.getText().toString();
//        String[] parts = tv70String.split(",");
//        String[] parts2 = tv71String.split(",");
//
//        for(int i=0;i<5;i++) {
//            double partsDouble = Double.valueOf(parts[i]);
//            double parts2Double = Double.valueOf(parts2[i]);
//            LatLng p1 = new LatLng(partsDouble, parts2Double);
//            mMap.addCircle(new CircleOptions().center(new LatLng(p1.latitude, p1.longitude)).radius(30).strokeWidth(0).fillColor(Color.argb(60,255,0,0)));
//            mMap.addCircle(new CircleOptions().center(new LatLng(p1.latitude, p1.longitude)).radius(75).strokeWidth(0).fillColor(Color.argb(50,255,109,0)));
//            mMap.addCircle(new CircleOptions().center(new LatLng(p1.latitude, p1.longitude)).radius(100).strokeWidth(0).fillColor(Color.argb(30,255,255,0)));
//            mMap.addCircle(new CircleOptions().center(new LatLng(p1.latitude, p1.longitude)).radius(150).strokeWidth(0).fillColor(Color.argb(30,102,255,51)));
//        }
//    }

    public void saveNote(View v) {
        String latAString = lat_a.getText().toString();
        String longAString = long_a.getText().toString();

        noteref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Toast.makeText(MapsActivity.this, "loaded fine", Toast.LENGTH_SHORT).show();
                    String latA = documentSnapshot.getString("latitude");
                    String longA = documentSnapshot.getString("longitude");
                    tv70.setText("" + latA);tv71.setText("" + longA);
                } else {
                    Toast.makeText(MapsActivity.this, "oops", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MapsActivity.this, "oops", Toast.LENGTH_SHORT).show();
            }
        });

        String tv70String = tv70.getText().toString();
        String tv71String = tv71.getText().toString();
        String[] parts = tv70String.split(",");
        String[] parts2 = tv71String.split(",");
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
                Toast.makeText(MapsActivity.this, "okay", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MapsActivity.this, "not okay", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void geoLocate() {
        mMap.clear();
        drawHeatmapWithoutSave();
        Intent intent = getIntent();

        String te2 = intent.getStringExtra(FaceTrackerActivity.EXTRA_TEXT);
        String searchString = mSearch.getText().toString();
        int indexPlace = -1;
        for (int i = 0; i < dista.length; i++) {
            if (place[i].equals(searchString)) {
                indexPlace = i;
            }
        }

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
        }

        double crt_lat = Double.parseDouble(lat_a.getText().toString());
        double crt_lng = Double.parseDouble(long_a.getText().toString());

        int lastin;
        if (indexPlace == -1) {
            Address address = list.get(0);
            movcam(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM);


            LatLng position_now = new LatLng(address.getLatitude(), address.getLongitude());
            double posnow_lat = position_now.latitude;
            double posnow_lng = position_now.longitude;

            lastin = search_nearest(err_pos, posnow_lat, posnow_lng);
        } else {
            lastin = indexPlace;
            movcam(new LatLng(au_m[indexPlace][0], au_m[indexPlace][1]), DEFAULT_ZOOM);
        }
//        Toast.makeText(MapsActivity.this,""+lastin,Toast.LENGTH_SHORT).show();
        int index = search_nearest(err_pos_now, crt_lat, crt_lng);
        int indexPerm = search_nearest(err_pos_now, crt_lat, crt_lng);

        String place_name = place[lastin];
        String depart = place[index];

        filter = dijkstraAlgorithm(index, lastin);
        int[] nextn2 = drawPly(filter, indexPerm);
        polylineDraw(nextn2);
//        Toast.makeText(MapsActivity.this,"This is destination index: "+ lastin,Toast.LENGTH_SHORT).show();
//            Toast.makeText(MapsActivity.this,"This is nearest point : "+place_name,Toast.LENGTH_SHORT).show();
//            Toast.makeText(MapsActivity.this,"Nearest Place : "+depart,Toast.LENGTH_SHORT).show();
//            Toast.makeText(MapsActivity.this,"Suspected place : "+te2,Toast.LENGTH_SHORT).show();
    }

    private void geoLocate3() {
        mMap.clear();
        drawHeatmapWithoutSave();
        Intent intent = getIntent();

        String te2 = intent.getStringExtra(FaceTrackerActivity.EXTRA_TEXT);
        String searchString = mSearch.getText().toString();
        int indexPlace = -1;
        for (int i = 0; i < dista.length; i++) {
            if (place[i].equals(searchString)) {
                indexPlace = i;
            }
        }

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {

        }

        double crt_lat = Double.parseDouble(lat_a.getText().toString());
        double crt_lng = Double.parseDouble(long_a.getText().toString());

        int lastin;
        if (indexPlace == -1) {
            Address address = list.get(0);
            movcam(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM);

            LatLng position_now = new LatLng(address.getLatitude(), address.getLongitude());
            double posnow_lat = position_now.latitude;
            double posnow_lng = position_now.longitude;

            lastin = search_nearest(err_pos, posnow_lat, posnow_lng);
        } else {
            lastin = indexPlace;
            movcam(new LatLng(au_m[indexPlace][0], au_m[indexPlace][1]), DEFAULT_ZOOM);
        }
        Toast.makeText(MapsActivity.this, "" + lastin, Toast.LENGTH_SHORT).show();
        int index = search_nearest(err_pos_now, crt_lat, crt_lng);
        int indexPerm = index;

        String place_name = place[lastin];
        String depart = place[index];
        filter = dijkstraAlgorithm(index, lastin);
        int[] nextn2 = drawPly(filter, indexPerm);

        if (nextn2.length > 2) {
            int avoidedNode = nextn2[1];
            Arrays.fill(dista, 1000000);
            filterMod = dijkstraAlgorithmMod(index, lastin, avoidedNode);
            int[] nextn3 = drawPly(filterMod, indexPerm);
            polylineDraw(nextn3);
            Toast.makeText(MapsActivity.this, "Avoid node : " + avoidedNode, Toast.LENGTH_SHORT).show();
        } else {
            polylineDraw(nextn2);
        }

        Circle circle = mMap.addCircle(new CircleOptions().center(new LatLng(35,170)).radius(100000).strokeColor(Color.RED).fillColor(Color.GREEN));
//        sear.setEnabled(false);
//        polb.setEnabled(false);
//        alt.setEnabled(false);
//        Toast.makeText(MapsActivity.this,"This is destination : "+place_name,Toast.LENGTH_SHORT).show();
//            Toast.makeText(MapsActivity.this,"This is nearest point : "+place_name,Toast.LENGTH_SHORT).show();
//            Toast.makeText(MapsActivity.this,"Nearest Place : "+depart,Toast.LENGTH_SHORT).show();
//            Toast.makeText(MapsActivity.this,"Suspected place : "+te2,Toast.LENGTH_SHORT).show();
    }

    private void geoLocate2() {
        mMap.clear();
        drawHeatmapWithoutSave();
        Intent intent = getIntent();
        String te2 = intent.getStringExtra(FaceTrackerActivity.EXTRA_TEXT);

        double crt_lat = Double.parseDouble(lat_a.getText().toString());
        double crt_lng = Double.parseDouble(long_a.getText().toString());
//
        int lastin = searchNearestPolice(errPosPolice, crt_lat, crt_lng);
        int index = search_nearest(err_pos_now, crt_lat, crt_lng);
        int indexPerm = index;
//
        String place_name = place[lastin];
        String depart = place[index];
        Toast.makeText(MapsActivity.this, "This is destination : " + place_name, Toast.LENGTH_SHORT).show();
        filter = dijkstraAlgorithm(index, lastin);
        int[] nextn2 = drawPly(filter, indexPerm);
        polylineDraw(nextn2);
        int lengthNextn2 = nextn2.length;
        int lastNextn2 = nextn2[lengthNextn2 - 1];
        movcam(new LatLng(au_m[lastNextn2][0], au_m[lastNextn2][1]), DEFAULT_ZOOM);
//        Toast.makeText(MapsActivity.this,"This is destination : "+place_name,Toast.LENGTH_SHORT).show();
//            Toast.makeText(MapsActivity.this,"This is nearest point : "+place_name,Toast.LENGTH_SHORT).show();
//            Toast.makeText(MapsActivity.this,"Nearest Place : "+depart,Toast.LENGTH_SHORT).show();
//            Toast.makeText(MapsActivity.this,"Suspected place : "+te2,Toast.LENGTH_SHORT).show();

    }

    private double[] euclideanDistance(double latA1,double longA1){
        double[] distance=new double [place.length];
        for(int j=0;j<place.length;j++) {
            distance[j]=measure(latA1,longA1,au_m[j][0],au_m[j][1]);
        }
        return distance;
    }

    private double searchMinArray(double[] arr) {
        double[] arrClone = arr.clone();
        Arrays.sort(arrClone);
        double minVal = arrClone[0];
        return minVal;
    }

     private double[][] formulateValue() {
         noteref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
             @Override
             public void onSuccess(DocumentSnapshot documentSnapshot) {
                 if (documentSnapshot.exists()) {
                     String latA = documentSnapshot.getString("latitude");
                     String longA = documentSnapshot.getString("longitude");
                     String[] parts = latA.split(",");
                     String[] parts2 = longA.split(",");

                     for(int i=0;i<5;i++){
                         double[] distance=euclideanDistance(Double.valueOf(parts[i]),Double.valueOf(parts2[i]));
                            for(int j=0;j<place.length;j++){
                                if(distance[j]<150){
                                    if (distance[j]<100){
                                        if(distance[j]<75){
                                            if (distance[j]<30){crm_rt[j]=crm_rt[j]+0.1*1000000;}
                                            else{crm_rt[j]=crm_rt[j]+0.05*1000000;}
                                        }
                                        else{crm_rt[j]=crm_rt[j]+0.01*1000000;}
                                    }
                                    else{crm_rt[j]=crm_rt[j]+0.005*1000000;}
                                }
                            }
                     }
                 }
             }
         });

         for (int i = 0; i < place.length; i++) {
             for (int j = 0; j < 4; j++) {
                 if (valu[i][j] != 0) {
                     int curNod = valu[i][j] - 1;
                     valu2[i][j]=measure(au_m[curNod][0],au_m[curNod][1],au_m[i][0],au_m[i][1])
                             +crm_rt[i]+crm_rt[curNod]+illum[i]+illum[curNod]+crowdedness[i]+crowdedness[curNod];
                 }
             }
         }

         return valu2;
     }


    private double measure(double lat1, double lon1, double lat2, double lon2){  // generally used geo measurement function
        double R = 6378.137; // Radius of earth in KM
        double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
        double dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d * 1000; // meters
    }

    private boolean[][] dijkstraAlgorithm(int index, int lastin) {
        Arrays.fill(dista, 100000);
        valu2=formulateValue();
        filter = new boolean[place.length][place.length];
        path_val = new int[place.length][place.length];
        String[] place2 = place.clone();
        double index2 = 0;
        dista[index] = 0;
        place2[index] = "0";
        double distanceNode[] = detDistance(index);
        int burnIndex = 1;
        double[] dist2 = dista.clone();
        for (int i = 0; (i < place2.length); i++) {
            for (int k = 0; k < dista.length; ++k) {
                if (dista[k] > distanceNode[k] + index2) {
                    dista[k] = distanceNode[k] + index2;
                    dist2[k] = distanceNode[k] + index2;
                    path_val[index][k] = 1;
                }
            }
            for (int mb = 0; mb < dista.length; mb++) {
                double kak = searchMinArray(dist2);
                int index3 = -1;
                String index4;
                int index5 = -1;
                for (int l = 0; l < dista.length; l++) {
                    if (dist2[l] == kak) {
                        index3 = l;
                        index4 = place[l];
                        for (int ma = 0; ma < dista.length; ma++) {
                            if (place2[ma].equals(index4)) {
                                index5 = 0;
                                burnIndex++;
                                mb = dista.length;
                                place2[index3] = "0";
                                index2 = dist2[index3];
                                index = index3;
                                distanceNode = detDistance(index);
                                l = dista.length;
                                break;
                            }
                        }
                        if (index5 == -1) {
                            dist2[index3] = 1000000;
                        }
                    }
                }
            }
            if (place2[lastin].equals("0")) {
                break;
            }
            if (burnIndex == dista.length) {
                break;
            }
        }
//
        for (int mc = 0; mc < place2.length; mc++) {
            for (int ia = 0; (ia < place2.length); ia++) {
                if (path_val[ia][lastin] == 1) {
                    filter[ia][lastin] = true;
                    lastin = ia;
                    ia = place2.length;
                }
            }
        }
        return filter;
    }

    private boolean[][] dijkstraAlgorithmMod(int index, int lastin, int avoidedNode) {
        Arrays.fill(dista, 100000);
        filterMod = new boolean[place.length][place.length];
        path_valMod = new int[place.length][place.length];
        String[] place2 = place.clone();
        double index2 = 0;
        dista[index] = 0;
        place2[index] = "0";
        double distanceNode[] = detDistanceMod(index, avoidedNode);
        int burnIndex = 1;
        double[] dist2 = dista.clone();
        for (int i = 0; (i < place2.length); i++) {
            for (int k = 0; k < dista.length; ++k) {
                if (dista[k] > distanceNode[k] + index2) {
                    dista[k] = distanceNode[k] + index2;
                    dist2[k] = distanceNode[k] + index2;
                    path_valMod[index][k] = 1;
                }
            }
            for (int mb = 0; mb < dista.length; mb++) {
                double kak = searchMinArray(dist2);
                int index5 = -1;
                for (int l = 0; l < dista.length; l++) {
                    if (dist2[l] == kak) {
                        int index3 = l;
                        String index4 = place[l];
                        for (int ma = 0; ma < dista.length; ma++) {
                            if (place2[ma].equals(index4)) {
                                index5 = 0;
                                burnIndex++;
                                mb = dista.length;
                                place2[index3] = "0";
                                index2 = dist2[index3];
                                index = index3;
                                distanceNode = detDistanceMod(index, avoidedNode);
                                l = dista.length;
                                break;
                            }
                        }
                        if (index5 == -1) {
                            dist2[index3] = 10000000;
                        }
                    }
                }
            }
            if (place2[lastin].equals("0")) {
                break;
            }
            if (burnIndex == dista.length - 1) {
                break;
            }
        }
//
        for (int mc = 0; mc < place2.length; mc++) {
            for (int ia = 0; (ia < place2.length); ia++) {
                if (path_valMod[ia][lastin] == 1) {
                    filterMod[ia][lastin] = true;
                    lastin = ia;
                    ia = place2.length;
                }
            }
        }
        return filterMod;
    }


    private int[] drawPly(boolean[][] filter, int indexPerm) {

        int[] nextn = new int[dista.length];
        int indexc = -1;
        nextn[0] = indexPerm + 1;

        for (int i = 1; i < dista.length; i++) {
            for (int j = 0; j < dista.length; j++) {
                if (filter[indexPerm][j]) {
                    nextn[i] = j + 1;
                    indexc = j;
                }
            }
            indexPerm = indexc;
        }
//
        int indexd = 0;
        for (int id = 0; id < nextn.length; id++) {
            if (nextn[id] != 0) {
                indexd = indexd + 1;
            }
        }

        int[] nextn2 = new int[indexd];
        for (int id = 0; id < nextn2.length; id++) {
            nextn2[id] = nextn[id] - 1;
            ;
        }
        return nextn2;
    }

    private double[] detDistance(int index) {
        double[] distanceNode = new double[place.length];
        Arrays.fill(distanceNode, 100000);
        for (int i = 0; i < place.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (valu[index][j] == i + 1) {
                    distanceNode[i] = valu2[index][j];
                }
            }
        }
        return distanceNode;
    }

    private double[] detDistanceMod(int index, int avoidedNode) {
        double[] distanceNode = new double[place.length];
        Arrays.fill(distanceNode, 100000);
        for (int i = 0; i < place.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (valu[index][j] == i + 1) {
                    if (valu[index][j] != avoidedNode + 1) {
                        distanceNode[i] = valu2[index][j];
                    }
                }
            }
        }
        return distanceNode;
    }

    private void polylineDraw(int[] nextn2) {
        for (int i = 0; i < nextn2.length - 1; i++) {
            LatLng p1 = new LatLng(au_m[nextn2[i]][0], au_m[nextn2[i]][1]);
            LatLng p2 = new LatLng(au_m[nextn2[i + 1]][0], au_m[nextn2[i + 1]][1]);
            mMap.addPolyline(new PolylineOptions().add(p1, p2).width(8F).color(Color.rgb(0, 100, 0)));
        }
    }

    private void drawHeatmapWithoutSave() {
        noteref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Toast.makeText(MapsActivity.this, "loaded fine", Toast.LENGTH_SHORT).show();
                    String latA = documentSnapshot.getString("latitude");
                    String longA = documentSnapshot.getString("longitude");
                    String[] parts = latA.split(",");
                    String[] parts2 = longA.split(",");
                    for (int i = 0; i < 5; i++) {
                        double partsDouble = Double.valueOf(parts[i]);
                        double parts2Double = Double.valueOf(parts2[i]);
                        LatLng p1 = new LatLng(partsDouble, parts2Double);
                        mMap.addCircle(new CircleOptions().center(new LatLng(p1.latitude, p1.longitude)).radius(30).strokeWidth(0).fillColor(Color.argb(60, 255, 0, 0)));
                        mMap.addCircle(new CircleOptions().center(new LatLng(p1.latitude, p1.longitude)).radius(75).strokeWidth(0).fillColor(Color.argb(50, 255, 109, 0)));
                        mMap.addCircle(new CircleOptions().center(new LatLng(p1.latitude, p1.longitude)).radius(100).strokeWidth(0).fillColor(Color.argb(30, 255, 255, 0)));
                        mMap.addCircle(new CircleOptions().center(new LatLng(p1.latitude, p1.longitude)).radius(150).strokeWidth(0).fillColor(Color.argb(30, 102, 255, 51)));
                    }
                } else {
                    Toast.makeText(MapsActivity.this, "oops", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MapsActivity.this, "oops", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int search_nearest(double[] err_pos, double posnow_lat, double posnow_lng) {
        for (int i = 0; i < dista.length; i++) {
            double err_lat = posnow_lat - au_m[i][0];
            double err_lng = posnow_lng - au_m[i][1];
            double er_sq = err_lat * err_lat + err_lng * err_lng;
            err_pos[i] = java.lang.Math.sqrt(er_sq);
        }
        double error_minimum = searchMinArray(err_pos);
        int minimumerrorindex = -1;
        for (int i = 0; i < dista.length; i++) {
            if (err_pos[i] == error_minimum) {
                minimumerrorindex = i;
            }
        }
        return minimumerrorindex;
    }


    private int searchNearestPolice(double[] errPosPolice, double crtLat, double crtLng) {
        for (int i = 0; i < placePolice.length; i++) {
            double err_lat = crtLat - au_mPolice[i][0];
            double err_lng = crtLng - au_mPolice[i][1];
            double er_sq = err_lat * err_lat + err_lng * err_lng;
            errPosPolice[i] = java.lang.Math.sqrt(er_sq);
        }
        double error_minimum = searchMinArray(errPosPolice);
        int minimumerrorindex = -1;
        for (int i = 0; i < errPosPolice.length; i++) {
            if (errPosPolice[i] == error_minimum) {
                minimumerrorindex = i;
            }
        }
        int placeNamePolice = placePolice[minimumerrorindex];
        Toast.makeText(MapsActivity.this, "" + placeNamePolice, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < dista.length; i++) {
            if (place[i].equals(placeNamePolice)) {
                minimumerrorindex = i;
            }
        }
        return minimumerrorindex;
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINLOC) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COLOC) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQ_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQ_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQ_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                }
                mLocationPermissionGranted = true;
                initMap();
            }
        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation : Getting the Device Location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(MapsActivity.this, "Your location is found!", Toast.LENGTH_SHORT).show();
                            Location currentlocation = (Location) task.getResult();
                            movcam(new LatLng(currentlocation.getLatitude(), currentlocation.getLongitude()), DEFAULT_ZOOM);
                            LatLng curloc = new LatLng(currentlocation.getLatitude(), currentlocation.getLongitude());
                            double curloc_lat = curloc.latitude;
                            double curloc_lng = curloc.longitude;
                            lat_a.setText(curloc_lat + "");
                            long_a.setText(curloc_lng + "");
                        } else {
                            Toast.makeText(MapsActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "srdytfughj");
        }
        ;
    }


    private void movcam(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        MarkerOptions options = new MarkerOptions().position(latLng);
        mMap.addMarker(options);
    }



    private static final int ALT_HEATMAP_RADIUS = 50;

    private static final int[] ALT_HEATMAP_GRADIENT_COLORS = {
                Color.argb(0, 0, 255, 255),// transparent
                Color.argb(255 / 3 * 2, 0, 255, 255),
                Color.rgb(0, 191, 255),
                Color.rgb(0, 0, 127),
                Color.rgb(255, 0, 0)
        };

    public static final float[] ALT_HEATMAP_GRADIENT_START_POINTS = {
                0.0f, 0.10f, 0.20f, 0.60f, 1.0f
        };


    public static final Gradient ALT_HEATMAP_GRADIENT = new Gradient(ALT_HEATMAP_GRADIENT_COLORS,
                ALT_HEATMAP_GRADIENT_START_POINTS);

    private HeatmapTileProvider mProvider;
    private TileOverlay mOverlay;

//
//
//    /**
//     * Maps name of data set to data (list of LatLngs)
//     * Also maps to the URL of the data set for attribution
//     */

//


    // Datasets from http://data.gov.au
    private ArrayList<LatLng> readItems(int resource) throws JSONException {
        ArrayList<LatLng> list = new ArrayList<LatLng>();
        InputStream inputStream = getResources().openRawResource(resource);
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            list.add(new LatLng(lat, lng));
        }
        return list;
    }

    /**
     * Helper class - stores data sets and sources.
     */


}
