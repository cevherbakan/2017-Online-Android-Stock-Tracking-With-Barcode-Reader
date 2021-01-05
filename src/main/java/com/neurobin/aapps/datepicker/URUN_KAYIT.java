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

public class URUN_KAYIT extends AppCompatActivity {

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    final Activity activity=this;

    String barkod;
    String urun_ad;
    String firma_ad;
    String not;
    Double urun_fiyat;
    Double fiyat;
    String fiyatStr;

    private Button silBtn,guncelleBtn,bakodBtn,kaydetBtn,numaraBtn;

    private EditText barkodEdt,urunadEdt,firmaadEdt,notEdt,urunfiyatEdt,eti;
    Boolean anahtar=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun__kayit);



        final Boolean giris=true;

        barkodEdt=(EditText)findViewById(R.id.barkodEdt);
        urunadEdt=(EditText)findViewById(R.id.urunadEdt);
        firmaadEdt=(EditText)findViewById(R.id.firmaadEdt);
        notEdt=(EditText)findViewById(R.id.notEdt);
        urunfiyatEdt=(EditText)findViewById(R.id.urunfiyatEdt);
        guncelleBtn=(Button)findViewById(R.id.guncelleBtn);
        numaraBtn=(Button)findViewById(R.id.numaraBtn);

        bakodBtn=(Button)findViewById(R.id.bakodBtn);
        silBtn=(Button)findViewById(R.id.silBtn);
        kaydetBtn=(Button)findViewById(R.id.kaydetBtn);

        numaraBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                anahtar=true;


                DatabaseReference oku= FirebaseDatabase.getInstance().getReference().child("numara");

                ValueEventListener dinle=new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int number;
                        String k;
                        k=dataSnapshot.getValue().toString();

                            number = Integer.parseInt(k);


                        barkodEdt.setText(String.valueOf(number+1));


                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Hata!", Toast.LENGTH_LONG).show();

                    }
                };
                oku.addValueEventListener(dinle);

            }


        });


        //burası barkod okuyucuyu çalıştırır


        bakodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator=new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Barkodu sabit tutunuz");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.initiateScan();
            }
        });





        guncelleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference oku= FirebaseDatabase.getInstance().getReference().child("urun").child(barkodEdt.getText().toString());

                ValueEventListener dinle=new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Urunler k=new Urunler();
                        k=dataSnapshot.getValue(Urunler.class);


                        urunadEdt.setText(k.urun_ad);
                        firmaadEdt.setText(k.firma_adi);
                        notEdt.setText(k.not);
                        fiyat=(k.urun_fiyat);
                        fiyatStr=fiyat.toString();

                        urunfiyatEdt.setText(fiyatStr);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Böyle bir ürün bulunamadı!", Toast.LENGTH_LONG).show();

                    }
                };
                oku.addValueEventListener(dinle);


            }
        });

        silBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference sil=FirebaseDatabase.getInstance().getReference().child("urun").child(barkodEdt.getText().toString());

                sil.removeValue();
                Intent i=new Intent(URUN_KAYIT.this,URUN.class);
                startActivity(i);
            }
        });

        //burası kayıt işlemi yapar


        kaydetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urun_fiyat = Double.parseDouble(urunfiyatEdt.getText().toString());
                barkod = barkodEdt.getText().toString();
                urun_ad = urunadEdt.getText().toString();
                firma_ad = firmaadEdt.getText().toString();
                not = notEdt.getText().toString();

                if(anahtar==true) {
                    NUMARA_KAYIT(Integer.parseInt(barkod));
                }
                anahtar=false;

                URUN_KAYIT(barkod,urun_ad,urun_fiyat,firma_ad,not);
                Intent i=new Intent(URUN_KAYIT.this,URUN.class);
                startActivity(i);

            }
        });



    }



    //barkod okuma yapıldıktan sonraki mesaj veren ve barkodu edittexte geçiren bölüm

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        EditText bark=(EditText)findViewById(R.id.barkodEdt);


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivitiy", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned:" + result.getContents(), Toast.LENGTH_LONG).show();
                bark.setText(result.getContents());


            }
        }

        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }




    //kaydetme fonksiyonu

    private void URUN_KAYIT(String barkod, String urun_ad,double urun_fiyat,String firma_adi,String not){
        Urunler urun=new Urunler(barkod,urun_ad,urun_fiyat,firma_adi,not);

        myRef.child("urun").child(barkod).setValue(urun);

    }


    private void NUMARA_KAYIT(int numara){
        NUMARA_OLUSTUR no=new NUMARA_OLUSTUR(numara);

        myRef.child("numara").setValue(numara);



    }





}