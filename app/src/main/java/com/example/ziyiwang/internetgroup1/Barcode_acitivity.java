package com.example.ziyiwang.internetgroup1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;




public class Barcode_acitivity extends AppCompatActivity {
    private Button btn1;
    TextView textViewShowResult;
    public static String barcode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_acitivity);
        textViewShowResult=(TextView)findViewById(R.id.text1);
        btn1=(Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator =new IntentIntegrator(Barcode_acitivity.this);
                scanIntegrator.initiateScan();

            }
        });


    }
    protected void onActivityResult( int requestCode, int resultCode, Intent in){
        IntentResult scanningResult =IntentIntegrator.parseActivityResult(requestCode,resultCode,in);
        if(scanningResult!=null){
            String contents= in.getStringExtra("SCAN_RESULT");
            String format =in.getStringExtra("SCAN_RESULT_FORMAT");
            textViewShowResult.setText(contents);
            barcode=contents;
        }
    }
}
