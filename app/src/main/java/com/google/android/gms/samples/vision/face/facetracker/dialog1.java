package com.google.android.gms.samples.vision.face.facetracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.widget.TextView;

public class dialog1 extends AppCompatDialogFragment {
    public static final String EXTRA_TEXT="com.google.android.gms.samples.vision.face.facetracker.EXTRA_TEXT";
    TextView tv10,lat_a,long_a;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle("Warning")
                .setMessage("Suspicious person has been detected. Please go to the map to update your route plan.")
                .setNegativeButton("Back to Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });
//                .setPositiveButton("Report and Proceed..", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String te= tv10.getText().toString() + "";
//                        Intent intent= new Intent(getContext(),MapsActivity.class);
//                        intent.putExtra(EXTRA_TEXT,te);
//                        startActivity(intent);
//                    }
//                });
        return builder.create();
    }
}
