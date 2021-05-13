package com.selfowner.att_student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {
    String emailid="",Name="",Stream="",Semester="",CourseType="",RegNumber="",RollNumber="",EnrolNumber="";
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    Button logOut;
    TextView name,regnumber,enrolnumber,ctype,stream,semester,rollnumber,timer;
    Button personalInfo,saveAtten,viewStats,viewNotice;
    String counter="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        personalInfo=findViewById(R.id.personalInfo);
        timer=findViewById(R.id.timer);
        saveAtten=findViewById(R.id.saveAtten);
        name=findViewById(R.id.Name);
        regnumber=findViewById(R.id.RegNumber);
        enrolnumber=findViewById(R.id.EnrolNumber);
        ctype=findViewById(R.id.CourseType);
        stream=findViewById(R.id.Stream);
        semester=findViewById(R.id.Semester);
        rollnumber=findViewById(R.id.RollNumber);
        viewStats=findViewById(R.id.viewStats);
        viewNotice=findViewById(R.id.viewNotice);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //initializing views
        emailid =user.getEmail();
        emailid=emailid.replace(".","");
        LOADDATA();
        setTitle("Attendance System");
        logOut=findViewById(R.id.logOut);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                //starting login activity
                startActivity(new Intent(Dashboard.this, MainActivity.class));
            }
        });

        personalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,Personal_Info.class);
                intent.putExtra("EMAIL", emailid);
                startActivity(intent);

            }
        });
        //saveAtten.setEnabled(false);

        /*ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        exec.schedule(new  Runnable() {

            @Override
            public void run() {

                Dashboard.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        saveAtten.setEnabled(true);

                    }
                });
            }
        }, two2, TimeUnit.MINUTES);*/
        saveAtten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,Keypad_Attendence.class);
                intent.putExtra("EMAIL", emailid);
                startActivity(intent);
            }
        });
        viewStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,View_Attendance.class);
                intent.putExtra("EMAIL", emailid);
                startActivity(intent);
            }
        });
        viewNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackBar = Snackbar.make(v, "Updating Soon..!", Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        });
    }
    private void LOADDATA(){
        databaseReference= FirebaseDatabase.getInstance().getReference("STUDENT");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(emailid)) {
                    String val1=dataSnapshot.child(emailid).child("name").getValue().toString();
                    String val2=dataSnapshot.child(emailid).child("courseType").getValue().toString();
                    String val3=dataSnapshot.child(emailid).child("semester").getValue().toString();
                    String val4=dataSnapshot.child(emailid).child("regNumber").getValue().toString();
                    String val5=dataSnapshot.child(emailid).child("enrolNumber").getValue().toString();
                    String val6=dataSnapshot.child(emailid).child("rollNumber").getValue().toString();
                    String val7=dataSnapshot.child(emailid).child("stream").getValue().toString();
                    name.setText("Name:"+val1);
                    regnumber.setText("Registration Number:"+val4);
                    enrolnumber.setText("Enrolment Number:"+val5);
                    ctype.setText("CourseType: "+val2);
                    stream.setText("Stream:"+val7);
                    semester.setText("Semester:"+val3);
                    rollnumber.setText("Roll Number:"+val6);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
