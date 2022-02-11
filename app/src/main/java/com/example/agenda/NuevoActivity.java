package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agenda.db.DbContactos;

import java.util.regex.Pattern;

public class NuevoActivity extends AppCompatActivity {

    EditText txtNombre, txtTelefono, txtCorreoElectronico, txtNumDocument, txtFechaNacimiento;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);
        txtNombre = findViewById(R.id.txtNombre);
        txtNumDocument = findViewById(R.id.txtNumDocument);
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimiento);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreoElectronico = findViewById(R.id.txtCorreoElectronico);
        btnGuardar = findViewById(R.id.btnGuardar);


        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
            }else{
                txtNombre.setText(extras.getString("NOMBRE"));
                txtNumDocument.setText(extras.getString("NUMDOCUMENT"));
                txtFechaNacimiento.setText(extras.getString("FECHANACIMIENTO"));
            }
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtNombre.getText().toString().equals("") && !txtNumDocument.getText().toString().equals("") && !txtFechaNacimiento.getText().toString().equals("") && !txtTelefono.getText().toString().equals("") && !txtCorreoElectronico.getText().toString().equals("")){
                    if(validateEmail(txtCorreoElectronico.getText().toString())){
                        DbContactos dbContactos = new DbContactos(NuevoActivity.this);
                        long id = dbContactos.insertarContacto(
                                txtNombre.getText().toString(),
                                txtNumDocument.getText().toString(),
                                txtFechaNacimiento.getText().toString(),
                                txtTelefono.getText().toString(),
                                txtCorreoElectronico.getText().toString()
                        );
                        if(id >0){
                            Toast.makeText(NuevoActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();
                            limpiar();
                        }else{
                            Toast.makeText(NuevoActivity.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(NuevoActivity.this, "CORREO INCORRECTO",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(NuevoActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public void limpiar(){
        txtNombre.setText("");
        txtNumDocument.setText("");
        txtFechaNacimiento.setText("");
        txtTelefono.setText("");
        txtCorreoElectronico.setText("");
    }
}