package com.selfowner.att_student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView textViewSignin;
    EditText enrolNumber,rollNumber,regNumber,sName,sEmail,sPassword;
    Button signUp;
    Spinner chooseStream,chooseSem,chooseType;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;
    private ProgressDialog progressDialog;
    String regemail;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Student Self Registration");
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
        }
        textViewSignin = findViewById(R.id.textViewSignin);
        chooseSem=findViewById(R.id.chooseSem);
        chooseType=findViewById(R.id.chooseType);
        chooseStream = findViewById(R.id.chooseStream);
        enrolNumber = findViewById(R.id.enrolNumber);
        rollNumber = findViewById(R.id.rollNumber);
        regNumber = findViewById(R.id.regNumber);
        sName = findViewById(R.id.sName);
        sEmail = findViewById(R.id.sEmail);
        sPassword = findViewById(R.id.sPassword);
        signUp = findViewById(R.id.buttonSignup);
        progressDialog = new ProgressDialog(this);
        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view){
                Snackbar snackBar = Snackbar.make(view, "Please Wait While The Process Complete Do No Press Back Button! ", Snackbar.LENGTH_LONG);
                snackBar.show();
                StuLogin();
            }
        });

        textViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                existingUser();
            }
        });
    }

    private void existingUser() {
        startActivity(new Intent(MainActivity.this,Stu_Login.class));
    }

    private void StuLogin(){
        final String enrolnumber = enrolNumber.getText().toString().trim();
        final String stream=chooseStream.getSelectedItem().toString().trim();
        final String  rollnumber= rollNumber.getText().toString().trim();
        final String regnumber=regNumber.getText().toString();
        final String name=sName.getText().toString().trim();
        final String email=sEmail.getText().toString().trim();
        final String password=sPassword.getText().toString().trim();
        final String semester=chooseSem.getSelectedItem().toString();
        final String type=chooseType.getSelectedItem().toString();
        regemail=email.replace(".","");
        //checking if email and passwords are empty
        if(stream.equals("SELECT STREAM") || semester.equals("SELECT SEMESTER") || type.equals("SELECT TYPE")){
            Toast.makeText(this, "Please Select Stream Properly", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please Enter FullName", Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        final Task<AuthResult> authResultTask = firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            //Toast.makeText(MainActivity.this,"Please Wait While The Process Complete Do No Press Back Button",Toast.LENGTH_LONG).show();
                            firebaseDatabase = FirebaseDatabase.getInstance().getReference("STUDENT").child("" + regemail);
                            firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChild("DETAILS")) {
                                        Toast.makeText(MainActivity.this, "Candidate Already Exist", Toast.LENGTH_LONG).show();
                                    } else {

                                        firebaseDatabase = FirebaseDatabase.getInstance().getReference("STUDENT").child("" + regemail);
                                        Student_Upload_Helper_Class stud = new Student_Upload_Helper_Class(enrolnumber, rollnumber, regnumber, name, stream, semester, type, email, password, "", "", "", "", "", "");
                                        firebaseDatabase.setValue(stud);
                                        finish();
                                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                        intent.putExtra("ENROLNUMBER", enrolNumber.getText().toString());
                                        startActivity(intent);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } else {
                            //display some message here
                            Toast.makeText(MainActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

}

