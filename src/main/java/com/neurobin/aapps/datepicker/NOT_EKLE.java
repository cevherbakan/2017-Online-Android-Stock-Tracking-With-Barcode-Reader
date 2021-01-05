package com.neurobin.aapps.datepicker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NOT_EKLE extends AppCompatActivity {
    FirebaseDatabase database2=FirebaseDatabase.getInstance();
    DatabaseReference myRef = database2.getReference();
    private EditText notEdt;
    private Button ekleBtn;

    final Activity activity=this;

    private void NOT_EKLe(String not){
        myRef.child("not").child(not).setValue(not);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not__ekle);

        ekleBtn=(Button)findViewById(R.id.ekleBtn);
        notEdt=(EditText)findViewById(R.id.notEdt);


        ekleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notStr;

                notStr=notEdt.getText().toString();
                NOT_EKLe(notStr);

                 Intent i=new Intent(NOT_EKLE.this,NOTLAR.class);
                  startActivity(i);

            }
        });

    }
}

