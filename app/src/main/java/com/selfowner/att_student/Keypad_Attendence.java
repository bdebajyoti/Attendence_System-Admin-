package com.selfowner.att_student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Keypad_Attendence extends AppCompatActivity {
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> namelist;
    Button one,two,three,four,five,six,seven,eight,nine,dash,zero,back;
    Button submitAtt;
    EditText numPad;
    String txt="";
    String emailid,stream,rollnumber,course,semester,getdate;
    DatabaseReference dbref,db1,db2;
    long initial_time,current_time;
    String rdnumber="",loadedSubject="";
    Spinner subject;
    int att=0;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keypad__attendence);
        setTitle("Self Attendance Section");
        GETDATE();
        Intent intent=getIntent();
        emailid=intent.getStringExtra("EMAIL");
        subject=findViewById(R.id.subject);
        submitAtt=findViewById(R.id.submitAtt);
        LOADDATA();
        submitAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GETTIMEINFO(v);
            }
        });
        numPad=findViewById(R.id.numPad);
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        three=findViewById(R.id.three);
        four=findViewById(R.id.four);
        five=findViewById(R.id.five);
        six=findViewById(R.id.six);
        seven=findViewById(R.id.seven);
        eight=findViewById(R.id.eight);
        nine=findViewById(R.id.nine);
        dash=findViewById(R.id.dash);
        zero=findViewById(R.id.zero);
        back=findViewById(R.id.back);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt=numPad.getText().toString()+1;
                numPad.setText(""+txt);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt=numPad.getText().toString()+2;
                numPad.setText(""+txt);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt=numPad.getText().toString()+3;
                numPad.setText(""+txt);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt=numPad.getText().toString()+4;
                numPad.setText(""+txt);
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt=numPad.getText().toString()+5;
                numPad.setText(""+txt);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt=numPad.getText().toString()+6;
                numPad.setText(""+txt);
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt=numPad.getText().toString()+7;
                numPad.setText(""+txt);
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt=numPad.getText().toString()+8;
                numPad.setText(""+txt);
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt=numPad.getText().toString()+9;
                numPad.setText(""+txt);
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt=numPad.getText().toString()+0;
                numPad.setText(""+txt);
            }
        });
        dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt=numPad.getText().toString()+"-";
                numPad.setText(""+txt);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = numPad.getText().length();
                if (length > 0) {
                    numPad.getText().delete(length - 1, length);
                }
            }
        });
    }
    private void LOADDATA(){
        dbref= FirebaseDatabase.getInstance().getReference("STUDENT");
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(emailid)) {
                    stream=dataSnapshot.child(emailid).child("stream").getValue().toString();
                    course=dataSnapshot.child(emailid).child("courseType").getValue().toString();
                    semester=dataSnapshot.child(emailid).child("semester").getValue().toString();
                    rollnumber=dataSnapshot.child(emailid).child("rollNumber").getValue().toString();
                    LOADSUBS();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void LOADSUBS(){
        namelist=new ArrayList<>();
        adapter=new ArrayAdapter<String>(Keypad_Attendence.this,android.R.layout.simple_spinner_dropdown_item,namelist);
        subject.setAdapter(adapter);
            databaseReference = FirebaseDatabase.getInstance().getReference("CACHAR COLLEGE").child(stream).child(course).child(semester);
            listener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        namelist.add(item.child("subject").getValue().toString());
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    private void GETTIMEINFO(final View view){
        dbref= FirebaseDatabase.getInstance().getReference("STUDENT");
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(emailid)) {
                    att=Integer.parseInt(dataSnapshot.child(emailid).child(subject.getSelectedItem().toString()).getValue().toString());

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        db1= FirebaseDatabase.getInstance().getReference("ATTENDENCE").child(getdate).child(stream);
        db1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(numPad.getText().toString())) {
                    initial_time=Long.parseLong(dataSnapshot.child(numPad.getText().toString()).child("INITIAL_TIME").getValue().toString());
                    rdnumber=dataSnapshot.child(numPad.getText().toString()).child("RANDOM NUMBER").getValue().toString();
                    loadedSubject=dataSnapshot.child(numPad.getText().toString()).child("SUBJECT").getValue().toString();
                    Toast.makeText(Keypad_Attendence.this, "Initial Time:"+initial_time, Toast.LENGTH_LONG).show();
                    current_time= System.currentTimeMillis();
                    if(current_time<=initial_time && numPad.getText().toString().equals(rdnumber) && subject.getSelectedItem().toString().equals(loadedSubject)){
                        SAVEATT();
                    }
                    else{
                        Snackbar snackBar = Snackbar.make(view, "You Have Missed The Time Or Data Is Not Correctly Choose", Snackbar.LENGTH_LONG);
                        snackBar.show();
                    }
                }
                else{
                    Snackbar snackBar = Snackbar.make(view, "Input Number Is Either Wrong Or Invalid..!", Snackbar.LENGTH_LONG);
                    snackBar.show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void SAVEATT(){
        databaseReference= FirebaseDatabase.getInstance().getReference("ATTENDENCE").child(getdate).child(stream).child(numPad.getText().toString());
        databaseReference.child(rollnumber).setValue(rollnumber+"=PRESENT");
        ALLOTATT();
        //Toast.makeText(this, "Current Time:"+time, Toast.LENGTH_LONG).show();
        finish();
    }
    private void ALLOTATT(){
        att=att+1;
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("STUDENT").child(emailid);
        databaseReference.child(subject.getSelectedItem().toString()).setValue(String.valueOf(att));
    }
    private void GETDATE(){

        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        Date todayDate = new Date();
        getdate =""+currentDate.format(todayDate);
    }
}
