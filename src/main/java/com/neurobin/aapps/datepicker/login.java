package com.neurobin.aapps.datepicker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {
    private Button login,kayit;
    private EditText e_mail,pass;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private ProgressDialog mLoginProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e_mail= (EditText) findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.password);
        kayit=(Button)findViewById(R.id.kaydol);


        login=(Button)findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        }
        mLoginProgress = new ProgressDialog(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = e_mail.getText().toString();
                String password = pass.getText().toString();

                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
                    mLoginProgress.setTitle("Giriş Yapılıyor");
                    mLoginProgress.setMessage("Lütfen bekleyiniz");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();

                    LoginUser(email, password);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Hatalı giriş!",Toast.LENGTH_SHORT).show();
                }

            }
        });


        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(login.this,kaydol.class);
                startActivity(i);
            }
        });


    }


    private void LoginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    mLoginProgress.dismiss();

                    FirebaseUser currenUser=mAuth.getCurrentUser();
                    String currentUId = currenUser.getUid();

                    mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUId);
                    mUserDatabase.keepSynced(true);

                    Intent i=new Intent(login.this,MainActivity.class);
                    startActivity(i);
                    finish();

                } else
                {
                    mLoginProgress.hide();
                    Toast.makeText(login.this,"Hatalı!!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currenUser=mAuth.getCurrentUser();
        if(currenUser != null)
        {
            sendToStart();
        }
    }
    private void sendToStart() {
        Intent startIntent=new Intent(login.this,MainActivity.class);
        startActivity(startIntent);
        finish();
    }
}
