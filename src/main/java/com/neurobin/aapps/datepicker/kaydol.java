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

import java.util.HashMap;

public class kaydol extends AppCompatActivity {
    private Button kaydet;
    private EditText user,e_mail,pass,dogrulama;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ProgressDialog mRegisterProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaydol);

        //ProgressDialog
        mRegisterProgress = new ProgressDialog(this);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        kaydet=(Button)findViewById(R.id.kayit);
        user=(EditText)findViewById(R.id.username);
        e_mail=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.password);
        dogrulama=(EditText)findViewById(R.id.dogrulama);

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = user.getText().toString();
                String email = e_mail.getText().toString();
                String password = pass.getText().toString();
                String dogrula= dogrulama.getText().toString();

                if(password.equals(dogrula)) {
                    if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
                        if (password.length() >= 6) {
                            mRegisterProgress.setTitle("Kullanıcı kaydediliyor");
                            mRegisterProgress.setMessage("Hesabınız oluşturuluyor lütfen bekleyiniz !");
                            mRegisterProgress.setCanceledOnTouchOutside(false);
                            mRegisterProgress.show();

                            RegisterUser(name, email, password);
                        } else {
                            Toast.makeText(getApplicationContext(), "Parola en az 6 karakter olmalı", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Hatalı veri girdiniz", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    {
                    Toast.makeText(getApplicationContext(),"parolalar eşleşmiyor",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void RegisterUser(final String name, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String uId = currentUser.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uId);

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", name);

                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mRegisterProgress.dismiss();

                                Intent i=new Intent(kaydol.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(kaydol.this, "Bir hata oluştu", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    mRegisterProgress.hide();
                    Toast.makeText(kaydol.this, "Bir hata oluştu", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
