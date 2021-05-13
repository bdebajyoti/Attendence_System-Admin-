package com.selfowner.att_student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class View_Attendance extends AppCompatActivity {
    TextView username,sub1,sub2,sub3,sub4,sub5,sub6,at1,at2,at3,at4,at5,at6;
    String emailid="";
    Button closeView;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    String val1="",ss1="",ss2="",ss3="",ss4="",ss5="",ss6="",att1="",att2="",att3="",att4="",att5="00",att6="00";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__attendance);
        setTitle("View Statistics");
        Intent intent=getIntent();
        emailid=intent.getStringExtra("EMAIL");
        username=findViewById(R.id.username);
        closeView=findViewById(R.id.closeView);
        at1=findViewById(R.id.att1);
        at2=findViewById(R.id.att2);
        at3=findViewById(R.id.att3);
        at4=findViewById(R.id.att4);
        at5=findViewById(R.id.att5);
        at6=findViewById(R.id.att6);
        sub1=findViewById(R.id.sub1);
        sub2=findViewById(R.id.sub2);
        sub3=findViewById(R.id.sub3);
        sub4=findViewById(R.id.sub4);
        sub5=findViewById(R.id.sub5);
        sub6=findViewById(R.id.sub6);
        progressDialog = new ProgressDialog(this);
        LOAD();
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_Attendance.this,Dashboard.class));
                finish();
            }
        });
    }

    private void LOAD(){

        progressDialog.setMessage("Loading Information Wait...!");
        progressDialog.show();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("STUDENT");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(emailid)) {
                     val1=dataSnapshot.child(emailid).child("name").getValue().toString();
                     ss1=dataSnapshot.child(emailid).child("sub1").getValue().toString();
                     ss2=dataSnapshot.child(emailid).child("sub2").getValue().toString();
                     ss3=dataSnapshot.child(emailid).child("sub3").getValue().toString();
                     ss4=dataSnapshot.child(emailid).child("sub4").getValue().toString();
                     ss5=dataSnapshot.child(emailid).child("sub5").getValue().toString();
                     ss6=dataSnapshot.child(emailid).child("sub6").getValue().toString();
                     att1=dataSnapshot.child(emailid).child(ss1).getValue().toString();
                     att2=dataSnapshot.child(emailid).child(ss2).getValue().toString();
                     att3=dataSnapshot.child(emailid).child(ss3).getValue().toString();
                     att4=dataSnapshot.child(emailid).child(ss4).getValue().toString();
                    if(!TextUtils.isEmpty(ss5)){
                        ss5="";
                        att5=dataSnapshot.child(emailid).child(ss5).getValue().toString();
                    }
                    if(!TextUtils.isEmpty(ss6)){
                        ss6="";
                        att6=dataSnapshot.child(emailid).child(ss6).getValue().toString();
                    }
                    if(TextUtils.isEmpty(ss5)){
                        ss5="NO SUBJECT";
                        att5="00";
                    }
                    if(TextUtils.isEmpty(ss6)){
                        ss6="NO SUBJECT";
                        att6="00";
                    }


                    username.setText(val1);
                    sub1.setText(ss1);
                    sub2.setText(ss2);
                    sub3.setText(ss3);
                    sub4.setText(ss4);
                    sub5.setText(ss5);
                    sub6.setText(ss6);
                    at1.setText(att1);
                    at2.setText(att2);
                    at3.setText(att3);
                    at4.setText(att4);
                    at5.setText(att5);
                    at6.setText(att6);
                    username.setEnabled(false);
                    sub1.setEnabled(false);
                    sub2.setEnabled(false);
                    sub3.setEnabled(false);
                    sub4.setEnabled(false);
                    sub5.setEnabled(false);
                    sub6.setEnabled(false);
                    at1.setEnabled(false);
                    at2.setEnabled(false);
                    at3.setEnabled(false);
                    at4.setEnabled(false);
                    at5.setEnabled(false);
                    at6.setEnabled(false);
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }
}
