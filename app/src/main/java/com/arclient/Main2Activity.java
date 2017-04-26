package com.arclient;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity {

    String[] f = new String[50];
    int i;
    //    @Override
    String[] stu;
    String date;
    String Class;
    String course;
    String hour;
    DatabaseReference mdata;

    protected void onCreate(Bundle savedInstanceState) {
        Bundle b1 = getIntent().getExtras();
        Log.e("msxml", b1.getStringArray("students")[0]);
        stu = b1.getStringArray("students");
        date = b1.getString("date");
        Class = b1.getString("class");
        hour = b1.getString("hour");
        course = b1.getString("course");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = this.getIntent();
        for(int i=0;i<50;i++)
        f[i]="1";
        mdata = FirebaseDatabase.getInstance().getReference();
  /* Obtain String from Intent  */
        if (intent != null) {
//            String strdata1 = intent.getExtras().getString("senddata");
            //          String strdata = intent.getExtras().getString("s2");

            //        String s2 = intent.getExtras().getString("s2");
            int[] a = new int[50];
            //a[0] = "Abishek";
            //a[1] = "Akash";
            // a[2] = "Nava";
            //a[3] = "Anudeep";
            //a[4] = "Buba";
            for (int j = 1; j < stu.length; j++)
                a[j] = j;


            //TextView te = (TextView) findViewById(R.id.textView4);
            //te.setText(strdata);
            //TextView t2 = (TextView) findViewById(R.id.textView5);
            //t2.setText(strdata1);
            LinearLayout rl = (LinearLayout) findViewById(R.id.activity_main2);
            ScrollView sc = new ScrollView(this);
            LinearLayout myLayout = new LinearLayout(this);
            sc.addView(myLayout);
            myLayout.setOrientation(LinearLayout.VERTICAL);
            for (i = 0; i < stu.length; i++) {
                final Button myButton = new Button(this);
                myButton.setId(i);
                myButton.setText(stu[i]);
                myLayout.addView(myButton);
                myButton.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View view) {
                        if (f[myButton.getId()].equals("1")) {
                            myButton.setBackgroundResource(android.R.color.holo_red_light);
                            f[myButton.getId()]="0";
                        } else {
                            myButton.setBackgroundResource(android.R.drawable.btn_default);
                            f[myButton.getId()]="1";
                        }
                    }
                });
            }


            //myButton.setBackgroundColor(Color.BLUE);
            //myButton[1].setText("2. Akash");
            //myLayout.addView(myButton[1]);
            //myButton[2].setText("3. Nava Surya Teja");
            //myLayout.addView(myButton[2]);
            //setContentView(myLayout);
            Button submit = (Button) findViewById(R.id.button5);
            submit.setBackgroundResource(android.R.color.holo_green_light);

            submit.setText("Submit");
            Toast.makeText(Main2Activity.this, " clicked", Toast.LENGTH_SHORT).show();
            //      submit.callOnClick(sub());
           /*
            submit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Toast.makeText(Main2Activity.this, "Submit clicked", Toast.LENGTH_SHORT).show();
                    sub();

                }
            });*/
            //  rl.addView(submit);

            rl.addView(sc);
            // DO SOMETHING HERE
        }
    }

    Dialog d1;

    public void sub(View view) {
        Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show();

        d1 = new Dialog(this);
        d1.setContentView(R.layout.layout_diag);

        Toast.makeText(this, "Diag", Toast.LENGTH_SHORT).show();
        LinearLayout l1 = (LinearLayout) d1.findViewById(R.id.Lin);
        for (int i = 0; i < stu.length; i++)
            if (f[i].equals("0")) {
                Integer i1 = i + 1;
                TextView t1 = new TextView(d1.getContext());
                t1.setText(stu[i]);
                l1.addView(t1);
            }
        d1.show();
    }

    public void cancel(View view) {
        d1.dismiss();
    }

    public void confirm(View view) {
        ProgressDialog p1 = new ProgressDialog(this);
        p1.setMessage("Adding Record ...");
        for (int i = 0; i <stu.length; i++) {
            mdata.child("class").child(Class).child(course).child(stu[i]).child(date).setValue(date);
            mdata.child("class").child(Class).child(course).child(stu[i]).child(date).child("Hour").setValue(hour);
            mdata.child("class").child(Class).child(course).child(stu[i]).child(date).child("Present").setValue(f[i]);
        }
    p1.dismiss();
        d1.dismiss();
        finish();
        Intent in  = new Intent(this,Home.class);
        startActivity(in);

    }
}