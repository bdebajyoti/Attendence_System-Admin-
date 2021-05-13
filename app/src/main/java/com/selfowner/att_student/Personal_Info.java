package com.selfowner.att_student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Personal_Info extends AppCompatActivity {
    Button selfUpdate,selfSave,loadSub;
    EditText sName,sEnroll,sRoll,sReg,sStream,sSemester,sCourse,s1,s2,s3,s4,s5,s6;
    private String emailid;
    private DatabaseReference databaseReference;
    private DatabaseReference firebaseDatabase;
    private ProgressBar pBar;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> namelist,selectedItems;
    ListView choice;
    String sub1="",sub2="",sub3="",sub4="",sub5="",sub6="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__info);
        setTitle("Personal Information");
        selectedItems=new ArrayList<String>();
        pBar=findViewById(R.id.pBar);
        Intent intent=getIntent();
        emailid=intent.getStringExtra("EMAIL");
        s1=findViewById(R.id.sub1);
        s2=findViewById(R.id.sub2);
        s3=findViewById(R.id.sub3);
        s4=findViewById(R.id.sub4);
        s5=findViewById(R.id.sub5);
        s6=findViewById(R.id.sub6);
        loadSub=findViewById(R.id.loadSubs);
        sName=findViewById(R.id.sName);
        sEnroll=findViewById(R.id.sEnroll);
        sRoll=findViewById(R.id.sRoll);
        sReg=findViewById(R.id.sReg);
        sStream=findViewById(R.id.sStream);
        sSemester=findViewById(R.id.sSemester);
        sCourse=findViewById(R.id.sCourse);
        selfUpdate=findViewById(R.id.selfUpdate);
        selfSave=findViewById(R.id.selfSave);
        choice=findViewById(R.id.checkable_list);
        choice.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        LOAD();
        selfUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackBar = Snackbar.make(v, "Contact Admin For Self Update!", Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        });
        selfSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SAVEDATA(v);
            }
        });
        loadSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackBar = Snackbar.make(v, "Loading Subjects...!", Snackbar.LENGTH_LONG);
                snackBar.show();
                LOAD1();
            }
        });
        choice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=((TextView)view).getText().toString();
                if(selectedItems.contains(item)){
                    selectedItems.remove(item);
                }
                else
                {
                    selectedItems.add(item);
                }
            }
        });
    }

    private void LOAD(){
        databaseReference= FirebaseDatabase.getInstance().getReference().child("STUDENT");
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
                    String ss1=dataSnapshot.child(emailid).child("sub1").getValue().toString();
                    String ss2=dataSnapshot.child(emailid).child("sub2").getValue().toString();
                    String ss3=dataSnapshot.child(emailid).child("sub3").getValue().toString();
                    String ss4=dataSnapshot.child(emailid).child("sub4").getValue().toString();
                    String ss5=dataSnapshot.child(emailid).child("sub5").getValue().toString();
                    String ss6=dataSnapshot.child(emailid).child("sub6").getValue().toString();
                    if(!TextUtils.isEmpty(ss1) || !TextUtils.isEmpty(ss2) || !TextUtils.isEmpty(ss3) || !TextUtils.isEmpty(ss4)|| !TextUtils.isEmpty(ss5) || !TextUtils.isEmpty(ss6)){
                        loadSub.setVisibility(View.INVISIBLE);
                        choice.setVisibility(View.INVISIBLE);
                    }
                    if(TextUtils.isEmpty(ss5)){
                        ss5="NO SUBJECT";
                    }
                    if(TextUtils.isEmpty(ss6)){
                        ss6="NO SUBJECT";
                    }
                    sName.setText(val1);
                    sEnroll.setText(val5);
                    sRoll.setText(val6);
                    sReg.setText(val4);
                    sStream.setText(val7);
                    sSemester.setText(val3);
                    sCourse.setText(val2);
                    s1.setText(ss1);
                    s2.setText(ss2);
                    s3.setText(ss3);
                    s4.setText(ss4);
                    s5.setText(ss5);
                    s6.setText(ss6);

                    sName.setEnabled(false);
                    sRoll.setEnabled(false);
                    sReg.setEnabled(false);
                    sStream.setEnabled(false);
                    sCourse.setEnabled(false);
                    sEnroll.setEnabled(false);
                    sStream.setEnabled(false);
                    s1.setEnabled(false);
                    s2.setEnabled(false);
                    s3.setEnabled(false);
                    s4.setEnabled(false);
                    s5.setEnabled(false);
                    s6.setEnabled(false);

                    pBar.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pBar.setVisibility(View.INVISIBLE);
            }
        });

    }
    private void SAVEDATA(View view){
        int nos=0;
        String[] items=new String[selectedItems.size()];
        for(String item:selectedItems){

            nos++;
        }
        if(nos<=0){
            Snackbar snackBar = Snackbar.make(view, "Nothing Is Selected Yet..!", Snackbar.LENGTH_LONG);
            snackBar.show();
        }
        if(nos==4){
            sub1=selectedItems.get(0);
            sub2=selectedItems.get(1);
            sub3=selectedItems.get(2);
            sub4=selectedItems.get(3);
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("STUDENT").child(emailid);
            databaseReference.child("sub1").setValue(sub1);
            databaseReference.child("sub2").setValue(sub2);
            databaseReference.child("sub3").setValue(sub3);
            databaseReference.child("sub4").setValue(sub4);
            databaseReference.child(sub1).setValue("00");
            databaseReference.child(sub2).setValue("00");
            databaseReference.child(sub3).setValue("00");
            databaseReference.child(sub4).setValue("00");
        }
        if(nos==5){
            sub1=selectedItems.get(0);
            sub2=selectedItems.get(1);
            sub3=selectedItems.get(2);
            sub4=selectedItems.get(3);
            sub5=selectedItems.get(4);
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("STUDENT").child(emailid);
            databaseReference.child("sub1").setValue(sub1);
            databaseReference.child("sub2").setValue(sub2);
            databaseReference.child("sub3").setValue(sub3);
            databaseReference.child("sub4").setValue(sub4);
            databaseReference.child("sub5").setValue(sub5);
            databaseReference.child(sub1).setValue("00");
            databaseReference.child(sub2).setValue("00");
            databaseReference.child(sub3).setValue("00");
            databaseReference.child(sub4).setValue("00");
            databaseReference.child(sub5).setValue("00");
        }
        if(nos==6){
            sub1=selectedItems.get(0);
            sub2=selectedItems.get(1);
            sub3=selectedItems.get(2);
            sub4=selectedItems.get(3);
            sub5=selectedItems.get(4);
            sub6=selectedItems.get(5);
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("STUDENT").child(emailid);
            databaseReference.child("sub1").setValue(sub1);
            databaseReference.child("sub2").setValue(sub2);
            databaseReference.child("sub3").setValue(sub3);
            databaseReference.child("sub4").setValue(sub4);
            databaseReference.child("sub5").setValue(sub5);
            databaseReference.child("sub6").setValue(sub6);
            databaseReference.child(sub1).setValue("00");
            databaseReference.child(sub2).setValue("00");
            databaseReference.child(sub3).setValue("00");
            databaseReference.child(sub4).setValue("00");
            databaseReference.child(sub5).setValue("00");
            databaseReference.child(sub6).setValue("00");
        }
            finish();
            startActivity(new Intent(Personal_Info.this,Personal_Info.class));

    }
    private void LOAD1(){
        retData();
    }
    private void retData(){
        namelist=new ArrayList<>();
        adapter=new ArrayAdapter<String>(Personal_Info.this,R.layout.rowlayout,R.id.txt_lan,namelist);
        choice.setAdapter(adapter);
        final String stream=sStream.getText().toString();
        final String course=sCourse.getText().toString();
        final String semester=sSemester.getText().toString();
        databaseReference= FirebaseDatabase.getInstance().getReference("CACHAR COLLEGE").child(stream).child(course).child(semester);
        listener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item:dataSnapshot.getChildren()){
                    namelist.add(item.child("subject").getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
