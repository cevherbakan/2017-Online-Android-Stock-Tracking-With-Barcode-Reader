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

public class HIZLI_ARA extends AppCompatActivity {
    private Button barkodBtn;
    private Button araBtn;
    private EditText barkodEdt,barkod;
    private TextView icerikTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hizli__ara);
        final Activity activity=this;

        barkodEdt=(EditText)findViewById(R.id.barkodEdt);
        barkodBtn=(Button)findViewById(R.id.barkodBtn);
        icerikTxt=(TextView)findViewById(R.id.icerikTxt);
        araBtn=(Button)findViewById(R.id.araBtn);


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



        araBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference oku=FirebaseDatabase.getInstance().getReference().child("urun").child(barkodEdt.getText().toString());

                ValueEventListener dinle=new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Urunler k=new Urunler();

                        k=dataSnapshot.getValue(Urunler.class);
                        icerikTxt.setText("\n ürün adı:   "+k.urun_ad+"\n ürün fiyatı:   "+k.urun_fiyat);
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
            if (result.getContents() == null)
            {
                Log.d("MainActivitiy", "Barkod okunamadı");
                Toast.makeText(this, "Barkod okunamdı", Toast.LENGTH_LONG).show();

            }
            else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned:" + result.getContents(), Toast.LENGTH_LONG).show();
                barkodEdt.setText(result.getContents());

                DatabaseReference oku=FirebaseDatabase.getInstance().getReference().child("urun").child(barkodEdt.getText().toString());

                ValueEventListener dinle=new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Urunler k=new Urunler();
                        k=dataSnapshot.getValue(Urunler.class);
                        icerikTxt.setText("\n adı:  "+k.urun_ad+"\n fiyatı:  "+k.urun_fiyat+"\n firma adı:  "+k.firma_adi+"\n not:  "+k.not);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                oku.addValueEventListener(dinle);
                }
        }

        else
            {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
