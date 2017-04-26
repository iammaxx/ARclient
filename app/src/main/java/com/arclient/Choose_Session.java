package com.arclient;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Choose_Session extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
DatabaseReference mdata= FirebaseDatabase.getInstance().getReference();
    String[] s1;
    Spinner spin2;
     Spinner spinner=null;
    Spinner spin1;
    String D;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__session);
       spinner=(Spinner) findViewById(R.id.spinner);
        Calendar c = Calendar.getInstance();
        Integer d,m,y;
        d=c.get(Calendar.DATE);
        m=c.get(Calendar.MONTH)+1;
        y=c.get(Calendar.YEAR);

        D=d.toString()+"-"+m.toString()+"-"+y.toString();
        TextView t1=(TextView)findViewById(R.id.textView6);
        t1.setText(D);
        mdata.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               HashMap<String,String>h1= (HashMap<String,String>)dataSnapshot.child("class").getValue();
                //s1=(String[])h1.keySet().toArray();

                // spinner=(Spinner) findViewById(R.id.spinner);
                //spinner.setOnItemSelectedListener();
                Log.e("OMG",h1.keySet().toString());
                //Log.e("DOG",s1.toString());

                List<String> c= new ArrayList<String>(h1.keySet());
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, c);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              final String class1= (String) adapterView.getItemAtPosition(i);
              mdata.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                      HashMap<String, String> h1 = (HashMap<String,String>)dataSnapshot.child("class").child(class1).getValue();
                      spin1=(Spinner) findViewById(R.id.spinner3);
                      List<String> c1= new ArrayList<String>(h1.keySet());
                      ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, c1);
                      dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                      spin1.setAdapter(dataAdapter1);
                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError) {

                  }
              });
          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });
/*
        Spinner spin1=(Spinner) findViewById(R.id.spinner3);
        spin1.setOnItemSelectedListener(this);
        List<String> c1= new ArrayList<String>();
        c1.add("FLA");
        c1.add("Software");
        c1.add("Android");
        c1.add("Physics");
        c1.add("Networks");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, c1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
*/
         spin2=(Spinner) findViewById(R.id.spinner2);
        spin2.setOnItemSelectedListener(this);
         List<String> c2= new ArrayList<String>();
        c2.add("1");
        c2.add("2");
        c2.add("3");
        c2.add("4");
        c2.add("5");
        c2.add("6");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, c2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner


        spin2.setAdapter(dataAdapter2);

        Button b4=(Button) findViewById(R.id.button4);

    }
    void go(View view){
        final String sx= (String) spinner.getSelectedItem();
        final String sy = (String) spin1.getSelectedItem();
       final String sz= (String) spin2.getSelectedItem();
       final TextView su  = (TextView)findViewById(R.id.textView6);
       final String dt= su.getText().toString();
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String,String>   h1 = (HashMap<String, String>) dataSnapshot.child("class").child(sx).child(sy).getValue();
                List<String>l1=new ArrayList<String>(h1.keySet());
                String [] students = l1.toArray(new String[l1.size()]);
                Bundle b1 =new Bundle();
                b1.putStringArray("students",students);
                b1.putString("class",sx);
                b1.putString("course",sy);
                b1.putString("hour",sz);
                b1.putString("date",dt);
                Log.e("stu",students[0]);
                Intent intent=new Intent(Choose_Session.this,Main2Activity.class);
                intent.putExtras(b1);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
void pick(View view) {

    DatePickerDialog d1 = new DatePickerDialog(this);
    d1.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            TextView  t1 = (TextView)findViewById(R.id.textView6);
            Integer day = i2;
            Integer month = i1+1;
            Integer year = i;
            t1.setText(day.toString()+"-"+month.toString()+"-"+year.toString());
        }
    });
    d1.show();
}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String item = parent.getItemAtPosition(i).toString();

        // Showing selected spinner item
     //   if((item.equals("FLA"))||(item.equals("Networks"))||(item.equals("Physics"))||(item.equals("Android"))||(item.equals("Software")))
          //  Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
