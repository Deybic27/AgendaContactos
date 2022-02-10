package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class LeerCodigoActivity extends AppCompatActivity {

    Button btnLeerCodigo;
    EditText txtScanCedula, txtScanNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_codigo);

        btnLeerCodigo = findViewById(R.id.btnLeerCodigo);
        txtScanCedula = findViewById(R.id.txtScanCedula);
        txtScanNombre = findViewById(R.id.txtScanNombre);

        btnLeerCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrador = new IntentIntegrator(LeerCodigoActivity.this);
                integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrador.setPrompt("Lector - CDP");
                integrador.setCameraId(0);
                integrador.setBeepEnabled(true);
                integrador.setBarcodeImageEnabled(true);
                integrador.initiateScan();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result != null){
            if (result.getContents() == null){
                Toast.makeText(this,"Lectura cancelada",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,result.getContents(),Toast.LENGTH_SHORT).show();
                txtScanCedula.setText(result.getContents().substring(47,58)); // Numero de Cedula
                txtScanNombre.setText(result.getContents().substring(58,150)); // Nombre
                Intent intent = new Intent(this, NuevoActivity.class);
                intent.putExtra("TELEFONO",result.getContents().substring(47,58));
                intent.putExtra("NOMBRE",result.getContents().substring(58,150));
                startActivity(intent);
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}