package com.google.android.gms.samples.vision.face.facetracker;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
import org.w3c.dom.Text;

import java.util.Arrays;


public class map3 extends Activity {
    private static final String[]place=new String[]{"A","B","C"};
    TextView texx;
    Button  buttonAdd, buttonSub, buttonMul, unsure, but_b, but_c, but_i, but_h;
    EditText cur,des;
    TextView path_AB,path_BC,path_AD,path_BE,path_CF,path_DE,path_DG,path_EF,path_EH, path_GH, path_FI, path_HI;
    TextView arr_AB,arr_BC,arr_AD,arr_BE,arr_CF,arr_DE,arr_DG,arr_EF,arr_EH, arr_GH, arr_FI, arr_HI;
    String stat_AB,stat_BC,stat_AD,stat_BE,stat_CF,stat_DE,stat_DG,stat_EF,stat_EH, stat_GH, stat_FI, stat_HI;

    double il_A=0.8, il_B=0.64, il_C=0.1, il_D=0.8, il_E=0.46, il_F=0.69, il_G=0.89, il_H=0.6, il_I=0.56,
            cr_A=0.07, cr_B=0.4, cr_C=0.56, cr_D=0.57, cr_E=0.23, cr_F=0.69, cr_G=0.39, cr_H=0.29, cr_I=0.28,
            rc_A=0.21, rc_B=0.5, rc_C=0.32, rc_D=0.25, rc_E=0.32, rc_F=0.45, rc_G=0.53, rc_H=0.4, rc_I=0.23,
            dist_AB=0.52,dist_BC=0.23,dist_AD=0.67,dist_BE=0.1,dist_CF=1,dist_DE=0.56,dist_DG=0.9, dist_EF=0.5,
            dist_EH=2.3, dist_GH=0.71, dist_FI=0.45, dist_HI=0.34;

    public static double mathround (double a) {
        double b=Math.round(a*100.0)/100.0;;
        return b;
    }

    double num_path_AB=il_A*il_B+cr_A*cr_B+rc_A*rc_B*dist_AB;
    double num_path_BC=il_B*il_C+cr_B*cr_C+rc_B*rc_C*dist_BC;
    double num_path_AD=il_A*il_D+cr_A*cr_D+rc_A*rc_D*dist_AD;
    double num_path_DE=il_D*il_E+cr_D*cr_E+rc_D*rc_E*dist_DE;
    double num_path_BE=il_B*il_E+cr_B*cr_E+rc_B*rc_E*dist_BE;
    double num_path_EF=il_E*il_F+cr_E*cr_F+rc_E*rc_F*dist_EF;
    double num_path_EH=il_E*il_H+cr_F*cr_H+rc_E*rc_H*dist_EH;
    double num_path_FI=il_F*il_I+cr_F*cr_I+rc_F*rc_I*dist_FI;
    double num_path_CF=il_C*il_F+cr_C*cr_F+rc_C*rc_F*dist_CF;
    double num_path_HI=il_H*il_I+cr_H*cr_I+rc_H*rc_I*dist_HI;
    double num_path_DG=il_D*il_G+cr_D*cr_G+rc_D*rc_G*dist_DG;
    double num_path_GH=il_G*il_H+cr_G*cr_H+rc_G*rc_H*dist_GH;
    double num_path_A_Sda=il_G*il_H+cr_G*cr_H+rc_G*rc_H*dist_GH;
    double num_path_Mj_Sby=il_G*il_H+cr_G*cr_H+rc_G*rc_H*dist_DG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_map3);

        texx=(TextView)findViewById(R.id.tex1);
        Button but=(Button) findViewById(R.id.jenson);


//        but.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                new map3.doit().execute();
//            }
//        });


        AutoCompleteTextView editText = (AutoCompleteTextView) findViewById(R.id.cur);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,place);
        editText.setAdapter(adapter);

        cur=(EditText) findViewById(R.id.cur);
        des=(EditText) findViewById(R.id.des);

        buttonAdd = (Button) findViewById(R.id.buttonadd);
        buttonSub = (Button) findViewById(R.id.buttonsub);
        buttonMul = (Button) findViewById(R.id.buttonmul);
        but_b = (Button) findViewById(R.id.but_b);
        but_c = (Button) findViewById(R.id.but_c);
        but_i = (Button) findViewById(R.id.but_i);
        but_h = (Button) findViewById(R.id.but_h);
        unsure = (Button) findViewById(R.id.unsure);
        path_AB = (TextView) findViewById(R.id.path_AB);
        path_AD = (TextView) findViewById(R.id.path_AD);
        path_BC = (TextView) findViewById(R.id.path_BC);
        path_DE = (TextView) findViewById(R.id.path_DE);
        path_BE = (TextView) findViewById(R.id.path_BE);
        path_EF = (TextView) findViewById(R.id.path_EF);
        path_EH = (TextView) findViewById(R.id.path_EH);
        path_FI = (TextView) findViewById(R.id.path_FI);
        path_CF = (TextView) findViewById(R.id.path_CF);
        path_HI = (TextView) findViewById(R.id.path_HI);
        path_DG = (TextView) findViewById(R.id.path_DG);
        path_GH = (TextView) findViewById(R.id.path_GH);
        arr_AB = (TextView) findViewById(R.id.arr_AB);
        arr_AD = (TextView) findViewById(R.id.arr_AD);
        arr_BC = (TextView) findViewById(R.id.arr_BC);
        arr_DE = (TextView) findViewById(R.id.arr_DE);
        arr_BE = (TextView) findViewById(R.id.arr_BE);
        arr_EF = (TextView) findViewById(R.id.arr_EF);
        arr_EH = (TextView) findViewById(R.id.arr_EH);
        arr_FI = (TextView) findViewById(R.id.arr_FI);
        arr_CF = (TextView) findViewById(R.id.arr_CF);
        arr_HI = (TextView) findViewById(R.id.arr_HI);
        arr_DG = (TextView) findViewById(R.id.arr_DG);
        arr_GH = (TextView) findViewById(R.id.arr_GH);

        but_b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                cur.setText("B");
            }

        });
        but_c.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                cur.setText("C");
            }

        });
        but_i.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                cur.setText("I");
            }

        });
        but_h.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                cur.setText("H");
            }

        });



        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num_path_AB=mathround(num_path_AB);	num_path_BC=mathround(num_path_BC);
                num_path_AD=mathround(num_path_AD); num_path_DE=mathround(num_path_DE);
                num_path_BE=mathround(num_path_BE); num_path_EF=mathround(num_path_EF);
                num_path_EH=mathround(num_path_EH);	num_path_FI=mathround(num_path_FI);
                num_path_CF=mathround(num_path_CF);	num_path_HI=mathround(num_path_HI);
                num_path_DG=mathround(num_path_DG);	num_path_GH=mathround(num_path_GH);
                num_path_A_Sda=mathround(num_path_A_Sda);num_path_Mj_Sby=mathround(num_path_Mj_Sby);

                path_AB.setText(num_path_AB+"");path_AD.setText(num_path_AD+"");
                path_BC.setText(num_path_BC+"");path_DE.setText(num_path_DE+"");
                path_BE.setText(num_path_BE+"");path_EF.setText(num_path_EF+"");
                path_EH.setText(num_path_EH+"");path_FI.setText(num_path_FI+"");
                path_CF.setText(num_path_CF+"");path_HI.setText(num_path_HI+"");
                path_DG.setText(num_path_DG+"");path_GH.setText(num_path_GH+"");

                arr_AB.setText(" ");arr_BC.setText(" ");
                arr_AD.setText(" ");arr_DE.setText(" ");
                arr_BE.setText(" ");arr_EF.setText(" ");
                arr_EH.setText(" ");arr_FI.setText(" ");
                arr_CF.setText(" ");arr_HI.setText(" ");
                arr_DG.setText(" ");arr_GH.setText(" ");

                double[][] valu= {{0,num_path_AB,100,num_path_AD,100,100,100,100,100,num_path_A_Sda,100},
                        {num_path_AB,0,num_path_BC,100,num_path_BE,100,100,100,100,100,num_path_Mj_Sby},
                        {100,num_path_BC,0,100,100,num_path_CF,100,100,100,100,100},
                        {num_path_AD,100,100,0,num_path_DE,100,num_path_DG,100,100,100,100},
                        {100,num_path_BE,100,num_path_DE,0,num_path_EF,100,num_path_EH,100,100,100},
                        {100,100,num_path_CF,100,num_path_EF,0,100,100,num_path_FI,100,100},
                        {100,100,100,num_path_DG,100,100,0,num_path_GH,100,100,100},
                        {100,100,100,100,100,100,num_path_GH,0,num_path_HI,100,100},
                        {100,100,100,100,100,num_path_FI,100,num_path_HI,0,100,100},
                        {num_path_A_Sda,100,100,100,100,100,100,100,100,100,100},
                        {100,num_path_Mj_Sby,100,100,100,100,100,100,100,100,100}};

                int[][] path_val=new int[11][11];  int[][] filter=new int[11][11];

                double[] dist= {100,100,100,100,100,100,100,100,100,100,100};
                String[] node= {"A","B","C","D","E","F","G","H","I","Sda","Sby"};
                String[] node2= {"A","B","C","D","E","F","G","H","I","Sda","Sby"};
                String curl= String.valueOf(cur.getText()); String dest= String.valueOf(des.getText());
                int index = -1;int lastin=-1;
                for (int i = 0; (i < dist.length); i++) {
                    if (node[i].equals(curl)) {index = i;}
                    if (node[i].equals(dest)) {lastin = i;}
                }

                double index2=0;
                for (int i=0;(i<node.length);i++) {
                    dist[index]=0;node[index]="0";
                }
                for (int i=0;(i<node2.length);i++) {
                    int j=0, k=0;
                    for(k=0;k<dist.length;++k)
                    {if (dist[k]>valu[index][k]+index2){dist[k]=valu[index][k]+index2;
                        for(int ka=0;ka<dist.length;++ka)
                            if(ka==index) {path_val[ka][k]=1;}
                            else {path_val[ka][k]=0;};}
                    };
                    double[]dist2=dist.clone();
                    for(int mb=0;mb<node2.length;mb++){
                        double[] dist3=dist2.clone();Arrays.sort(dist3);double kak=dist3[0];int index3=-1;String index4;
                        int index5=-1;
                        for (int l=0;l<node2.length;l++) {
                            if (dist2[l]==kak) {index3 = l;index4 = node2[l];
                                for (int ma=0;ma<node.length;ma++) {if (node[ma].equals(index4)) {index5=0;System.out.println("Node "+ index4+" valid. Pencarian selesai.");
                                    mb=node2.length;node[index3]="0";index2=dist2[index3];index=index3;}}
                                if(index5==-1) {dist2[index3]=1000;}
                            }
                        }
                    }

                    for(int mc=0;mc<node2.length;mc++) {
                        for (int ia = 0; (ia < node2.length); ia++) {
                            if (path_val[ia][lastin] == 1) {
                                filter[ia][lastin] = 1;lastin = ia;ia = node2.length;
                            }
                        }
                    }
                }

                if(filter[0][1]==1) {stat_AB=">>";} else if(filter[1][0]==1){stat_AB="^^";} else {stat_AB="";}
                if(filter[1][2]==1) {stat_BC=">>";} else if(filter[2][1]==1){stat_BC="<<";} else {stat_BC="";}
                if(filter[0][3]==1) {stat_AD="vv";} else if(filter[3][0]==1){stat_AD="^^";} else {stat_AD="";}
                if(filter[3][4]==1) {stat_DE=">>";} else if(filter[4][3]==1){stat_DE="<<";} else {stat_DE="";}
                if(filter[1][4]==1) {stat_BE="vv";} else if(filter[4][1]==1){stat_BE="^^";} else {stat_BE="";}
                if(filter[4][5]==1) {stat_EF=">>";} else if(filter[5][4]==1){stat_EF="<<";} else {stat_EF="";}
                if(filter[4][7]==1) {stat_EH="vv";} else if(filter[7][4]==1){stat_EH="^^";} else {stat_EH="";}
                if(filter[5][8]==1) {stat_FI="vv";} else if(filter[8][5]==1){stat_FI="^^";} else {stat_FI="";}
                if(filter[2][5]==1) {stat_CF="vv";} else if(filter[5][2]==1){stat_CF="^^";} else {stat_CF="";}
                if(filter[7][8]==1) {stat_HI=">>";} else if(filter[8][7]==1){stat_HI="<<";} else {stat_HI="";}
                if(filter[3][6]==1) {stat_DG="vv";} else if(filter[6][3]==1){stat_DG="^^";} else {stat_DG="";}
                if(filter[6][7]==1) {stat_GH=">>";} else if(filter[7][6]==1){stat_GH="<<";} else {stat_GH="";}
                if(filter[0][9]==1) {stat_GH="shi";} else if(filter[9][0]==1){stat_GH="shi";} else {stat_GH="";}

                arr_AB.setText(stat_AB+"");arr_BC.setText(stat_BC+"");
                arr_AD.setText(stat_AD+"");arr_DE.setText(stat_DE+"");
                arr_BE.setText(stat_BE+"");arr_EF.setText(stat_EF+"");
                arr_EH.setText(stat_EH+"");arr_FI.setText(stat_FI+"");
                arr_CF.setText(stat_CF+"");arr_HI.setText(stat_HI+"");
                arr_DG.setText(stat_DG+"");arr_GH.setText(stat_GH+"");
            }
        });

        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arr_AB.setText("");arr_BC.setText("");
                arr_AD.setText("");arr_DE.setText("");
                arr_BE.setText("");arr_EF.setText("");
                arr_EH.setText("");arr_FI.setText("");
                arr_CF.setText("");arr_HI.setText("");
                arr_DG.setText("");arr_GH.setText("");

                int[][] path_val=new int[11][11];  int[][] filter=new int[11][11];
                num_path_AB=mathround(num_path_AB);	num_path_BC=mathround(num_path_BC);
                num_path_AD=mathround(num_path_AD); num_path_DE=mathround(num_path_DE);
                num_path_BE=mathround(num_path_BE); num_path_EF=mathround(num_path_EF);
                num_path_EH=mathround(num_path_EH);	num_path_FI=mathround(num_path_FI);
                num_path_CF=mathround(num_path_CF);	num_path_HI=mathround(num_path_HI);
                num_path_DG=mathround(num_path_DG);	num_path_GH=mathround(num_path_GH);
                num_path_A_Sda=mathround(num_path_A_Sda);num_path_Mj_Sby=mathround(num_path_Mj_Sby);

                path_AB.setText(num_path_AB+"");path_AD.setText(num_path_AD+"");
                path_BC.setText(num_path_BC+"");path_DE.setText(num_path_DE+"");
                path_BE.setText(num_path_BE+"");path_EF.setText(num_path_EF+"");
                path_EH.setText(num_path_EH+"");path_FI.setText(num_path_FI+"");
                path_CF.setText(num_path_CF+"");path_HI.setText(num_path_HI+"");
                path_DG.setText(num_path_DG+"");path_GH.setText(num_path_GH+"");
                double par=0.3;
                double num_path_AB_r=num_path_AB+Math.random()*par;double num_path_BC_r=num_path_BC+Math.random()*par;
                double num_path_AD_r=num_path_AD+Math.random()*par;double num_path_DE_r=num_path_DE+Math.random()*par;
                double num_path_BE_r=num_path_BE+Math.random()*par;double num_path_EF_r=num_path_EF+Math.random()*par;
                double num_path_EH_r=num_path_EH+Math.random()*par;double num_path_FI_r=num_path_FI+Math.random()*par;
                double num_path_CF_r=num_path_CF+Math.random()*par;double num_path_HI_r=num_path_HI+Math.random()*par;
                double num_path_DG_r=num_path_DG+Math.random()*par;double num_path_GH_r=num_path_DG+Math.random()*par;

                double[][] valu= {{0,num_path_AB,100,num_path_AD,100,100,100,100,100,num_path_A_Sda,100},
                        {num_path_AB,0,num_path_BC,100,num_path_BE,100,100,100,100,100,num_path_Mj_Sby},
                        {100,num_path_BC,0,100,100,num_path_CF,100,100,100,100,100},
                        {num_path_AD,100,100,0,num_path_DE,100,num_path_DG,100,100,100,100},
                        {100,num_path_BE,100,num_path_DE,0,num_path_EF,100,num_path_EH,100,100,100},
                        {100,100,num_path_CF,100,num_path_EF,0,100,100,num_path_FI,100,100},
                        {100,100,100,num_path_DG,100,100,0,num_path_GH,100,100,100},
                        {100,100,100,100,100,100,num_path_GH,0,num_path_HI,100,100},
                        {100,100,100,100,100,num_path_FI,100,num_path_HI,0,100,100},
                        {num_path_A_Sda,100,100,100,100,100,100,100,100,100,100},
                        {100,num_path_Mj_Sby,100,100,100,100,100,100,100,100,100}};

                double[] dist= {100,100,100,100,100,100,100,100,100,100,100};
                String[] node= {"A","B","C","D","E","F","G","H","I","Sda","Sby"};
                String[] node2= {"A","B","C","D","E","F","G","H","I","Sda","Sby"};
                String curl= String.valueOf(cur.getText()); String dest= String.valueOf(des.getText());
                int index = -1;int lastin=-1;
                for (int i = 0; (i < dist.length); i++) {
                    if (node[i].equals(curl)) {index = i;}
                    if (node[i].equals(dest)) {lastin = i;}
                }

                double index2=0;
                for (int i=0;(i<node.length);i++) {
                    dist[index]=0;node[index]="0";
                }
                for (int i=0;(i<node2.length);i++) {
                    int j=0, k=0;
                    for(k=0;k<dist.length;++k)
                    {System.out.println("k "+k);
                        if (dist[k]>valu[index][k]+index2){dist[k]=valu[index][k]+index2;
                            for(int ka=0;ka<dist.length;++ka)
                                if(ka==index) {path_val[ka][k]=1;}
                                else {path_val[ka][k]=0;};}
                    };
                    double[]dist2=dist.clone();
                    for(int mb=0;mb<node2.length;mb++){
                        double[] dist3=dist2.clone();Arrays.sort(dist3);double kak=dist3[0];int index3=-1;String index4;
                        int index5=-1;
                        for (int l=0;l<node2.length;l++) {
                            if (dist2[l]==kak) {index3 = l;index4 = node2[l];
                                //									index3 adalah posisi nilai terkecil, index4 adalah node nilai terkecil
                                for (int ma=0;ma<node.length;ma++) {if (node[ma].equals(index4)) {index5=0;System.out.println("Node "+ index4+" valid. Pencarian selesai.");
                                    mb=node2.length;node[index3]="0";index2=dist2[index3];index=index3;}}
                                if(index5==-1) {dist2[index3]=1000;}
                            }
                        }
                    }

                    for(int mc=0;mc<node2.length;mc++) {
                        for (int ia = 0; (ia < node2.length); ia++) {
                            if (path_val[ia][lastin] == 1) {
                                filter[ia][lastin] = 1;lastin = ia;ia = node2.length;
                            }
                        }
                    }
                }

                if(filter[0][1]==1) {stat_AB=">>";} else if(filter[1][0]==1){stat_AB="^^";} else {stat_AB="";}
                if(filter[1][2]==1) {stat_BC=">>";} else if(filter[2][1]==1){stat_BC="<<";} else {stat_BC="";}
                if(filter[0][3]==1) {stat_AD="vv";} else if(filter[3][0]==1){stat_AD="^^";} else {stat_AD="";}
                if(filter[3][4]==1) {stat_DE=">>";} else if(filter[4][3]==1){stat_DE="<<";} else {stat_DE="";}
                if(filter[1][4]==1) {stat_BE="vv";} else if(filter[4][1]==1){stat_BE="^^";} else {stat_BE="";}
                if(filter[4][5]==1) {stat_EF=">>";} else if(filter[5][4]==1){stat_EF="<<";} else {stat_EF="";}
                if(filter[4][7]==1) {stat_EH="vv";} else if(filter[7][4]==1){stat_EH="^^";} else {stat_EH="";}
                if(filter[5][8]==1) {stat_FI="vv";} else if(filter[8][5]==1){stat_FI="^^";} else {stat_FI="";}
                if(filter[2][5]==1) {stat_CF="vv";} else if(filter[5][2]==1){stat_CF="^^";} else {stat_CF="";}
                if(filter[7][8]==1) {stat_HI=">>";} else if(filter[8][7]==1){stat_HI="<<";} else {stat_HI="";}
                if(filter[3][6]==1) {stat_DG="vv";} else if(filter[6][3]==1){stat_DG="^^";} else {stat_DG="";}
                if(filter[6][7]==1) {stat_GH=">>";} else if(filter[7][6]==1){stat_GH="<<";} else {stat_GH="";}

                arr_AB.setText(stat_AB+"");arr_BC.setText(stat_BC+"");
                arr_AD.setText(stat_AD+"");arr_DE.setText(stat_DE+"");
                arr_BE.setText(stat_BE+"");arr_EF.setText(stat_EF+"");
                arr_EH.setText(stat_EH+"");arr_FI.setText(stat_FI+"");
                arr_CF.setText(stat_CF+"");arr_HI.setText(stat_HI+"");
                arr_DG.setText(stat_DG+"");arr_GH.setText(stat_GH+"");
            }
        });

        buttonMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int[][] path_val=new int[11][11];  int[][] filter=new int[11][11];

                double[] dist= {100,100,100,100,100,100,100,100,100,100,100};
                String[] node= {"A","B","C","D","E","F","G","H","I","Sda","Sby"};
                String[] node2= {"A","B","C","D","E","F","G","H","I","Sda","Sby"};

                num_path_AB=mathround(num_path_AB);	num_path_BC=mathround(num_path_BC);
                num_path_AD=mathround(num_path_AD); num_path_DE=mathround(num_path_DE);
                num_path_BE=mathround(num_path_BE); num_path_EF=mathround(num_path_EF);
                num_path_EH=mathround(num_path_EH);	num_path_FI=mathround(num_path_FI);
                num_path_CF=mathround(num_path_CF);	num_path_HI=mathround(num_path_HI);
                num_path_DG=mathround(num_path_DG);	num_path_GH=mathround(num_path_GH);
                num_path_A_Sda=mathround(num_path_A_Sda);num_path_Mj_Sby=mathround(num_path_Mj_Sby);


                double[][] valu= {{0,num_path_AB,100,num_path_AD,100,100,100,100,100,num_path_A_Sda,100},
                        {num_path_AB,0,num_path_BC,100,num_path_BE,100,100,100,100,100,num_path_Mj_Sby},
                        {100,num_path_BC,0,100,100,num_path_CF,100,100,100,100,100},
                        {num_path_AD,100,100,0,num_path_DE,100,num_path_DG,100,100,100,100},
                        {100,num_path_BE,100,num_path_DE,0,num_path_EF,100,num_path_EH,100,100,100},
                        {100,100,num_path_CF,100,num_path_EF,0,100,100,num_path_FI,100,100},
                        {100,100,100,num_path_DG,100,100,0,num_path_GH,100,100,100},
                        {100,100,100,100,100,100,num_path_GH,0,num_path_HI,100,100},
                        {100,100,100,100,100,num_path_FI,100,num_path_HI,0,100,100},
                        {num_path_A_Sda,100,100,100,100,100,100,100,100,100,100},
                        {100,num_path_Mj_Sby,100,100,100,100,100,100,100,100,100}};


                arr_AB.setText(" ");arr_BC.setText(" ");
                arr_AD.setText(" ");arr_DE.setText(" ");
                arr_BE.setText(" ");arr_EF.setText(" ");
                arr_EH.setText(" ");arr_FI.setText(" ");
                arr_CF.setText(" ");arr_HI.setText(" ");
                arr_DG.setText(" ");arr_GH.setText(" ");

                path_AB.setText(num_path_AB+"");path_AD.setText(num_path_AD+"");
                path_BC.setText(num_path_BC+"");path_DE.setText(num_path_DE+"");
                path_BE.setText(num_path_BE+"");path_EF.setText(num_path_EF+"");
                path_EH.setText(num_path_EH+"");path_FI.setText(num_path_FI+"");
                path_CF.setText(num_path_CF+"");path_HI.setText(num_path_HI+"");
                path_DG.setText(num_path_DG+"");path_GH.setText(num_path_GH+"");
                String curl= String.valueOf(cur.getText()); String dest="E";
                int index = -1;int lastin=-1;
                for (int i = 0; (i < dist.length); i++) {
                    if (node[i].equals(curl)) {index = i;}
                    if (node[i].equals(dest)) {lastin = i;}
                }

                double index2=0;
                for (int i=0;(i<node.length);i++) {
                    dist[index]=0;node[index]="0";
                }
                for (int i=0;(i<node2.length);i++) {
                    int j=0, k=0;
                    for(k=0;k<dist.length;++k)
                    {System.out.println("k "+k);
                        if (dist[k]>valu[index][k]+index2){dist[k]=valu[index][k]+index2;
                            for(int ka=0;ka<dist.length;++ka)
                                if(ka==index) {path_val[ka][k]=1;}
                                else {path_val[ka][k]=0;};}
                    };
                    double[]dist2=dist.clone();
                    for(int mb=0;mb<node2.length;mb++){
                        double[] dist3=dist2.clone();Arrays.sort(dist3);double kak=dist3[0];int index3=-1;String index4;
                        int index5=-1;
                        for (int l=0;l<node2.length;l++) {
                            if (dist2[l]==kak) {index3 = l;index4 = node2[l];
                                for (int ma=0;ma<node.length;ma++) {if (node[ma].equals(index4)) {index5=0;System.out.println("Node "+ index4+" valid. Pencarian selesai.");
                                    mb=node2.length;node[index3]="0";index2=dist2[index3];index=index3;}}
                                if(index5==-1) {dist2[index3]=1000;}
                            }
                        }
                    }

                    for(int mc=0;mc<node2.length;mc++) {
                        for (int ia = 0; (ia < node2.length); ia++) {
                            if (path_val[ia][lastin] == 1) {
                                filter[ia][lastin] = 1;lastin = ia;ia = node2.length;
                            }
                        }
                    }
                }

                if(filter[0][1]==1) {stat_AB=">>";} else if(filter[1][0]==1){stat_AB="^^";} else {stat_AB="";}
                if(filter[1][2]==1) {stat_BC=">>";} else if(filter[2][1]==1){stat_BC="<<";} else {stat_BC="";}
                if(filter[0][3]==1) {stat_AD="vv";} else if(filter[3][0]==1){stat_AD="^^";} else {stat_AD="";}
                if(filter[3][4]==1) {stat_DE=">>";} else if(filter[4][3]==1){stat_DE="<<";} else {stat_DE="";}
                if(filter[1][4]==1) {stat_BE="vv";} else if(filter[4][1]==1){stat_BE="^^";} else {stat_BE="";}
                if(filter[4][5]==1) {stat_EF=">>";} else if(filter[5][4]==1){stat_EF="<<";} else {stat_EF="";}
                if(filter[4][7]==1) {stat_EH="vv";} else if(filter[7][4]==1){stat_EH="^^";} else {stat_EH="";}
                if(filter[5][8]==1) {stat_FI="vv";} else if(filter[8][5]==1){stat_FI="^^";} else {stat_FI="";}
                if(filter[2][5]==1) {stat_CF="vv";} else if(filter[5][2]==1){stat_CF="^^";} else {stat_CF="";}
                if(filter[7][8]==1) {stat_HI=">>";} else if(filter[8][7]==1){stat_HI="<<";} else {stat_HI="";}
                if(filter[3][6]==1) {stat_DG="vv";} else if(filter[6][3]==1){stat_DG="^^";} else {stat_DG="";}
                if(filter[6][7]==1) {stat_GH=">>";} else if(filter[7][6]==1){stat_GH="<<";} else {stat_GH="";}

                arr_AB.setText(stat_AB+"");
                arr_BC.setText(stat_BC+"");
                arr_AD.setText(stat_AD+"");
                arr_DE.setText(stat_DE+"");
                arr_BE.setText(stat_BE+"");
                arr_EF.setText(stat_EF+"");
                arr_EH.setText(stat_EH+"");
                arr_FI.setText(stat_FI+"");
                arr_CF.setText(stat_CF+"");
                arr_HI.setText(stat_HI+"");
                arr_DG.setText(stat_DG+"");
                arr_GH.setText(stat_GH+"");
            }
        });

        unsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num_path_AB=mathround(num_path_AB);	num_path_BC=mathround(num_path_BC);
                num_path_AD=mathround(num_path_AD); num_path_DE=mathround(num_path_DE);
                num_path_BE=mathround(num_path_BE); num_path_EF=mathround(num_path_EF);
                num_path_EH=mathround(num_path_EH);	num_path_FI=mathround(num_path_FI);
                num_path_CF=mathround(num_path_CF);	num_path_HI=mathround(num_path_HI);
                num_path_DG=mathround(num_path_DG);	num_path_GH=mathround(num_path_GH);
                num_path_A_Sda=mathround(num_path_A_Sda);

                path_AB.setText(num_path_AB+"");path_AD.setText(num_path_AD+"");
                path_BC.setText(num_path_BC+"");path_DE.setText(num_path_DE+"");
                path_BE.setText(num_path_BE+"");path_EF.setText(num_path_EF+"");
                path_EH.setText(num_path_EH+"");path_FI.setText(num_path_FI+"");
                path_CF.setText(num_path_CF+"");path_HI.setText(num_path_HI+"");
                path_DG.setText(num_path_DG+"");path_GH.setText(num_path_GH+"");

                arr_AB.setText(" ");arr_BC.setText(" ");
                arr_AD.setText(" ");arr_DE.setText(" ");
                arr_BE.setText(" ");arr_EF.setText(" ");
                arr_EH.setText(" ");arr_FI.setText(" ");
                arr_CF.setText(" ");arr_HI.setText(" ");
                arr_DG.setText(" ");arr_GH.setText(" ");

                double[][] valu= {{0,num_path_AB,100,num_path_AD,100,100,100,100,100,num_path_A_Sda},
                        {num_path_AB,0,num_path_BC,100,num_path_BE,100,100,100,100,100},
                        {100,num_path_BC,0,100,100,num_path_CF,100,100,100,100},
                        {num_path_AD,100,100,0,num_path_DE,100,num_path_DG,100,100,100},
                        {100,num_path_BE,100,num_path_DE,0,num_path_EF,100,num_path_EH,100,100},
                        {100,100,num_path_CF,100,num_path_EF,0,100,100,num_path_FI,100},
                        {100,100,100,num_path_DG,100,100,0,num_path_GH,100,100},
                        {100,100,100,100,100,100,num_path_GH,0,num_path_HI,100},
                        {100,100,100,100,100,num_path_FI,100,num_path_HI,0,100},
                        {num_path_A_Sda,100,100,100,100,100,100,100,100,100}};

                int[][] path_val=new int[10][10];  int[][] filter=new int[10][10];

                double[] dist= {100,100,100,100,100,100,100,100,100,100};
                String[] node= {"A","B","C","D","E","F","G","H","I","Sda"};
                String[] node2= {"A","B","C","D","E","F","G","H","I","Sda"};
                String curl= String.valueOf(cur.getText()); String dest= String.valueOf(des.getText());
                int index = -1;int lastin=-1;
                for (int i = 0; (i < dist.length); i++) {
                    if (node[i].equals(curl)) {index = i;}
                    if (node[i].equals(dest)) {lastin = i;}
                }

                double index2=0;
                for (int i=0;(i<node.length);i++) {
                    dist[index]=0;node[index]="0";
                }
                for (int i=0;(i<node2.length);i++) {
                    int j=0, k=0;
                    for(k=0;k<dist.length;++k)
                    {if (dist[k]>valu[index][k]+index2){dist[k]=valu[index][k]+index2;
                        for(int ka=0;ka<dist.length;++ka)
                            if(ka==index) {path_val[ka][k]=1;}
                            else {path_val[ka][k]=0;};}
                    };
                    double[]dist2=dist.clone();
                    for(int mb=0;mb<node2.length;mb++){
                        double[] dist3=dist2.clone();Arrays.sort(dist3);double kak=dist3[0];int index3=-1;String index4;
                        int index5=-1;
                        for (int l=0;l<node2.length;l++) {
                            if (dist2[l]==kak) {index3 = l;index4 = node2[l];
                                for (int ma=0;ma<node.length;ma++) {if (node[ma].equals(index4)) {index5=0;System.out.println("Node "+ index4+" valid. Pencarian selesai.");
                                    mb=node2.length;node[index3]="0";index2=dist2[index3];index=index3;}}
                                if(index5==-1) {dist2[index3]=1000;}
                            }
                        }
                    }

                    for(int mc=0;mc<node2.length;mc++) {
                        for (int ia = 0; (ia < node2.length); ia++) {
                            if (path_val[ia][lastin] == 1) {
                                filter[ia][lastin] = 1;lastin = ia;ia = node2.length;
                            }
                        }
                    }
                }

                if(filter[0][1]==1) {stat_AB=">>";} else if(filter[1][0]==1){stat_AB="^^";} else {stat_AB="";}
                if(filter[1][2]==1) {stat_BC=">>";} else if(filter[2][1]==1){stat_BC="<<";} else {stat_BC="";}
                if(filter[0][3]==1) {stat_AD="vv";} else if(filter[3][0]==1){stat_AD="^^";} else {stat_AD="";}
                if(filter[3][4]==1) {stat_DE=">>";} else if(filter[4][3]==1){stat_DE="<<";} else {stat_DE="";}
                if(filter[1][4]==1) {stat_BE="vv";} else if(filter[4][1]==1){stat_BE="^^";} else {stat_BE="";}
                if(filter[4][5]==1) {stat_EF=">>";} else if(filter[5][4]==1){stat_EF="<<";} else {stat_EF="";}
                if(filter[4][7]==1) {stat_EH="vv";} else if(filter[7][4]==1){stat_EH="^^";} else {stat_EH="";}
                if(filter[5][8]==1) {stat_FI="vv";} else if(filter[8][5]==1){stat_FI="^^";} else {stat_FI="";}
                if(filter[2][5]==1) {stat_CF="vv";} else if(filter[5][2]==1){stat_CF="^^";} else {stat_CF="";}
                if(filter[7][8]==1) {stat_HI=">>";} else if(filter[8][7]==1){stat_HI="<<";} else {stat_HI="";}
                if(filter[3][6]==1) {stat_DG="vv";} else if(filter[6][3]==1){stat_DG="^^";} else {stat_DG="";}
                if(filter[6][7]==1) {stat_GH=">>";} else if(filter[7][6]==1){stat_GH="<<";} else {stat_GH="";}
                if(filter[0][9]==1) {stat_GH="shi";} else if(filter[9][0]==1){stat_GH="shi";} else {stat_GH="";}

                arr_AB.setText(stat_AB+"");arr_BC.setText(stat_BC+"");
                arr_AD.setText(stat_AD+"");arr_DE.setText(stat_DE+"");
                arr_BE.setText(stat_BE+"");arr_EF.setText(stat_EF+"");
                arr_EH.setText(stat_EH+"");arr_FI.setText(stat_FI+"");
                arr_CF.setText(stat_CF+"");arr_HI.setText(stat_HI+"");
                arr_DG.setText(stat_DG+"");arr_GH.setText(stat_GH+"");
            }
        });

    }

//    public class doit extends AsyncTask<Void,Void,Void> {
//        String words;
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                Document doc = Jsoup.connect("http://dunia.rmol.co/read/2018/12/12/370435/Raja-Mohammed-VI:-Migrasi-Bukan-Hanya-Masalah-Keamanan-").get();
//                words=doc.text();
//            }catch(Exception e){}
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void aVoid){
//            super.onPostExecute(aVoid);
//            texx.setText(words);
//        }
//    }
}

