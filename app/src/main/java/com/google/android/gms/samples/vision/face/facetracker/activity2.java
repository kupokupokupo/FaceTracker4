package com.google.android.gms.samples.vision.face.facetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class activity2 extends AppCompatActivity {



    static public int value_in_ac2 = 0;
    private static Button reroute,polbox;

    private static TextView tv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);
        Intent intent=getIntent();
        String te=((Intent) intent).getStringExtra(FaceTrackerActivity.EXTRA_TEXT);
        tv3=(TextView)findViewById(R.id.textView3);
        tv3.setText(te);


        reroute = (Button) findViewById(R.id.button5);
        polbox = (Button) findViewById(R.id.button6);
        reroute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity2.this,map3.class);
                startActivity(intent);
            }
        });

        polbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity2.this,map3.class);
                startActivity(intent);
            }
        });
    }
}
