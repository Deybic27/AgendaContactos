package com.example.agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.agenda.db.DbContactos;
import com.example.agenda.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VerActivity extends AppCompatActivity {

    EditText txtNombre, txtCorreo, txtTelefono, txtNumDocument, txtFechaNacimiento;
    TextView txtTitleActivity;
    Button btnGuardar;
    FloatingActionButton fabEditar, fabEliminar;
    DbContactos dbContactos;
    Contactos contacto;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);
        txtNombre = findViewById(R.id.txtNombre);
        txtNumDocument = findViewById(R.id.txtNumDocument);
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimiento);
        txtCorreo = findViewById(R.id.txtCorreoElectronico);
        txtTelefono = findViewById(R.id.txtTelefono);
        btnGuardar = findViewById(R.id.btnGuardar);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);
        txtTitleActivity = findViewById(R.id.txtTitleActivity);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            }else{
                id = extras.getInt("ID");
            }
        }else{
            id = (int) savedInstanceState.getSerializable("ID");
        }

        llenarCampos(id);

        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerActivity.this, EditarActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerActivity.this);
                builder.setMessage("¿Desea eliminar este contacto?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(dbContactos.eliminarContacto(id)){
                                    lista();
                                }

                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });

    }

    public void lista(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        llenarCampos(id);
    }

    public void llenarCampos(int id){
        dbContactos = new DbContactos(VerActivity.this);
        contacto = dbContactos.verContacto(id);

        if (contacto != null){
            txtNombre.setText(contacto.getNombre());
            txtNumDocument.setText(contacto.getDocumento());
            txtFechaNacimiento.setText(contacto.getFecha_nacimiento());
            txtTelefono.setText(contacto.getTelefono());
            txtCorreo.setText(contacto.getCorreo_electronico());
            btnGuardar.setVisibility(View.INVISIBLE);
            txtNombre.setInputType(InputType.TYPE_NULL);
            txtTelefono.setInputType(InputType.TYPE_NULL);
            txtCorreo.setInputType(InputType.TYPE_NULL);
            txtTitleActivity.setText(R.string.txt_contacto);
        }
    }
}