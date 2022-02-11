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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        String nombreCompleto = "";

        if(result != null){
            if (result.getContents() == null){
                Toast.makeText(this,"Lectura cancelada",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,result.getContents(),Toast.LENGTH_SHORT).show();
                System.out.println("________________________________________________________________________");
                txtScanCedula.setText(result.getContents().substring(48,58)); // Numero de Cedula
                txtScanNombre.setText(result.getContents().substring(58,150)); // Nombre
                //result.getContents().substring(152,160) // Fecha de nacimiento

                nombreCompleto = convertirByteToString(result);
                String[] splitNombreCompleto = nombreCompleto.split(" ");
                System.out.println(nombreCompleto);
                System.out.println(result.getContents().substring(48,58));
                System.out.println(result.getContents().substring(152,160));
                System.out.println(result.getContents());

                System.out.println("________________________________________________________________________");
                Intent intent = new Intent(this, NuevoActivity.class);
                intent.putExtra("NUMDOCUMENT",result.getContents().substring(48,58));
                intent.putExtra("FECHANACIMIENTO",result.getContents().substring(152,160));
                if (splitNombreCompleto.length == 4){
                    intent.putExtra("NOMBRE",splitNombreCompleto[2] + " " + splitNombreCompleto[3] + " " + splitNombreCompleto[0] + " " + splitNombreCompleto[1]);
                }else if (splitNombreCompleto.length == 3){
                    intent.putExtra("NOMBRE",splitNombreCompleto[2] + " " + splitNombreCompleto[0] + " " + splitNombreCompleto[1]);
                }
                startActivity(intent);
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    public String convertirByteToString(IntentResult result){
        String s = result.getContents().substring(58, 150);
        byte[] b = s.getBytes(StandardCharsets.UTF_8);
        List<Byte> c = new ArrayList<>();

        for(int i=0, j=1; i<b.length;i++){
            if (b[i] != 0 || b[i] == 0 && b[i-j] != 0){
                if (b[i] != 0){

                    System.out.println(b[i]);
                    c.add(b[i]);

                }else if (b[i] == 0){
                    System.out.println(b[i]);
                    c.add((byte)32);
                }
            }
        }

        byte[] e = new byte[c.size()];

        for(int i=0; i<c.size();i++){
            e[i] = c.get(i);
        }

        String r = new String(e, StandardCharsets.US_ASCII);


        System.out.println(b[0]);
         //Arrays.toString(result.getContents().substring(58, 150).getBytes(StandardCharsets.UTF_8))
        System.out.println(txtScanNombre.getText());

        return r;
    }
}