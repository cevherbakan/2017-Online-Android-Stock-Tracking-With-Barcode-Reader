package com.neurobin.aapps.datepicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Calendar calendar;
    SimpleDateFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button urunBtn=(Button)findViewById(R.id.urun);
        Button hizliAraBtn=(Button)findViewById(R.id.hizli_arama);
        Button satisBtn=(Button)findViewById(R.id.satis);
        Button islemlerBtn=(Button)findViewById(R.id.islemler);
        Button notlarBtn=(Button)findViewById(R.id.notlar);
        Button logoutBtn=(Button)findViewById(R.id.cikis);


        TextView tarih=(TextView)findViewById(R.id.tarih);

        urunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,URUN.class);
                startActivity(i);
            }
        });

        hizliAraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,HIZLI_ARA.class);
                startActivity(i);
            }
        });
        satisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,SATIS.class);
                startActivity(i);
            }
        });


        islemlerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,ISLEMLER.class);
                startActivity(i);
            }
        });

        notlarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,NOTLAR.class);
                startActivity(i);
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i=new Intent(MainActivity.this,login.class);
                startActivity(i);
            }
        });

        format=new SimpleDateFormat("dd:MM:yy:HH:mm:ss");
        calendar=Calendar.getInstance();

        tarih.setText(format.format(calendar.getTime()));


    }

}

