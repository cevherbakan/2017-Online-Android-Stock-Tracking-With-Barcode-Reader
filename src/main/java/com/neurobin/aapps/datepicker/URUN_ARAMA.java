package com.neurobin.aapps.datepicker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class URUN_ARAMA extends AppCompatActivity {
    FirebaseDatabase database2=FirebaseDatabase.getInstance();
    final Activity activity=this;

    TextView liste;
    Button aramaBtn,barkodBtn;
    EditText bakodEdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun__aram);

        barkodBtn = (Button)findViewById(R.id.barkodBtn);
        aramaBtn=(Button)findViewById(R.id.aramaBtn);
        liste=(TextView)findViewById(R.id.liste);
        bakodEdt=(EditText)findViewById(R.id.bakodEdt);

        barkodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator=new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });

        aramaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference oku= FirebaseDatabase.getInstance().getReference().child("urun").child(bakodEdt.getText().toString());
                ValueEventListener dinle=new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Urunler k=new Urunler();
                        k=dataSnapshot.getValue(Urunler.class);
                        liste.setText("\n adı:  "+k.urun_ad+"\n fiyatı:  "+k.urun_fiyat+"₺"+"\n firma adı:  "+k.firma_adi+"\n not:  "+k.not);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                oku.addValueEventListener(dinle);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivitiy", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

            }

            else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned:" + result.getContents(), Toast.LENGTH_LONG).show();
                bakodEdt.setText(result.getContents());

                DatabaseReference oku=FirebaseDatabase.getInstance().getReference().child("urun").child(bakodEdt.getText().toString());

                ValueEventListener dinle=new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Urunler k=new Urunler();
                        k=dataSnapshot.getValue(Urunler.class);
                        liste.setText("\n adı:  "+k.urun_ad+"\n fiyatı:  "+k.urun_fiyat+"\n firma adı:  "+k.firma_adi+"\n not:  "+k.not);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                oku.addValueEventListener(dinle);

            }
        }

        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
